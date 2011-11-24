package com.u1s1f1.collabsys.floor;

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

public class FloorAgent extends Agent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FloorAgent.class.getName());

	/**
	 * Required for serializable class
	 */
	private static final long serialVersionUID = 5269711606355949789L;
	
	private Connection conn;
	
	protected void setup() {
		logger.info("FloorAgent " + this.getAID().getName() + "is ready");
		try {
			this.addBehaviour(new MaintainFloorBehaviour(this.getAID(), this));
			this.registerInDirectory();		
		} catch (Exception e) {
			logger.severe("Error. Stopping");
			this.doDelete();
		}
	}
	
	private void registerInDirectory() {
		logger.info("FloorAgent is registering in service directory");
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("floor");
		sd.setName("JADE-Floor");
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

}
