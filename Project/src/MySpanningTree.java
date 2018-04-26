import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

public class MySpanningTree<T> {

	double weight;
	ArrayList<DefaultWeightedEdge>edges;
	int numOfEdges;
	double avg;
	
	public MySpanningTree(double weight,ArrayList<DefaultWeightedEdge>edges) {
		this.weight = weight;
		this.edges = edges;
		this.numOfEdges = edges.size();
		avg = weight/numOfEdges;
	
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ArrayList<DefaultWeightedEdge>getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<DefaultWeightedEdge>edges) {
		this.edges = edges;
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
	
	
}
