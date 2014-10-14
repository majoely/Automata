package automata;

import java.util.ArrayList;
import java.util.Collections;

public class DFAState {
	
	private ArrayList<Integer> states;
	private DFATransition[] transitions;
	
	public DFAState(ArrayList<Integer> s, int alphaSize) {
		transitions = new DFATransition[alphaSize];
		this.states = s;
		Collections.sort(this.states);
	}
	
	public void addTransition(DFATransition t, int place) {
		this.transitions[place] = t;
	}
	
	public ArrayList<Integer> getStates() {
		return this.states;
	}
	
	public DFATransition[] getTransitions() {
		return this.transitions;
	}
	
	@Override
	public String toString() {
		String response = "{ ";
		for (int i = 0; i < this.states.size(); i++) {
			response += this.states.get(i) + " ";
		}
		response += "} ";
		for (int i = 0; i < this.transitions.length; i++) {
			if (this.transitions[i] != null) {
				response += this.transitions[i].toString();
			} else {
				char c = (char) (97 + i);
				response += " -" + c + "-> {null}";
			}
		}
		return response;
	}

}
