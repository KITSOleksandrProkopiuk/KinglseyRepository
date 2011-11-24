package com.u1s1f1.collabsys.user;

import java.util.logging.Logger;
import java.util.*;
/*import java.io.FileInputStream;*/

import jade.core.Agent;

public class UserAgent extends Agent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserAgent.class.getName());

	/**
	 * Required for serializable class
	 */
	private static final long serialVersionUID = -340538567617862151L;
	
			
	protected void setup() {
		logger.info("UserAgent " + this.getAID().getName() + " is ready");
		try {
			logger.info("User is connected ");
			this.runCreateJoinSession();
			
		}
		catch (Exception e) {
			logger.severe("Exception during retreiving user's credentials. Stopping");
			e.printStackTrace();
			this.doDelete();
		}
	}

	private void runCreateJoinSession() {
		this.addBehaviour(new CreateJoinSessionBehaviour(this, 5000));
	}

	protected void takeDown() {
		logger.info("UserAgent " + this.getAID().getName() + " terminating");
	}
	
}
