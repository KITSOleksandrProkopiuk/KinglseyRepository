package com.kingsley.turing.state;

import java.util.ArrayList;
import java.util.List;

import com.kingsley.turing.Tape;
import com.kingsley.turing.state.row.StateRow;

public class State {

	private String stateName;
	private List<StateRow> rows;

	public State(String stateName) {
		this.stateName = stateName;
		rows = new ArrayList<StateRow>();
	}

	public void processCarriage(final Tape tape) {	
		
		StateRow row = getAppropriateRow(tape);	
		
		row.exequteCommand(tape);		
	}

	private StateRow getAppropriateRow(final Tape tape) {
		System.out.println("**************************************8");
		StateRow appropriateRow=null; 
		for (StateRow row : rows) {
			System.out.println("read : " + row.getReadCharacter());
			if (row.getReadCharacter().equalsIgnoreCase(tape.getCurrentPosition())) {
				appropriateRow = row;
			}
			System.out.println("app row" + appropriateRow);
			continue;
		}//throw "there is no row."
		return appropriateRow;
	}

	public void addStateRow(final StateRow stateRow) {
		rows.add(stateRow);
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<? extends StateRow> getRows() {
		return rows;
	}

	public void setRows(List<StateRow> rows) {
		this.rows = rows;
	}
}