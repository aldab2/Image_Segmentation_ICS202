import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.jgrapht.graph.DefaultWeightedEdge;

public class MySpanningTree<T> {

	double weight;
	ArrayList<DefaultWeightedEdge>nonEdges;
	Stack<DefaultWeightedEdge> stackOfEdges;
	int numOfEdges;
	double avg;
	
	public MySpanningTree(double weight,ArrayList<DefaultWeightedEdge>nonEdges, Stack<DefaultWeightedEdge> stackOfEdges) {
		this.weight = weight;
		this.nonEdges = nonEdges;
		this.numOfEdges = nonEdges.size();
		avg = weight/numOfEdges;
		this.stackOfEdges = stackOfEdges; 
	
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ArrayList<DefaultWeightedEdge>getEdges() {
		return nonEdges;
	}

	public void setEdges(ArrayList<DefaultWeightedEdge>nonEdges) {
		this.nonEdges = nonEdges;
	}

	public int getNumOfEdges() {
		return numOfEdges;
	}

	public void setNumOfEdges(int numOfEdges) {
		this.numOfEdges = numOfEdges;
	}
	public double getAvg() {
		avg = weight/numOfEdges;
		return avg;
	}
	
	public DefaultWeightedEdge pop() {
		nonEdges.add(this.stackOfEdges.peek());
		
		 return stackOfEdges.pop();
	}
	
}
