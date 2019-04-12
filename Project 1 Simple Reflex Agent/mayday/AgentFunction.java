/*
 * Class that defines the agent function.
 * 
 * Written by James P. Biagioni (jbiagi1@uic.edu)
 * for CS511 Artificial Intelligence II
 * at The University of Illinois at Chicago
 * 
 * Last modified 2/19/07 
 * 
 * DISCLAIMER:
 * Elements of this application were borrowed from
 * the client-server implementation of the Wumpus
 * World Simulator written by Kruti Mehta at
 * The University of Texas at Arlington.
 * 
 */

import java.util.Random;

class AgentFunction {
	
	// string to store the agent's name
	// do not remove this variable
	private String agentName = "Agent Smith";
	
	// all of these variables are created and used
	// for illustration purposes; you may delete them
	// when implementing your own intelligent agent
	private int[] actionTable;
	private boolean bump;
	private boolean glitter;
	private boolean breeze;
	private boolean stench;
	private boolean scream;
	private Random rand;

	public AgentFunction()
	{
		// for illustration purposes; you may delete all code
		// inside this constructor when implementing your 
		// own intelligent agent

		// this integer array will store the agent actions
		actionTable = new int[8];
				  
		actionTable[0] = Action.GO_FORWARD;
		actionTable[1] = Action.GO_FORWARD;
		actionTable[2] = Action.GO_FORWARD;
		actionTable[3] = Action.GO_FORWARD;
		actionTable[4] = Action.TURN_RIGHT;
		actionTable[5] = Action.TURN_LEFT;
		actionTable[6] = Action.GRAB;
		actionTable[7] = Action.SHOOT;
		
		// new random number generator, for
		// randomly picking actions to execute
		rand = new Random();
	}

	public int process(TransferPercept tp)
	{
		// To build your own intelligent agent, replace
		// all code below this comment block. You have
		// access to all percepts through the object
		// 'tp' as illustrated here:
		
		// read in the current percepts
		bump = tp.getBump();
		glitter = tp.getGlitter();
		breeze = tp.getBreeze();
		stench = tp.getStench();
		scream = tp.getScream();
		int nextAction = Action.GO_FORWARD;

		if (glitter == true) {
			// Glitter "True", Bump "True?False", Breeze "True?False", Stench "True?False", Scream "True?False?" - Includes 16 observations
			nextAction = Action.GRAB;
		} else if (bump == true) {
			// Glitter "False", Bump "True", Breeze "True?False", Stench "True?False", Scream "True?False?" - Includes 8 observations
			if (breeze == true) {
				nextAction = Action.NO_OP;
			}else if (stench == true) {
				nextAction = nextActionShootForwardTurn();
			}

			nextAction = nextActionTurnOrForward();
		} else if (breeze == true) {
			// Glitter "False", Bump "False", Breeze "True", Stench "True?False", Scream "True?False?" - Includes 4 observations
			if (scream == true) {
				nextAction = nextActionTurnOrForward();
			}else if (stench == true) {
				nextAction = Action.NO_OP;
			}else {
				nextAction = nextActionBreeze();
			}
		} else if (stench == true) {
			// Glitter "False", Bump "False", Breeze "False", Stench "True", Scream "True?False?" - Includes 2 observations
			nextAction = nextActionShootForwardTurn();
		} else if (scream = true) {
			// Glitter "False", Bump "False", Breeze "False", Stench "False", Scream "True" - Includes 1 observation
			nextAction = Action.GO_FORWARD;
		} else {
			// Glitter "False", Bump "False", Breeze "False", Stench "False", Scream "False" - Includes 1 observation
			nextAction = Action.GO_FORWARD;
		}
		
		// return action to be performed
	    return nextAction;	    
	}
	
	// public method to return the agent's name
	// do not remove this method
	public String getAgentName() {
		return agentName;
	}

	private int nextActionTurnOrForward() {
		int move = Action.TURN_RIGHT;
		switch(rand.nextInt(4)) {
			case 0: move = Action.TURN_RIGHT; break;
			case 1: move = Action.TURN_LEFT; break;
			case 2: move = Action.GO_FORWARD; break;
			case 3: move = Action.GO_FORWARD; break;
		}

		return move;
	}

	private int nextActionBreeze() {
		int move = Action.NO_OP;
		return move;
	}

	private int nextActionShootForwardTurn() {
		int move = Action.NO_OP;
		
		switch(rand.nextInt(4)) {
			case 0: move = Action.NO_OP; break;
			case 1: move = Action.NO_OP; break;
			case 2: move = Action.NO_OP; break;
			case 3: move = Action.SHOOT; break;
		}
		
		return move;
	}

}