package com.kingsley.turing.state.row;

import com.kingsley.turing.Tape;
import com.kingsley.turing.direction.Dirrection;

public abstract class StateRow {

	private String readCharacter;
	private String writeCharacter;
	private Dirrection dirrection;

	public StateRow(String readCharacter, String writeCharacter, Dirrection dirrection) {
		this.readCharacter = readCharacter;
		this.writeCharacter = writeCharacter;
		this.dirrection = dirrection;
	}

	@Override
	public String toString() {
		return "StateRow [dirrection=" + dirrection + ", readCharacter=" + readCharacter + ", writeCharacter="
				+ writeCharacter + "]";
	}

	public StateRow() {
	}

	public void exequteCommand(final Tape tape) {
		tape.counerInc();
		tape.setValue(getWriteCharacter());
		move(tape);
	}

	public void move(final Tape tape) {
		dirrection.move(tape);
	}

	public String getReadCharacter() {
		return readCharacter;
	}

	public void setReadCharacter(String readCharacter) {
		this.readCharacter = readCharacter;
	}

	public String getWriteCharacter() {
		return writeCharacter;
	}

	public void setWriteCharacter(String writeCharacter) {
		this.writeCharacter = writeCharacter;
	}

	public Dirrection getDirrection() {
		return dirrection;
	}

	public void setDirrection(Dirrection dirrection) {
		this.dirrection = dirrection;
	}

}
