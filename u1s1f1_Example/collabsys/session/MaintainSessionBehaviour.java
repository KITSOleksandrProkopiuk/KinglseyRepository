package com.u1s1f1.collabsys.session;

import java.util.logging.Logger;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MaintainSessionBehaviour extends CyclicBehaviour {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MaintainSessionBehaviour.class.getName());

	/**
	 * Required for serializable class
	 */
	private static final long serialVersionUID = 4821511759353161972L;

	private boolean SessionCreated = false;

	private String SessionController;

	private int Quantity_of_Clients = 0;

	private final jade.core.AID sessionID;

	/**@Override*/

	public MaintainSessionBehaviour(final jade.core.AID sessionID, Agent agent) {
		super(agent);
		this.sessionID = sessionID;
		}

	public void action() {
		MessageTemplate template = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF),MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msg = myAgent.receive(template);
		if (msg != null) {
			if (msg.getPerformative() == ACLMessage.QUERY_IF) {
				logger.info("SessionAgent received request from " + msg.getContent());
				ACLMessage reply = msg.createReply();
							
				if (!SessionCreated) {
					logger.info("Session " + sessionID + " is created. Sending CONFIRM performative");
					reply.setPerformative(ACLMessage.CONFIRM);
					reply.setContent("TRUE");
					SessionController = msg.getContent();
					logger.info("Session controller is " + SessionController);
					SessionCreated = true;
				} else {
					logger.info("User " + msg.getContent() + " joined session " + sessionID + ". Sending DISCONFIRM performative");
					reply.setPerformative(ACLMessage.DISCONFIRM);
					reply.setContent("FALSE");
					}
				myAgent.send(reply);
				Quantity_of_Clients = Quantity_of_Clients + 1;
			}
			if (msg.getPerformative() == ACLMessage.INFORM) {
				logger.info("SessionAgent received request from " + msg.getContent() + "; Session Controller = " + SessionController);
				ACLMessage reply = msg.createReply();				
			
				if (SessionController.equals(msg.getContent())) {
					logger.info("Session " + sessionID + " is finished. Sending CONFIRM performative");
					reply.setPerformative(ACLMessage.CONFIRM);
					reply.setContent("TRUE");
					SessionController = "";
				} else {
					logger.info("User " + msg.getContent() + " disconnecting from session " + sessionID + ". Sending CONFIRM performative");
					reply.setPerformative(ACLMessage.CONFIRM);
					reply.setContent("TRUE");
				}
				myAgent.send(reply);
				Quantity_of_Clients = Quantity_of_Clients - 1;

				if (Quantity_of_Clients == 0) {
					SessionCreated = false;
																	
				}
			}
		} 
		
		else {
			/*this.block();*/
		}
	}

}