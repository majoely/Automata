package automata;

public class NFATransition {
	
	private NFAState from;
	private NFAState to;
	private char alpha;
	
	public NFATransition(NFAState f, NFAState t, char a) {
		// Requires both the to and the from state just in case I want to do
		// reverse graph searching later.
		this.from = f;
		this.to = t;
		this.alpha = a;
	}
	
	public NFAState getFrom() {
		// For graph re-transversal
		return this.from;
	}
	
	public NFAState getTo() {
		// For graph transversal and display
		return this.to;
	}
	
	public char getAlpha() {
		// For display
		return this.alpha;
	}
	
	@Override
	public String toString() {
		// For text based debugging
		return "-" + this.alpha + "->" + this.to.getStateNum() + ",";
	}

}
