package com.kingsley.turing.direction;

import com.kingsley.turing.Tape;

public class LeftDirrection implements Dirrection{

	public void move(Tape tape) {
		tape.stepLeft();		
	}
}
