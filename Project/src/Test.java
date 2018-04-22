

import java.util.Scanner;

import javax.xml.datatype.DatatypeConfigurationException;

public class Test {

    public static void main(String[] args) {
	
	WeightedGraph<Character> graph = new WeightedGraph<>();
	
	graph.addTwoWayEdge('A',  8, 'B').addTwoWayEdge('A', 10, 'C');
	
	graph.addTwoWayEdge('A', 13, 'D').addTwoWayEdge('C', 13, 'D');

	graph.addTwoWayEdge('D', 12, 'E').addTwoWayEdge('C', 12, 'F');

	graph.addTwoWayEdge('D', 11, 'G').addTwoWayEdge('F',  7, 'G');

	graph.addTwoWayEdge('F',  5, 'E').addTwoWayEdge('B', 11, 'D');
	
	graph.addTwoWayEdge('F', 30, 'H');
	
	
	System.out.print("Enter your start position: ");
	char start = charInput();
	
	System.out.print("Enter your end position: ");
	char end = charInput();
//	
//	try {
//	    int prim = graph.prim(start);
//	    System.out.println("Prim: " + prim);
//	    System.out.println(graph.mst());
//	} catch (DatatypeConfigurationException e) {
//	    e.printStackTrace();
//	}
	
	
	try {
	    System.out.println(
	    	graph.getShortestPath(start, end) );
	} catch (DatatypeConfigurationException e) {
	    e.printStackTrace();
	}
	
    }
    
    static char charInput() {
	
	Scanner input = new Scanner(System.in);
	
	boolean notValid = true;
	
	while (notValid) {
	    try {
		notValid = false;
		return input.nextLine().charAt(0);
	    } catch (Exception e) {
		notValid = true;
	    }
	}
	input.close();
	throw new IllegalStateException();
    }
}
