import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.jgrapht.*;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import com.sun.javafx.geom.Edge;


public class ImageSeg2 {
	
	//static int[][] coloeredPixels= {{10,2,4,7},{5,6,11,8},{9,13,3,2},{10,15,13,4}};
static int[][] coloeredPixels = {{1,5,2},{4,9,6},{3,7,2}};
//	static int[][] coloeredPixels = new int[550][500];
	static int rows = coloeredPixels.length;
	static int cols = coloeredPixels[0].length;
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> fourNeighbored;
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> eightNeighbored;	
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> forest;
public static HashMap<Integer, Integer> map;
public static ArrayList<Integer> anything = new ArrayList<>();

	public static void main(String...strings) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		fourNeighbored = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		forest = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		map = new HashMap<>();
		
		eightNeighbored =  new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(int i=0;i<(rows*cols);i++) {
			graph.addVertex(i);
			forest.addVertex(i);
		}
		
	//	graph.setEdgeWeight(graph.addEdge(0, 3), Math.abs(coloeredPixels[0][0]-coloeredPixels[0][1]));
	
	
	
		
		fourNeighbored = fourNeighbored(graph);

		//forest = fourNeighbored(graph);
		//System.out.println(fourNeighbored);
		
		

		//KruskalMinimumSpanningTree<Integer,DefaultWeightedEdge > mst = new KruskalMinimumSpanningTree<>(fourNeighbored); 
		int R= 4;
		long start = System.nanoTime() ;
		MySpanningTree<Integer> mymst =   mstKruskal(fourNeighbored);
		System.out.println(mymst.stackOfEdges);
		
	for(int i=1;i<R;i++) {
		System.out.println("Popped:"+mymst.pop());
		}
		
	
		
		for(int i=0;i<mymst.stackOfEdges.size();i++) {
			fourNeighbored.removeAllEdges(mymst.nonEdges);
		}
		
		udpateMaping(map);
		customIteration(new DepthFirstIterator<>(fourNeighbored),new MyTravsersalListener<>(), customIteration(new DepthFirstIterator<>(fourNeighbored), new MyTravsersalListener<>()));
System.out.println(map);
		
		//Collections.sort(mymst.edges,(int1,int2)-> Integer.valueOf(int1).compareTo(int2));
		//eightNeighbored = eightNeighbored(graph);
		
		
		
		/*for(int i=0;i<map.size();i++)
			System.out.println(map.get(i));*/
		
		//System.out.println(mst.getSpanningTree());

		
		System.out.println(mymst.weight + " Stack: "+mymst.stackOfEdges+"\n Non Edges: "+ mymst.nonEdges + "\n Forest edges: " + fourNeighbored.edgeSet());
		//System.out.println("Time is "+(System.nanoTime()-start)/1e9);
		


		
		
		
		
	}
	public static MySpanningTree<Integer> rmst(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph, int pixels, int reigons){
		MySpanningTree<Integer> mySpanningTree = new MySpanningTree<>(0, null,null);
		for( int i= pixels-2;i>reigons-1;i--) {
		 mySpanningTree=	mstKruskal(graph);
		 	for(int j=mySpanningTree.stackOfEdges.size();j>=i;j--) {
		 		 DefaultWeightedEdge edge = mySpanningTree.stackOfEdges.remove(j);
		 		 mySpanningTree.weight -= graph.getEdgeWeight(edge);
		 		 mySpanningTree.numOfEdges -=1;
		 		
		 		 		 		 
		 	}
		 	
		 	
		 
		}
		
		
		return mySpanningTree ;
	}  
	public static ArrayList<Integer>  customIteration(DepthFirstIterator<Integer, DefaultWeightedEdge> dfi,MyTravsersalListener<Integer, DefaultWeightedEdge> adapter) {
		ArrayList<Integer> averages = new ArrayList<>();
		Integer next;
		int sum=0;
		int avg= 0 ;
		int cnt = 0;
		
	    dfi.addTraversalListener(adapter);
	    double tmp = adapter.startOfNewTree;
	    while (dfi.hasNext()) {
	    	
	    	if(tmp==adapter.startOfNewTree) {
	    		sum+= map.get(dfi.next());
	    		cnt++;
	    	}
	    	else {
	    		avg = sum/cnt ; 
	    		averages.add(avg);
	    		sum = map.get(dfi.next());
	    		cnt = 1;
	    		System.out.println("AVG:"+avg);
	    	}
	     	System.out.println("Is End? : "+adapter.startOfNewTree);
	     	
	       
	   
	     	tmp = adapter.startOfNewTree;
    		

	    }
	    avg = sum/cnt ; 
	    System.out.println("AVG:"+avg);
	    averages.add(avg);
	    return averages;
	}
	
	public static void  customIteration(DepthFirstIterator<Integer, DefaultWeightedEdge> dfi,MyTravsersalListener<Integer, DefaultWeightedEdge> adapter, ArrayList<Integer> avgs) {
		ArrayList<Integer> averages = new ArrayList<>();
		int cnt = 0;
		int i=0;
	    dfi.addTraversalListener(adapter);
	    double tmp = adapter.startOfNewTree;
	    while (dfi.hasNext()) {
	    	if(cnt==0) {
	    		map.replace(map.get(dfi.next()), avgs.get(0));
	    		cnt++;
	    	}
	    	else
	    	if(tmp==adapter.startOfNewTree) {
	    		map.replace(map.get(dfi.next()), avgs.get(i));
	    		cnt++;
	    	}
	    	else {
	    		map.replace(dfi.next(), avgs.get(i));
	    		i++;
	    		cnt++;
	    		
	    	}
	    	
	    	if(i==averages.size())
	    		map.replace(map.get(dfi.next()), avgs.get(0));
	     	System.out.println("Is End? : "+adapter.startOfNewTree);

	       
	   
	     	tmp = adapter.startOfNewTree;
    		

	    }
		i++;
		
		//map.replace(dfi.next(), avgs.get(i));
	    //System.out.println("AVG:"+avg);
	//    averages.add(avg);
	    //return averages;
	}
	
	
	public static  MySpanningTree<Integer> mstKruskal(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph ){
		double weight = 0;
		
	//	ArrayList<Integer> vertexSet = new ArrayList<>(graph.vertexSet());
		DisjointSet disjointSet = new DisjointSet(graph.vertexSet());
		Stack<DefaultWeightedEdge> stackOfEdges = new Stack<>();
	
		ArrayList<DefaultWeightedEdge> edges = new ArrayList<>(graph.edgeSet());
		Collections.sort(edges, (e1,e2) -> Double.valueOf(graph.getEdgeWeight(e1)).compareTo(graph.getEdgeWeight(e2)));
		ArrayList<DefaultWeightedEdge> nonEdgelist = new ArrayList<>();
		
		
		for(DefaultWeightedEdge edge : edges) {
			
			Integer src = graph.getEdgeSource(edge);
			Integer target = graph.getEdgeTarget(edge);
			//System.out.println("1>>>");
			if(disjointSet.find(src).equals(disjointSet.find(target))) {
				//System.out.println("we are here");
				nonEdgelist.add(edge);
				continue;
			}
			//System.out.println("2>>>");
			disjointSet.union(src, target);
			stackOfEdges.push(edge);
			weight += graph.getEdgeWeight(edge);
			//System.out.println("3>>>");
			
		}
	
				
		MySpanningTree<Integer> spanningTree= new MySpanningTree(weight, nonEdgelist,stackOfEdges);
		Collections.sort(edges, (e1,e2) -> Double.valueOf(graph.getEdgeWeight(e1)).compareTo(graph.getEdgeWeight(e2)));
		return spanningTree; 
	}
/*	public static MySpanningTree<Integer> MSTVersion2 (SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph ){
		double weight = 0;
		SimpleWeightedGraph<Integer, DefaultWeightedEdge> spanningGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	//	ArrayList<Integer> vertexSet = new ArrayList<>(graph.vertexSet());
		Stack<DefaultWeightedEdge> stackOfEdges = new Stack<>();
		CycleDetector<Integer, DefaultWeightedEdge> cycleDetector = new CycleDetector<>(spanningGraph);
		
		ArrayList<DefaultWeightedEdge> edges = new ArrayList<>(graph.edgeSet());
		Collections.sort(edges, (e1,e2) -> Double.valueOf(graph.getEdgeWeight(e1)).compareTo(graph.getEdgeWeight(e2)));
		ArrayList<DefaultWeightedEdge> nonEdgelist = new ArrayList<>();
		
		
		for(DefaultWeightedEdge edge : edges) {
			Integer src = graph.getEdgeSource(edge);
			Integer target = graph.getEdgeTarget(edge);
			
			//System.out.println("1>>>");
			if(cycleDetector.detectCycles()) {
				//System.out.println("we are here");
				nonEdgelist.add(edge);
				continue;
			}
			//System.out.println("2>>>");
			spanningGraph.addEdge(src, target);
			stackOfEdges.push(edge);
			weight += graph.getEdgeWeight(edge);
			//System.out.println("3>>>");
			
		}
	
				MySpanningTree<Integer> spanningTree = new MySpanningTree<>(weight, nonEdgelist, stackOfEdges);
		
		return spanningTree; 
	}*/



	public static void udpateMaping(HashMap<Integer, Integer> map) {
		int k=0;
		for(int i=0;i<coloeredPixels.length;i++)
			for(int j=0;j<coloeredPixels[i].length;j++,k++) {
				map.put(k, coloeredPixels[i][j]);
			}
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
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
				
				
				k++;
				
			}
			else if((i+1)% cols ==0) {
				//System.out.println("last Elem in the 1st Row");
				// Last El in  the Row
				/*System.out.println("In this Stage i:"+i+" J:"+j+" K:"+k);
				graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][i]-coloeredPixels[j][i-1]), i-1);
				graph.addOneWayEdge(i,Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]), i+cols);*/
				
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]));
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
			
				j++;
				k=0;
			}
			else {
				//System.out.println("Between 1st row ");
				// In Between but in the first row
				
				

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
			//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
			//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));

			
				k++;
			}
			}
			else if(i>=(rows-1)*cols) {
				
				// In the Last Row
				if(i%cols ==0) {
					//System.out.println("1st Elem in the last Row");
					// 1st Elem in the Row
		
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
			//		System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
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
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				}
			else {

				// Between Fist Col And Last Col
				if(i%cols ==0) {
				//	System.out.println("1st Elem in between");

					// 1st Elem in the Row
					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					k++;
				}
				else if((i+1)% cols ==0) {
				//	System.out.println("last Elem in between");
					// Last El in  the Row
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					
					j++;	
					k=0;
					
				}
				else {
					//System.out.println("Between Elem in between");
					// In Between Explicitly
				
					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]));
					//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+cols), Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]));
				//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+cols)));
					
					
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

