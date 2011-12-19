package com.kingsley.turing;

import java.util.LinkedList;

import com.kingsley.turing.direction.Dirrection;
import com.kingsley.turing.direction.RightDirrection;
import com.kingsley.turing.direction.StayDirrection;
import com.kingsley.turing.machine.MachineEmulator;
import com.kingsley.turing.state.State;
import com.kingsley.turing.state.row.EndStateRow;
import com.kingsley.turing.state.row.StagingStateRow;
import com.kingsley.turing.state.row.StateRow;

public class Runner {

	public static void main(String[] args) {
		LinkedList<String> testTapeList = new LinkedList<String>();
		testTapeList.add("A");
		testTapeList.add("A");
		testTapeList.add("A");

		Tape tape = new Tape(testTapeList);

		MachineEmulator turing = new MachineEmulator();
		turing.setTape(tape);

		Dirrection rightDirrection = new RightDirrection();
		Dirrection stayDirrection = new StayDirrection();

		State stateOne = new State("S1");
		StagingStateRow stateOneRow1 = new StagingStateRow("A", "B", rightDirrection);
		stateOneRow1.setFolowingState(stateOne);
		StateRow stateOneRow2 = new EndStateRow(Tape.NIL_VALUE, Tape.NIL_VALUE, stayDirrection);
		stateOne.addStateRow(stateOneRow1);
		stateOne.addStateRow(stateOneRow2);

		State startState = new State("S0");
		StagingStateRow state0Row1 = new StagingStateRow("A", "a", stayDirrection);
		state0Row1.setFolowingState(stateOne);
		startState.addStateRow(state0Row1);

		turing.setStartState(startState);
		turing.go();

	}
}
