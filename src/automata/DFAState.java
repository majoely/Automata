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
        
        public String getStateName() {
            StringBuilder sb = new StringBuilder();
            for (Integer state: states) {
                sb.append(state);
                sb.append(" ");
            }
            if (sb.length() == 0) {
                return "";
            }
            return sb.substring(0, sb.length()-1);
        }
	
	public DFATransition[] getTransitions() {
		return this.transitions;
	}
	
	@Override
	public String toString() {
		String response = "{ " + getStateName() + " }";
                
		for (int i = 0; i < this.transitions.length; i++) {
                    response += this.transitions[i].toString();
		}
		return response;
	}

}
