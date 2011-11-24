package com.u1s1f1.collabsys.user;

import java.util.logging.Logger;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.*;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UseFloorBehaviour extends TickerBehaviour {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UseFloorBehaviour.class.getName());

	/**
	 * Required for serializable class
	 */
	private static final long serialVersionUID = 422222731776550002L;

	private AID[] floorAgents;
	private MessageTemplate template;
	private int step = 0;
	
	public boolean isFloorController = false;
		
	public UseFloorBehaviour(Agent agent, long interval) {
		super(agent, interval);		
	}

	private void updateFloorAgents() {
		logger.info("UseFloorBehaviour updating floors list");
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("floor");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			this.floorAgents = new AID[result.length];
			logger.info("There are " + result.length + " floor services available");
			for (int i = 0; i < result.length; ++i) {
				this.floorAgents[i] = result[i].getName();
			}
			logger.info("Successfuly updated floors list");
		}
		catch (FIPAException fe) {
			logger.severe("Error during update of floors list");
			fe.printStackTrace();
		}
	}
	
	private void createUseFloorQuery() {
		logger.info("UseFloorBehaviour is checking to access floor");

		this.updateFloorAgents();
		if (this.floorAgents == null || this.floorAgents.length == 0) {
			return;
		}
		
		logger.info("UseFloorBehaviour is sending PROPOSE to floor controller " + this.floorAgents[0]);
		ACLMessage query = new ACLMessage(ACLMessage.CFP);

		query.addReceiver(this.floorAgents[0]);
		
		query.setContent(String.valueOf(myAgent.getAID().getName()));
		query.setConversationId("query-access-floor");
		myAgent.send(query);
		this.template = MessageTemplate.and(MessageTemplate.MatchConversationId("query-access-floor"),
				MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
		this.step = 1;
	}
	
	private void receiveResponseUseFloor() {
		logger.info("UseFloorBehaviour is receiving answers for query to use floor " + this.floorAgents[0]);
		
		ACLMessage reply = myAgent.receive(this.template);
		if (reply != null) {
			if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
				logger.info("UseFloorBehaviour occupied floor " + this.floorAgents[0]);
				this.isFloorController = true;
				this.step = 2;
				return;
			}			
		} else {
			this.block();
		}
	}
	
	private void accessFloorDone() {
		logger.info("User " + myAgent.getAID().getName() + " occupied floor " + this.floorAgents[0]);	
		this.step = 3;	
	}

	private void suspendFloor() {
		logger.info("User " + myAgent.getAID().getName() + " suspends floor " + this.floorAgents[0]);
		ACLMessage query = new ACLMessage(ACLMessage.PROPOSE);

		query.addReceiver(this.floorAgents[0]);
		
		query.setContent(String.valueOf(myAgent.getAID().getName()));
		query.setConversationId("query-suspend-floor");
		myAgent.send(query);
		this.template = MessageTemplate.and(MessageTemplate.MatchConversationId("query-suspend-floor"),
				MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
		this.step = 4;
	}

	private void receiveResponseSuspendFloor() {
		logger.info("UseFloorBehaviour is receiving answers for query to suspend floor " + this.floorAgents[0]);
		
		ACLMessage reply = myAgent.receive(this.template);
		if (reply != null) {
			if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
				logger.info("UseFloorBehaviour suspended floor " + this.floorAgents[0]);
				this.step = 5;
				return;
			}			
		} else {
			this.block();
		}
	}

	private void resumeFloor() {
		logger.info("User " + myAgent.getAID().getName() + " suspends floor " + this.floorAgents[0]);
		ACLMessage query = new ACLMessage(ACLMessage.INFORM);

		query.addReceiver(this.floorAgents[0]);
		
		query.setContent(String.valueOf(myAgent.getAID().getName()));
		query.setConversationId("query-resume-floor");
		myAgent.send(query);
		this.template = MessageTemplate.and(MessageTemplate.MatchConversationId("query-resume-floor"),
				MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
		this.step = 6;
	}

	private void receiveResponseResumeFloor() {
		logger.info("UseFloorBehaviour is receiving answers for query to resume floor " + this.floorAgents[0]);
		
		ACLMessage reply = myAgent.receive(this.template);
		if (reply != null) {
			if (reply.getPerformative() == ACLMessage.CONFIRM) {
				logger.info("UseFloorBehaviour resumed floor " + this.floorAgents[0]);
				this.step = 7;
				return;
			}			
		} else {
			this.block();
		}
	}

	private void releaseFloor() {
		logger.info("User " + myAgent.getAID().getName() + " releases floor " + this.floorAgents[0]);
		ACLMessage query = new ACLMessage(ACLMessage.QUERY_IF);

		query.addReceiver(this.floorAgents[0]);
		
		query.setContent(String.valueOf(myAgent.getAID().getName()));
		query.setConversationId("query-release-floor");
		myAgent.send(query);
		this.template = MessageTemplate.and(MessageTemplate.MatchConversationId("query-release-floor"),
				MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
		this.step = 8;
	}

	private void receiveResponseReleaseFloor() {
		logger.info("UseFloorBehaviour is receiving answers for query to release floor " + this.floorAgents[0]);
		
		ACLMessage reply = myAgent.receive(this.template);
		if (reply != null) {
			if (reply.getPerformative() == ACLMessage.CONFIRM) {
				logger.info("UseFloorBehaviour released floor " + this.floorAgents[0]);
				logger.info("UseFloorBehaviour of " + myAgent.getAID().getName() + " is ceased");
				this.isFloorController = false;
				
				ACLMessage query = new ACLMessage(ACLMessage.CONFIRM);

        query.addReceiver(myAgent.getAID());
		
        query.setContent("FLOOR RELEASED!");
        query.setConversationId("floor-released");
        myAgent.send(query);
        
				myAgent.removeBehaviour(this);
				return;
			}			
		} else {
      logger.info("!!!Release Message Blocked!!!");
			this.block();
		}
	}
	
	protected void onTick() {
		switch(step) {
		case 0:
			logger.info("UseFloorBehaviour step 1: sending query to access the floor");
			this.createUseFloorQuery();
			break;
		case 1:
			logger.info("UseFloorBehaviour step 2: receiving response to access the floor " + this.floorAgents[0]);
			this.receiveResponseUseFloor();
			break;
		case 2:
			logger.info("UseFloorBehaviour step 3: analyzing response to access the floor" + this.floorAgents[0]);
			this.accessFloorDone();
			break;
		case 3:
			logger.info("UseFloorBehaviour step 4: suspending using floor" + this.floorAgents[0]);
			this.suspendFloor();
			break;
		case 4:
			logger.info("UseFloorBehaviour step 5: receiving response to suspend the floor " + this.floorAgents[0]);
			this.receiveResponseSuspendFloor();
			break;
		case 5:
			logger.info("UseFloorBehaviour step 6: resuming using floor" + this.floorAgents[0]);
			this.resumeFloor();
			break;
		case 6:
			logger.info("UseFloorBehaviour step 7: receiving response to resume the floor " + this.floorAgents[0]);
			this.receiveResponseResumeFloor();
			break;
		case 7:
			logger.info("UseFloorBehaviour step 8: releasing floor" + this.floorAgents[0]);
			this.releaseFloor();
			break;
		case 8:
			logger.info("UseFloorBehaviour step 9: receiving response to release the floor " + this.floorAgents[0]);
			this.receiveResponseReleaseFloor();			
			break;
		}
	}
}

