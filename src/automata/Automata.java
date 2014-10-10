package automata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Automata {
	
	private State[] nodes;
	private char[] alpha;
	private WGraph g;
	
	public Automata(int numNodes, int numAlpha) {
		// Create nodes
		nodes = new State[numNodes];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new State(i);
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
				nodes[stateNum].setTransition(nodes[to], alpha[i]);
			}
		}
	}
	
	@Override
	public String toString() {
		String response = "";
		for (int i = 0; i < nodes.length; i++) {
			response += nodes[i].toString();
		}
		return response;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//System.out.print("File name: ");
		//Scanner in = new Scanner(System.in);
		//String filePath = in.nextLine();
		System.out.println("OUT");
		File f = new File("./graph.txt");
		//in.close();
		Scanner file = new Scanner(f);
		int numberOfNodes = Integer.parseInt(file.nextLine());
		int numberOfAlpha = Integer.parseInt(file.nextLine());
		Automata a = new Automata(numberOfNodes, numberOfAlpha);
		int i = 0;
		while (file.hasNext()) {
			String line = file.nextLine();
			a.parseLine(line, i++);
		}
		file.close();
		//System.out.println(a.toString());
        
	}
	
}