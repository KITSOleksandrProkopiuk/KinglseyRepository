package com.kingsley.turing;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TapeTest {
	private static final String SETTED_SYMBL = "Added";
	private static final String TREE = "TREE";
	private static final String TWO = "Two";
	private static final String ONE = "One";
	private Tape tape;
	private LinkedList<String> tapeList;

	@Before
	public void setUp() {
		tapeList = new LinkedList<String>();
		tapeList.add(ONE);
		tapeList.add(TWO);
		tapeList.add(TREE);
		tape = new Tape(tapeList);
	}

	@Test
	public void shouldMovePointerToFirstPositionAtStart() {
		assertTrue("Start position of pointer is incorrect", ONE.equalsIgnoreCase(tape.getCurrentPosition()));
	}

	@Test
	public void shouldIterThroughtTapeToTheRight() {
		assertTrue("position of pointer is incorrect while iter to right", TWO.equals(tape.stepRight()));
		assertTrue(TWO.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to right", TREE.equals(tape.stepRight()));
		assertTrue(TREE.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer incorrect while iter to right and add nill ", Tape.NIL_VALUE.equals(tape.stepRight()));
		assertTrue(Tape.NIL_VALUE.equals(tape.getCurrentPosition()));
	}

	@Test
	public void shouldIterThroughtTapeToTheLeftFromRightEnd() {
		goToTheEndOfType();
		assertTrue("position of pointer is incorrect while iter", Tape.NIL_VALUE.equals(tape.stepLeft()));
		assertTrue(Tape.NIL_VALUE.equals(tape.getCurrentPosition()));

		assertTrue("position of pointer is incorrect while iter to right", TREE.equals(tape.stepLeft()));
		assertTrue(TREE.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to right", TWO.equals(tape.stepLeft()));
		assertTrue(TWO.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to right", ONE.equals(tape.stepLeft()));
		assertTrue(ONE.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter", Tape.NIL_VALUE.equals(tape.stepLeft()));	
		assertTrue(Tape.NIL_VALUE.equals(tape.getCurrentPosition()));
	}
	
	@Test
	public void shouldIterToTheLeftFromTheStart(){
		assertTrue("position of pointer is incorrect while iter to left from start", Tape.NIL_VALUE.equals(tape.stepLeft()));	
		assertTrue(Tape.NIL_VALUE.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to left from start", Tape.NIL_VALUE.equals(tape.stepLeft()));	
		assertTrue(Tape.NIL_VALUE.equals(tape.getCurrentPosition()));
	}
	
	@Test
	public void shouldIterIfChahgeDerivitive(){
		assertTrue("position of pointer is incorrect while iter to right", TWO.equals(tape.stepRight()));
		assertTrue(TWO.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to right", TREE.equals(tape.stepRight()));
		assertTrue(TREE.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to right", TWO.equals(tape.stepLeft()));
		assertTrue(TWO.equals(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to right", ONE.equals(tape.stepLeft()));
		assertTrue(ONE.equals(tape.getCurrentPosition()));		
	}
	
	@Test
	public void shouldSetCurrectSymblToJustAdded(){
		tape.setValue(SETTED_SYMBL);
		assertTrue(SETTED_SYMBL.equalsIgnoreCase(tape.getCurrentPosition()));
	}
	
	@Test
	public void shouldIterCorrectlyToRightIfSettetNewSymbl(){		
		tape.setValue(SETTED_SYMBL);
		assertTrue(SETTED_SYMBL.equalsIgnoreCase(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect if step right after setting", TWO.equals(tape.stepRight()));
		assertTrue(TWO.equals(tape.getCurrentPosition()));				
	}
	
	@Test
	public void shouldIterCorrectlyToLeftIfSettetNewalue(){		
		tape.setValue(SETTED_SYMBL);
		assertTrue(SETTED_SYMBL.equalsIgnoreCase(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect while iter to left from start", Tape.NIL_VALUE.equals(tape.stepLeft()));	
		assertTrue(Tape.NIL_VALUE.equals(tape.getCurrentPosition()));			
	}
	
	@Test
	public void shouldSetCorrectlyTwise(){
		tape.setValue(SETTED_SYMBL);
		assertTrue(SETTED_SYMBL.equalsIgnoreCase(tape.getCurrentPosition()));
		
		tape.setValue(SETTED_SYMBL);
		assertTrue(SETTED_SYMBL.equalsIgnoreCase(tape.getCurrentPosition()));
		
		assertTrue("position of pointer is incorrect if step right after setting", TWO.equals(tape.stepRight()));
		assertTrue(TWO.equals(tape.getCurrentPosition()));	
	}	

	private void goToTheEndOfType() {
		tape.stepRight();
		tape.stepRight();
		tape.stepRight();
		tape.stepRight();
	}
}
