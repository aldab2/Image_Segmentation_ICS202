
import javax.xml.datatype.DatatypeConfigurationException;

public class DijkstraTest {

    public static void main(String[] args) throws DatatypeConfigurationException {
	
	WeightedGraph<Character> graph = new WeightedGraph<Character>();
	
	graph.addOneWayEdge('A', 4, 'B').addOneWayEdge('A',  2, 'C');
	graph.addOneWayEdge('C', 5, 'D').addOneWayEdge('A', 15, 'E');
	graph.addOneWayEdge('B', 1, 'D').addOneWayEdge('B', 10, 'E');
	graph.addOneWayEdge('D', 3, 'E').addOneWayEdge('D',  0, 'F');
	graph.addOneWayEdge('F', 2, 'D').addOneWayEdge('F',  4, 'H');
	graph.addOneWayEdge('G', 4, 'H');
		
	String path = graph.getShortestPath('A', 'E');
		
	System.out.println(path);
	
    }
}
