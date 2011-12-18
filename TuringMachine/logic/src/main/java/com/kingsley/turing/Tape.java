package com.kingsley.turing;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Tape {

	public static final String NIL_VALUE = "Nothing";
	private List<String> tape;
	private ListIterator<String> iterator;
	private String currentPosition;
	private int count = 0;

	private boolean changeDerivitive = false;
	private boolean justRemoved = false;

	public Tape(final LinkedList<String> tape) {
		this.tape = tape;
		iterator = tape.listIterator();
		if (iterator.hasNext()) {
			stepRight();
		}
	}

	public String stepRight() {

		if (changeDerivitive) {
			iterator.next();
			changeDerivitive = false;
		}
		if (iterator.hasNext()) {

			currentPosition = iterator.next();
		} else {
			iterator.add(NIL_VALUE);
			iterator.previous();
			iterator.next();
			currentPosition = NIL_VALUE;
		}
		justRemoved = false;
		return currentPosition;
	}

	public String stepLeft() {

		if (!changeDerivitive) {
			iterator.previous();
			changeDerivitive = true;
		}

		if (iterator.hasPrevious()) {
			currentPosition = iterator.previous();
		} else {
			iterator.add(NIL_VALUE);
			currentPosition = NIL_VALUE;
		}

		justRemoved = false;
		return currentPosition;
	}

	public void setValue(final String value) {
		ifJustSet();

		iterator.remove();
		iterator.add(value);
		currentPosition = value;
		justRemoved = true;
	}

	public void counerInc(){
		count++;
	}

	public void printTape() {
		System.out.println("Tape.print");
		for (String check : tape) {
			System.out.println(check);
		}
	}

	private void ifJustSet() {
		if (justRemoved) {
			if (iterator.hasPrevious()) {
				iterator.previous();
			} else {
				stepLeft();
			}
			iterator.next();
		}
	}

	public List<String> getTape() {
		return tape;
	}

	public void setTape(List<String> tape) {
		this.tape = tape;
	}

	public ListIterator<String> getIterator() {
		return iterator;
	}

	public void setIterator(ListIterator<String> iterator) {
		this.iterator = iterator;
	}

	public String getCurrentPosition() {
		System.out.println("Currenct position : " + currentPosition);
		return currentPosition;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
