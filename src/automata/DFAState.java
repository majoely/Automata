package automata;

import java.util.ArrayList;

public class DFAState {
	
	private int[] states;
	private ArrayList<DFATransition> transitions;
	
	public DFAState(int[] s) {
		this.states = s;
	}
	
	public void addTransition(DFATransition t) {
		this.transitions.add(t);
	}
	
	public int[] getStates() {
		return this.states;
	}
	
	public ArrayList<DFATransition> getTransitions() {
		return this.transitions;
	}
	
	@Override
	public String toString() {
		String response = "{ ";
		for (int i = 0; i < this.states.length; i++) {
			response += this.states[i] + " ";
		}
		response += "} ";
		for (DFATransition t : this.transitions) {
			response += t.toString();
		}
		return response;
	}

}
