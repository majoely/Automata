package automata;

public class Transition {
	
	private State from;
	private State to;
	private char alpha;
	
	public Transition(State f, State t, char a) {
		// Requires both the to and the from state just in case I want to do
		// reverse graph searching later.
		this.from = f;
		this.to = t;
		this.alpha = a;
	}
	
	public State getFrom() {
		// For graph re-transversal
		return this.from;
	}
	
	public State getTo() {
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
