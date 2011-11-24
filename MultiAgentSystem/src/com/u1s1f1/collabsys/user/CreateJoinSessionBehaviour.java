package com.u1s1f1.collabsys.user;

import java.util.logging.Logger;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CreateJoinSessionBehaviour extends TickerBehaviour {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CreateJoinSessionBehaviour.class.getName());

	/**
	 * Required for serializable class
	 */
	private static final long serialVersionUID = 5300916511326899569L;
	
	/*private final long sessionID;	*/
	private MessageTemplate template;
	private AID[] sessionAgents;
	public int step = 0;
	
	public boolean isSessionController = false;
	public boolean isWaiting = false;
	
			
	public CreateJoinSessionBehaviour(Agent agent, long interval) {
		super(agent, interval);
		/*this.sessionID = sessionID;*/
	}

	private void updateSessionAgents() {
		logger.info("CreateJoinSessionBehaviour updating sessions list");
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("session");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			this.sessionAgents = new AID[result.length];
			logger.info("There are " + result.length + " session services available");
			for (int i = 0; i < result.length; ++i) {
				this.sessionAgents[i] = result[i].getName();
			}
			logger.info("Successfuly updated sessions list");
		}
		catch (FIPAException fe) {
			logger.severe("Error during update of sessions list");
			fe.printStackTrace();
		}
	}
	
	private void createJoinSessionQuery() {
		logger.info("CreateJoinSessionBehaviour is checking to create/join session");

		this.updateSessionAgents();
		if (this.sessionAgents == null || this.sessionAgents.length == 0) {
			return;
		}
		
		logger.info("CreateJoinSessionBehaviour is sending QUERY_IF to session controller " + this.sessionAgents[0]);
		ACLMessage query = new ACLMessage(ACLMessage.QUERY_IF);

		query.addReceiver(this.sessionAgents[0]);
		
		query.setContent(String.valueOf(myAgent.getAID().getName()));
		query.setConversationId("query-create-session");
		myAgent.send(query);
		this.template = MessageTemplate.and(MessageTemplate.MatchConversationId("query-create-session"),
				MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM), MessageTemplate.MatchPerformative(ACLMessage.DISCONFIRM)));
		this.step = 1;
	}
	
	private void receiveResponse() {
		logger.info("CreateJoinSessionBehaviour is receiving answers for query for session");
		
		ACLMessage reply = myAgent.receive(this.template);
		if (reply != null) {
			if (reply.getPerformative() == ACLMessage.CONFIRM) {
				logger.info("CreateJoinSessionBehaviour joined session " + this.sessionAgents[0] + " as CONTROLLER");
				isSessionController = true;
				this.step = 2;
				return;
			}
			if (reply.getPerformative() == ACLMessage.DISCONFIRM) {
				logger.info("CreateJoinSessionBehaviour joined  session " + this.sessionAgents[0] + " as USER");
				this.step = 2;
			}
		} else {
			this.block();
		}
	}
	
	private void joinSessionDone() {
		
		if (!isWaiting){
		logger.info("User " + myAgent.getAID().getName() + " joined session " + this.sessionAgents[0] + ". Adding UseFloorBehaviour to use resource");
		myAgent.addBehaviour(new UseFloorBehaviour(myAgent, 2000));
		
		this.isWaiting = true;
		}
		
		this.template = MessageTemplate.and(MessageTemplate.MatchConversationId("floor-released"),
				MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
				
		ACLMessage signal = myAgent.receive(this.template);
		if (signal != null) {
			if (signal.getPerformative() == ACLMessage.CONFIRM) {
				logger.info("User " + myAgent.getAID().getName() + " released floor and is leaving session " + this.sessionAgents[0]);
				this.step = 3;
				this.isWaiting = false;
			}			
		}		
	}

	private void leaveSession() {
		logger.info("User " + myAgent.getAID().getName() + " leaving session " + this.sessionAgents[0]);
		ACLMessage query = new ACLMessage(ACLMessage.INFORM);

		query.addReceiver(this.sessionAgents[0]);
		
		query.setContent(String.valueOf(myAgent.getAID().getName()));
		query.setConversationId("query-leave-session");
		myAgent.send(query);
		this.template = MessageTemplate.and(MessageTemplate.MatchConversationId("query-leave-session"),MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
		this.step = 4;		
	}

	private void leaveSessionDone() {
		logger.info("CreateJoinSessionBehaviour is receiving answers for query to leave session " + this.sessionAgents[0]);
		
		ACLMessage reply = myAgent.receive(this.template);
		if (reply != null) {
			if (reply.getPerformative() == ACLMessage.CONFIRM) {
				logger.info("CreateJoinSessionBehaviour left session " + this.sessionAgents[0]);
				logger.info("CreateJoinSessionBehaviour of " + myAgent.getAID().getName() + " is ceased");
				if (isSessionController)
          isSessionController = false;
				myAgent.removeBehaviour(this);
				
				return;
			}			
		} else {
			this.block();
		}
				
	}
	
		
	protected void onTick() {
		switch(step) {
		case 0:
			logger.info("CreateJoinSessionBehaviour step 1: sending queries to create/join session");
			this.createJoinSessionQuery();
			break;
		case 1:
			logger.info("CreateJoinSessionBehaviour step 2: receiving response for creating/joining session " + this.sessionAgents[0]);
			this.receiveResponse();
			break;
		case 2:
			logger.info("CreateJoinSessionBehaviour step 3: analyzing response for creating/joining session " + this.sessionAgents[0]);
			this.joinSessionDone();
			break;
		case 3:
			logger.info("CreateJoinSessionBehaviour step 4: quitting session " + this.sessionAgents[0]);
			this.leaveSession();
			break;
		case 4:
			logger.info("CreateJoinSessionBehaviour step 5: analyzing response for leaving session " + this.sessionAgents[0]);
			this.leaveSessionDone();
			break;	
		default:
      break;
		}
	}
}
