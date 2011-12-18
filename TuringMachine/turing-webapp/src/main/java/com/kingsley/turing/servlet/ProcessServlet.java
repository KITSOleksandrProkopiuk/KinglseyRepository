package com.kingsley.turing.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingsley.turing.Tape;
import com.kingsley.turing.direction.Dirrection;
import com.kingsley.turing.direction.LeftDirrection;
import com.kingsley.turing.direction.RightDirrection;
import com.kingsley.turing.direction.StayDirrection;
import com.kingsley.turing.machine.MachineEmulator;
import com.kingsley.turing.state.State;
import com.kingsley.turing.state.row.EndStateRow;
import com.kingsley.turing.state.row.StagingStateRow;
import com.kingsley.turing.state.row.StateRow;

public class ProcessServlet extends HttpServlet {
	private static final long serialVersionUID = -8210096599521399008L;
	private static final String START_NAME = "_startName";
	private static final String TO_READ = "_to_read";
	private static final String TO_WRITE = "_to_write";
	private static final String DIRECTION = "_direction";
	private static final String NEXT_STATE = "_next_state";

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<String, String> mapRequestParam = new TreeMap<String, String>(req.getParameterMap());
			Map<String, State> statesMap = new HashMap<String, State>();

			for (int i = 0; i < mapRequestParam.size() / 5; i++) {
				generateStates(req, statesMap, i + 2);
			}

			if (!statesMap.isEmpty()) {
				MachineEmulator turing = createMachine(req);
				State startState = statesMap.get(req.getParameter(2 + START_NAME));
				turing.setStartState(startState);
				turing.go();

				req.setAttribute("result_tape", turing.getTape().getTape());
				req.setAttribute("counter", turing.getTape().getCount());
			}

		} catch (Exception e) {
			req.setAttribute("error", "Error occured while processing");

		} finally {
			try {
				req.getRequestDispatcher("index.jsp").forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void generateStates(HttpServletRequest req, Map<String, State> statesMap, int i) {
		String nextStateParam = i + NEXT_STATE;
		String stateNameParam = i + START_NAME;
		String toReadParam = i + TO_READ;
		String toWriteParam = i + TO_WRITE;
		String directionParam = i + DIRECTION;

		if (req.getParameter(nextStateParam).equalsIgnoreCase("")) {
			StateRow stateRow = new EndStateRow();
			stateRow.setReadCharacter(req.getParameter(toReadParam));
			stateRow.setWriteCharacter(req.getParameter(toWriteParam));
			stateRow.setDirrection(findDirection(req.getParameter(directionParam)));
			State state = findState(req.getParameter(stateNameParam), statesMap);
			state.addStateRow(stateRow);

		} else {
			StagingStateRow stateRow = new StagingStateRow();
			State nextState = findState(req.getParameter(nextStateParam), statesMap);
			stateRow.setFolowingState(nextState);
			stateRow.setReadCharacter(req.getParameter(toReadParam));
			stateRow.setWriteCharacter(req.getParameter(toWriteParam));
			stateRow.setDirrection(findDirection(req.getParameter(directionParam)));
			State state = findState(req.getParameter(stateNameParam), statesMap);
			state.addStateRow(stateRow);
		}
	}

	private MachineEmulator createMachine(HttpServletRequest req) {
		MachineEmulator turing = new MachineEmulator();
		LinkedList<String> tapeList = new LinkedList<String>();

		String httpTape = req.getParameter("tape");
		StringTokenizer stringTokenizer = new StringTokenizer(httpTape, " ");
		while (stringTokenizer.hasMoreTokens()) {
			tapeList.add(stringTokenizer.nextToken());
		}

		Tape tape = new Tape(tapeList);
		tape.setTape(tapeList);

		turing.setTape(tape);

		return turing;
	}

	private State findState(String name, Map<String, State> map) {
		State state = null;
		if (map.containsKey(name)) {
			state = map.get(name);
		} else {
			state = new State(name);
			map.put(name, state);
		}
		return state;
	}

	private Dirrection findDirection(String directionName) {
		Dirrection dirrection = null;

		if (directionName.equalsIgnoreCase("Right")) {
			dirrection = new RightDirrection();
		} else {
			if (directionName.equalsIgnoreCase("Left")) {
				dirrection = new LeftDirrection();
			} else {
				if (directionName.equalsIgnoreCase("Stay")) {
					dirrection = new StayDirrection();
				}
			}
		}
		return dirrection;
	}
}
