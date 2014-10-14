package automata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class DFAAutomata {
	
	private ArrayList<DFAState> nodes;
	private char[] alpha;
	
	public DFAAutomata(NFAAutomata auto) {
		// Set up class variables
		this.alpha = auto.getAlpha();
		nodes = new ArrayList<>();
		// Get states for building the automata
		NFAState[] nstates = auto.getStates();
		// Create the first element and add to the automata
		ArrayList<Integer> stateList = new ArrayList<>();
		stateList.add(nstates[0].getStateNum());
		DFAState first = new DFAState(stateList, this.alpha.length);
		nodes.add(first);
		// Create a queue for searching through the tree and add first
		Queue<DFAState> list = new LinkedList<>();
		list.add(first);
		// Continue searching until there are no more elements to explore
		while (!list.isEmpty()) {
			DFAState cur = list.remove();
			// Get all the NFAStates in the current DFAState
			ArrayList<Integer> curStates = cur.getStates();

			for (Integer x : curStates) {
				System.out.print(x + " ");
			}
			System.out.println();
			// Search out from the states for each letter in the alphabet
			for (int i = 0; i < this.alpha.length; i++) {
				// A set for catching the next lot of states for each letter
				Set<Integer> nextStates = new TreeSet<>();
				for (int j = 0; j < curStates.size(); j++) {
					System.out.println("i: " + i + " j: " + j);
					System.out.println("s: " + this.alpha.length + " s: " + curStates.size());
					// Check if the edges are labed with the with the current
					// letter and add if so add to set
					ArrayList<NFATransition> trans = nstates[curStates.get(j)].getTransitions();
					for (NFATransition tran : trans) {
						if (tran.getAlpha() == this.alpha[i])
							nextStates.add(tran.getTo().getStateNum());
					}
				}
				// Check if new state !exists in the automata
				ArrayList<Integer> nextStatesList = new ArrayList<>(nextStates);
				DFAState to = exists(nextStatesList);
				if (to == null) {
					System.out.println("New state " + this.nodes.size());
					DFAState newState = new DFAState(nextStatesList, this.alpha.length);
					DFATransition newTransition = new DFATransition(cur, newState, this.alpha[i]);
					cur.addTransition(newTransition, i);
					this.nodes.add(newState);
					list.add(newState);
				} else {
					DFATransition newTransition = new DFATransition(cur, to, this.alpha[i]);
					cur.addTransition(newTransition, i);
				}
					// If true add it to the list and the automata
			}
		}
	}
	
	private DFAState exists(ArrayList<Integer> n) {
		//this needs to be implemented
		Collections.sort(n);
		for (DFAState d : this.nodes) {
			if (n.equals(d.getStates()))
				return d;
		}
		return null;
	}
	
	@Override
	public String toString() {
		String result = "DFA AUTOMATA\n";
		for (int i = 0; i < this.nodes.size(); i++) {
			result += this.nodes.get(i).toString() + "\n";
		}
		return result;
	}

}
