package com.kingsley.turing.state.row;

import com.kingsley.turing.Tape;
import com.kingsley.turing.direction.Dirrection;

public class EndStateRow extends StateRow {

	public EndStateRow(String readCharacter, String writeCharacter, Dirrection dirrection) {
		super(readCharacter, writeCharacter, dirrection);
		
	}

	public EndStateRow() {
		super();
	}

	@Override
	public void exequteCommand(Tape tape) {
		super.exequteCommand(tape);
		tape.printTape();
	}
}
