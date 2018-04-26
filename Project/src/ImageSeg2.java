import java.util.HashMap;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.sun.javafx.geom.Edge;


public class ImageSeg2 {
	
	//static int[][] coloeredPixels= {{10,2,4,7},{5,6,11,8},{9,13,3,2},{10,15,13,4}};
	static int[][] coloeredPixels = {{1,5,2},{4,9,6},{3,7,2}};
	static int rows = 3;
	static int cols = 3;
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> fourNeighbored;
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> eightNeighbored;	
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;

	public static void main(String...strings) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		fourNeighbored = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	
		
		eightNeighbored =  new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(int i=0;i<(rows*cols);i++) {
			graph.addVertex(i);
			
		}
		
	//	graph.setEdgeWeight(graph.addEdge(0, 3), Math.abs(coloeredPixels[0][0]-coloeredPixels[0][1]));
	
	
	
		
		
		
		
		
		
		fourNeighbored = fourNeighbored(graph);
		
		
		//eightNeighbored = eightNeighbored(graph);
		HashMap<Integer, Integer> map = new HashMap<>();
		int k=0;
		for(int i=0;i<coloeredPixels.length;i++)
			for(int j=0;j<coloeredPixels[i].length;j++,k++) {
				map.put(k, coloeredPixels[i][j]);
			}
		
	for(int i=0;i<cols*rows;i++)
		System.out.println(map.get(i));
		System.out.println(fourNeighbored);

		
		
		
		
	}
	
	
	
	public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> fourNeighbored(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		
		 
		int k=0;
		int j=0;
		for(int i=0;i<(rows*cols);i++) {
		
			
		//System.out.println(i+":"+j+":"+k);
			if(i < cols) {
				// In the First Row
			if(i%cols ==0) {
			//	System.out.println("1st Elem in the 1st Row");
				// 1st Elem in the Row
				
				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
				
				
				k++;
				
			}
			else if((i+1)% cols ==0) {
				//System.out.println("last Elem in the 1st Row");
				// Last El in  the Row
				/*System.out.println("In this Stage i:"+i+" J:"+j+" K:"+k);
				graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][i]-coloeredPixels[j][i-1]), i-1);
				graph.addOneWayEdge(i,Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]), i+cols);*/
				
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
			
				j++;
				k=0;
			}
			else {
				//System.out.println("Between 1st row ");
				// In Between but in the first row
				
				

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));

			
				k++;
			}
			}
			else if(i>=(rows-1)*cols) {
				
				// In the Last Row
				if(i%cols ==0) {
					//System.out.println("1st Elem in the last Row");
					// 1st Elem in the Row
		
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				else if((i+1)% cols ==0) {
					//System.out.println("Last Elem in the last Row");

					// Last El in  the Row
				
					
					
					j++;
					k=0;
					
					
				}
				else {
					//System.out.println("Between Elem in the last Row");

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				}
			else {

				// Between Fist Col And Last Col
				if(i%cols ==0) {
				//	System.out.println("1st Elem in between");

					// 1st Elem in the Row
					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					k++;
				}
				else if((i+1)% cols ==0) {
				//	System.out.println("last Elem in between");
					// Last El in  the Row
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					
					j++;	
					k=0;
					
				}
				else {
					//System.out.println("Between Elem in between");
					// In Between Explicitly
				
					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					
					k++;
				
			}
			}
			

			
		}
		return graph;
	}
	public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> eightNeighbored(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		
		 
		int k=0;
		int j=0;
		for(int i=0;i<(rows*cols);i++) {
		
			
		//System.out.println(i+":"+j+":"+k);
			if(i < cols) {
				// In the First Row
			if(i%cols ==0) {
			//	System.out.println("1st Elem in the 1st Row");
				// 1st Elem in the Row
	
				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
				
				// Right Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+cols+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols+1)));
				
				
				
				k++;
				
			}
			else if((i+1)% cols ==0) {
				//System.out.println("last Elem in the 1st Row");
				// Last El in  the Row
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
				
				// Left Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+cols-1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k-1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols-1)));
			
				j++;
				k=0;
			}
			else {
				//System.out.println("Between 1st row ");
				// In Between but in the first row
		
				

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
				// Right Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+cols+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols+1)));
				
				// Left Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+cols-1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k-1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols-1)));

			
				k++;
			}
			}
			else if(i>=(rows-1)*cols) {
				
				// In the Last Row
				if(i%cols ==0) {
					//System.out.println("1st Elem in the last Row");
					// 1st Elem in the Row

					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				else if((i+1)% cols ==0) {
					//System.out.println("Last Elem in the last Row");

					// Last El in  the Row

					
					
					
					
					
					j++;
					k=0;
					
					
				}
				else {
					//System.out.println("Between Elem in the last Row");

					// In Between but in the last row

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				}
			else {

				// Between Fist Col And Last Col
				if(i%cols ==0) {
				//	System.out.println("1st Elem in between");

					// 1st Elem in the Row

					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					// Right Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+cols+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols+1)));
				
					
					k++;
				}
				else if((i+1)% cols ==0) {
				//	System.out.println("last Elem in between");
					// Last El in  the Row

					
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
			
					// Left Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+cols-1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k-1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols-1)));
					
					
					j++;	
					k=0;
					
				}
				else {
					//System.out.println("Between Elem in between");
					// In Between Explicitly

					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					// Right Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+cols+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols+1)));
					// Left Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+cols-1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k-1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols-1)));
					
					
					k++;
				
			}
			}
			

			
		}
		return graph;
	}
	
}

