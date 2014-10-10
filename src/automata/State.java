package automata;

import java.util.ArrayList;

public class State {
	
	private ArrayList<Transition> transition;
	private int stateNum;
	
	public State(int sn) {
		// Only need the number of the state to crate it
		this.stateNum = sn;
		transition = new ArrayList<>();
	}
	
	public void setTransition(State to, char a) {
		// Add transitions to the state
		transition.add(new Transition(this, to, a));
	}
	
	public int getStateNum() {
		// Get the state number, used for display and creation
		return this.stateNum;
	}
	
	public ArrayList<Transition> getTransition() {
		// Return the list of transition for graph traversal
		return this.transition;
	}
	
	// May add method to get individual transition
	
	@Override
	public String toString() {
		// For text based debugging
		String response = "" + stateNum + " : ";
		for (Transition t : transition) {
			response += " " + t.toString();
		}
		return response + "\n";
	}

}
