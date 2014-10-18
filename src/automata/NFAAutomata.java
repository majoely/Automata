package automata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NFAAutomata {
	
	private NFAState[] nodes;
	private char[] alpha;
	
	private void setStates(int numStates) {
            // Create nodes
            nodes = new NFAState[numStates];
            for (int i = 0; i < nodes.length; i++) {
                    nodes[i] = new NFAState(i);
            }
	}
        
        private void setAlphabet(int numAlpha) {
            // Create edges
            alpha = new char[numAlpha];
            for (int i = 0; i < numAlpha; i++) {
                    alpha[i] = (char) (i + 97);
            }
	}
        
        public NFAAutomata(File file) throws FileNotFoundException, NumberFormatException {
            Scanner sc = new Scanner(file);
            int numberOfStates = Integer.parseInt(sc.nextLine());
            int numberOfAlpha = Integer.parseInt(sc.nextLine());
            setStates(numberOfStates);
            setAlphabet(numberOfAlpha);
            int i = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                parseLine(line, i++);
            }
            sc.close();
        }
	
	private void parseLine(String line, int stateNum) {
		String[] edges = line.split(" ");
		int i = 0;
		for (String s : edges) {
			if (s.matches(";")) {
				i++;
			} else {
				int to = Integer.parseInt(s);
				nodes[stateNum].addTransition(nodes[to], alpha[i]);
			}
		}
	}
	
	public char[] getAlpha() {
		return this.alpha;
	}
	
	public NFAState[] getStates() {
		return this.nodes;
	}
	
	@Override
	public String toString() {
                StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.length; i++) {
			sb.append(nodes[i].toString());
		}
		return sb.toString();
	}
        
	public WGraph draw(WGraph graph) {
            graph.clear();
            for (int i = 0; i < nodes.length; i++) {
                graph.add("" + i);
            }
            for (NFAState node : nodes) {
                for (NFATransition transition: node.getTransitions()) {
                    int from = node.getStateNum();
                    int to = transition.getTo().getStateNum();
                    String label = "" + transition.getAlpha();
                    System.out.println("From:" + from + " To:" + to + " label:" + label);
                    if (!graph.addEdge("" + from, "" + to, label)) {
                        graph.apendLabel("" + from, "" + to, label);
                    }
                }
            }
            return graph;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
            //System.out.print("File name: ");
            //Scanner in = new Scanner(System.in);
            //String filePath = in.nsextLine();
            System.out.println("OUT");
            File f = new File("./graph2.txt");
            //in.close();=
            NFAAutomata a = new NFAAutomata(f);
            System.out.println(a.toString());
            WGraph graph = new WGraph(480, 480);
            //a.draw(graph);
            DFAAutomata b = new DFAAutomata(a);
            b.draw(graph);
            System.out.println("\n" + b.toString());
            //System.out.println(a.toString());
	}
	
}
