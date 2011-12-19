package com.kingsley.turing.state.row;

import com.kingsley.turing.Tape;
import com.kingsley.turing.direction.Dirrection;
import com.kingsley.turing.state.State;

public class StagingStateRow extends StateRow {



	private State folowingState;

	public StagingStateRow(String readCharacter, String writeCharacter, Dirrection dirrection) {
		super(readCharacter, writeCharacter, dirrection);

	}

	public StagingStateRow() {
		
	}

	@Override
	public String toString() {
		return "StagingStateRow [folowingState=" + folowingState + "]";
	}
	@Override
	public void exequteCommand(Tape tape) {		
		super.exequteCommand(tape);
		folowingState.processCarriage(tape);
	}

	public State getFolowingState() {
		return folowingState;
	}

	public void setFolowingState(State folowingState) {
		this.folowingState = folowingState;
	}
}
