package automata;

import java.util.ArrayList;

public class NFAState {
	
	private ArrayList<NFATransition> transitions;
	private int stateNum;
	
	public NFAState(int sn) {
		// Only need the number of the state to crate it
		this.stateNum = sn;
		transitions = new ArrayList<>();
	}
	
	public void addTransition(NFAState to, char a) {
		// Add transitions to the state
		transitions.add(new NFATransition(this, to, a));
	}
	
	public int getStateNum() {
		// Get the state number, used for display and creation
		return this.stateNum;
	}
	
	public ArrayList<NFATransition> getTransitions() {
		// Return the list of transition for graph traversal
		return this.transitions;
	}
	
	// May add method to get individual transition
	
	@Override
	public String toString() {
		// For text based debugging
		String response = "" + stateNum + " : ";
		for (NFATransition t : transitions) {
			response += " " + t.toString();
		}
		return response + "\n";
	}

}
