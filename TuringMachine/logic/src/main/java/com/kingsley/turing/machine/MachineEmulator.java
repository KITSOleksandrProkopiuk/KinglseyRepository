package com.kingsley.turing.machine;

import com.kingsley.turing.Tape;
import com.kingsley.turing.state.State;

public class MachineEmulator {
	private Tape tape;
	private State startState;
	
	public void go(){
		startState.processCarriage(tape);
	}

	public Tape getTape() {
		return tape;
	}

	public void setTape(Tape tape) {
		this.tape = tape;
	}

	public State getStartState() {
		return startState;
	}

	public void setStartState(State startState) {
		this.startState = startState;
	}
}