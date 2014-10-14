package automata;

import java.util.ArrayList;

public class DFATransition {
	
	private DFAState to;
	private DFAState from;
	private char alpha;
	
	public DFATransition(DFAState f, DFAState t, char a) {
		// Requires both the to and the from state just in case I want to do
		// reverse graph searching later.
		this.to = t;
		this.from = f;
		this.alpha = a;
	}
	
	public DFAState getFrom() {
		return this.from;
	}
	
	public DFAState getTo() {
		return this.to;
	}
	
	public char getAlpha() {
		return this.alpha;
	}
	
	@Override
	public String toString() {
		String response = " -" + this.alpha + "-> { ";
		ArrayList<Integer> toStates = this.to.getStates();
		for (int i = 0; i < toStates.size(); i++) {
			response += toStates.get(i) + " ";
		}
		return response += "} ";
	}

}
