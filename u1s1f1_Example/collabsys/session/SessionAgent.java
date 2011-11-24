package com.u1s1f1.collabsys.session;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.*;

public class SessionAgent extends Agent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SessionAgent.class.getName());

	/**
	 * Required for serializable class
	 */
	private static final long serialVersionUID = 5269711606355949789L;
	
	private Connection conn;
	
	protected void setup() {
		logger.info("SessionAgent " + this.getAID().getName() + "is ready");
		try {
			this.addBehaviour(new MaintainSessionBehaviour(this.getAID(), this));
			this.registerInDirectory();
		} catch (Exception e) {
			logger.severe("Error. Stopping");
			this.doDelete();
		}
		
	}

	private void registerInDirectory() {
		logger.info("SessionAgent is registering in service directory");
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("session");
		sd.setName("JADE-Session");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			logger.info("Directory registration is successful");
		}
		catch (FIPAException fe) {
			logger.severe("Can't register SessionAgent in directory");
			fe.printStackTrace();
		}
	}
	
	protected void takeDown() {
		logger.info("SessionAgent " + this.getAID().getName() + " is terminating.");
	}	

}
