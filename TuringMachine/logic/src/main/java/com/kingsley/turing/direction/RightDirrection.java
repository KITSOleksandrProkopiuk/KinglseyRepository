package com.kingsley.turing.direction;

import com.kingsley.turing.Tape;

public class RightDirrection implements Dirrection {

	public void move(Tape tape) {
		tape.stepRight();
	}
}
