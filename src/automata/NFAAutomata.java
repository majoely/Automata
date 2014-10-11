package automata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NFAAutomata {
	
	private NFAState[] nodes;
	private char[] alpha;
	
	public NFAAutomata(int numNodes, int numAlpha) {
		// Create nodes
		nodes = new NFAState[numNodes];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new NFAState(i);
		}
		// Create edges
		alpha = new char[numAlpha];
		for (int i = 0; i < numAlpha; i++) {
			alpha[i] = (char) (i + 97);
		}
	}
	
	public void parseLine(String line, int stateNum) {
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
	
	@Override
	public String toString() {
                StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.length; i++) {
			sb.append(nodes[i].toString());
		}
		return sb.toString();
	}
        
	public WGraph draw() {
            WGraph graph = new WGraph(480, 480);
            for (int i = 0; i < nodes.length; i++) {
                graph.add(i);
            }
            for (NFAState node : nodes) {
                for (NFATransition transition: node.getTransitions()) {
                    int from = node.getStateNum();
                    int to = transition.getTo().getStateNum();
                    String label = "" + transition.getAlpha();
                    System.out.println("From:" + from + " To:" + to + " label:" + label);
                    if (!graph.addEdge(from, to, label)) {
                        graph.apendLabel(from, to, label);
                    }
                }
            }
            return graph;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//System.out.print("File name: ");
		//Scanner in = new Scanner(System.in);
		//String filePath = in.nextLine();
		System.out.println("OUT");
		File f = new File("./graph1.txt");
		//in.close();
		Scanner file = new Scanner(f);
		int numberOfNodes = Integer.parseInt(file.nextLine());
		int numberOfAlpha = Integer.parseInt(file.nextLine());
		NFAAutomata a = new NFAAutomata(numberOfNodes, numberOfAlpha);
		int i = 0;
		while (file.hasNext()) {
			String line = file.nextLine();
			a.parseLine(line, i++);
		}
		file.close();
		System.out.println(a.toString());
                a.draw();
		//System.out.println(a.toString());
        
	}
	
}
