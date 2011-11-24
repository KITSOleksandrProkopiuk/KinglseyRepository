package com.u1s1f1.collabsys.floor;

import java.util.logging.Logger;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MaintainFloorBehaviour extends CyclicBehaviour {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MaintainFloorBehaviour.class.getName());

	/**
	 * Required for serializable class
	 */
	private static final long serialVersionUID = 4821511759353161972L;

	private boolean FloorOccupied = false;
	private boolean FloorSuspended = false;

	private final jade.core.AID floorID;

	private String FloorController = "";

public MaintainFloorBehaviour(final jade.core.AID floorID, Agent agent) {
		super(agent);
		this.floorID = floorID;
	}
	/**@Override*/
	public void action() {
	MessageTemplate template = MessageTemplate.or(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF),MessageTemplate.MatchPerformative(ACLMessage.INFORM)),MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchPerformative(ACLMessage.CFP)));

		/**MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF);*/
		ACLMessage msg = myAgent.receive(template);
		if (msg != null) {
			if (msg.getPerformative() == ACLMessage.CFP) {
				logger.info("FloorAgent received request");
				ACLMessage reply = msg.createReply();
							
				if (!FloorOccupied) {
					logger.info("Floor " + this.floorID + " is occupied. Sending CONFIRM performative");
					reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					reply.setContent("TRUE");
					FloorController = msg.getContent();
					FloorOccupied = true;
					myAgent.send(reply);
				} else {
					logger.info("Floor " + this.floorID + " access is denied to user " + msg.getContent());
					this.block();
					}				
				
			}
			
			if (msg.getPerformative() == ACLMessage.PROPOSE) {
				logger.info("FloorAgent received suspend request");
				ACLMessage reply = msg.createReply();
			
				if (!FloorSuspended) {
					logger.info("Floor " + floorID + " is being suspended");
					reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					reply.setContent("TRUE");
					FloorSuspended=true;
					myAgent.send(reply);
				} else {
					this.block();
				}
			}
			
			if (msg.getPerformative() == ACLMessage.INFORM) {
				logger.info("FloorAgent received resume request");
				ACLMessage reply = msg.createReply();
			
				if (FloorSuspended) {
					logger.info("Floor " + floorID + " is being resumed");
					reply.setPerformative(ACLMessage.CONFIRM);
					reply.setContent("TRUE");
					FloorSuspended=false;
					myAgent.send(reply);
				} else {
					this.block();
				}
			}

			if (msg.getPerformative() == ACLMessage.QUERY_IF) {
				logger.info("FloorAgent received release request");
				ACLMessage reply = msg.createReply();
			
				if ((FloorOccupied) && (FloorController.equals(msg.getContent()))) {
					logger.info("Floor " + floorID + " is being released");
					reply.setPerformative(ACLMessage.CONFIRM);
					reply.setContent("TRUE");
					FloorOccupied=false;
					FloorController = "";
					myAgent.send(reply);
					
															
				} else {
					/*this.block();*/
				}
			}

		} else {
			/*this.block();*/
			}
	}

}
