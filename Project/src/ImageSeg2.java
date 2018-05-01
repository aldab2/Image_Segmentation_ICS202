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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.awt.Color;
import java.awt.image.*;
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
//import com.sun.javafx.geom.Edge
public class ImageSeg2 {
	
	//static int[][] greyPixels= {{10,2,4,7},{5,6,11,8},{9,13,3,2},{10,15,13,4}};
//static int[][] greyPixels = {{1,5,2},{4,9,6},{3,7,2}};
//	static int[][] greyPixels = new int[550][500];

public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> fourNeighbored;
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> eightNeighbored;	
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> forest;
public static HashMap<Integer, Integer> map;
public static ArrayList<Integer> anything = new ArrayList<>();

static int[][] coloeredPixels;
static int[][] greyPixels;
static int[][] greyNoisePixels;
static BufferedImage img = null;
static BufferedImage greyImage;
 static BufferedImage greyNoisedImage;

 
	public static void main(String...strings) throws IOException {
		
		//READING THE IMAGE
		 img = ImageIO.read(new File("Images/kitty.jpg")); // Grayscale Image output
		  greyImage =  ImageIO.read(new File("Images/kitty.jpg"));
		  greyNoisedImage=   ImageIO.read(new File("Images/kitty.jpg"));
		     int width = img.getWidth() ;
		    int height = img.getHeight() ;
		    coloeredPixels = new int[height][width] ;

		    greyPixels = getPixels(coloeredPixels);
		    greyPixels = toGreyScale(greyPixels);
		    greyImage =  setImageToPixels(greyPixels,greyImage);
		    
		    
		    greyNoisePixels = toGreyAndNoised(greyPixels);
		     
		     greyNoisedImage =setImageToPixels(greyNoisePixels,greyNoisedImage);
		    
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		fourNeighbored = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		forest = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		map = new HashMap<>();
		
		eightNeighbored =  new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(int i=0;i<(height * width);i++) {
			graph.addVertex(i);
			forest.addVertex(i);
		}
		
	
		fourNeighbored = fourNeighbored(graph);

	
		mst(fourNeighbored, 2);

			
		
		 
	    try {
	    	File outputFile = new File("GreyImage.png");
	    	ImageIO.write(greyImage, "png", outputFile);

	    	/*File outputFile2 = new File("GreyNoisedImage.png");
		    		ImageIO.write(greyNoisedImage, "png", outputFile2);*/
	    }
	    catch(IOException e) {
	    	
	    }
		
		
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




	public static void udpateMaping(HashMap<Integer, Integer> map) {
		int k=0;
		for(int i=0;i<greyPixels.length;i++)
			for(int j=0;j<greyPixels[i].length;j++,k++) {
				map.put(k, greyPixels[i][j]);
			}
	}
	
	//MST METHOD
	public static void mst(SimpleWeightedGraph<Integer, DefaultWeightedEdge> fourNeighbored, int R){
		MySpanningTree<Integer> mymst =   mstKruskal(fourNeighbored);

	
	for(int i=1;i<R;i++) {
		mymst.pop();
		}
		
		for(int i=0;i<mymst.stackOfEdges.size();i++) {
			fourNeighbored.removeAllEdges(mymst.nonEdges);
		}
		udpateMaping(map);
		customIteration(new DepthFirstIterator<>(fourNeighbored),new MyTravsersalListener<>(), customIteration(new DepthFirstIterator<>(fourNeighbored), new MyTravsersalListener<>()));
		
		
		for(int i = 0; i < greyPixels.length;i++) {
			for(int j = 0; j < greyPixels[i].length;j++) {
				greyPixels[i][j] = map.get(i);
				System.out.println(greyPixels[i][j]);

			}
		}
	}
	
	//RMST METHOD
	public static void rmst(SimpleWeightedGraph<Integer, DefaultWeightedEdge> fourNeighbored, int R){
		for(int i = (img.getHeight() * img.getWidth()) -2; i < R;i++ ) {
			MySpanningTree<Integer> mymst =   mstKruskal(fourNeighbored);
			
			for(int j=1;j<R;j++) {
				mymst.pop();
				}
				
				for(int k=0;k<mymst.stackOfEdges.size();k++) {
					fourNeighbored.removeAllEdges(mymst.nonEdges);
				}
				udpateMaping(map);
				customIteration(new DepthFirstIterator<>(fourNeighbored),new MyTravsersalListener<>(), customIteration(new DepthFirstIterator<>(fourNeighbored), new MyTravsersalListener<>()));
				
		}
		
		
		for(int i = 0; i < greyPixels.length;i++) {
			for(int j = 0; j < greyPixels[i].length;j++) {
				greyPixels[i][j] = map.get(i);
				System.out.println(greyPixels[i][j]);

			}
		}
	}
	
	
	public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> fourNeighbored(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		 int width = img.getWidth() ;
		    int height = img.getHeight() ;
		 
		int k=0;
		int j=0;
		for(int i=0;i<(width*height);i++) {
		
			
		//System.out.println(i+":"+j+":"+k);
			if(i < height) {
				// In the First Row
			if(i%height ==0) {
			//	System.out.println("1st Elem in the 1st Row");
				// 1st Elem in the Row
				
				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
				
				
				k++;
				
			}
			else if((i+1)% height ==0) {
				//System.out.println("last Elem in the 1st Row");
				// Last El in  the Row
				/*System.out.println("In this Stage i:"+i+" J:"+j+" K:"+k);
				graph.addOneWayEdge(i, Math.abs(greyPixels[j][i]-greyPixels[j][i-1]), i-1);
				graph.addOneWayEdge(i,Math.abs(greyPixels[j][i]-greyPixels[j+1][i]), i+height);*/
				
				
				graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][i]-greyPixels[j+1][i]));
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
			
				j++;
				k=0;
			}
			else {
				//System.out.println("Between 1st row ");
				// In Between but in the first row
				
				

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
			//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
			//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));

			
				k++;
			}
			}
			else if(i>=(width-1)*height) {
				
				// In the Last Row
				if(i%height ==0) {
					//System.out.println("1st Elem in the last Row");
					// 1st Elem in the Row
		
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
			//		System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				else if((i+1)% height ==0) {
					//System.out.println("Last Elem in the last Row");

					// Last El in  the Row
				
					
					
					j++;
					k=0;
					
					
				}
				else {
					//System.out.println("Between Elem in the last Row");

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
				//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				}
			else {

				// Between Fist Col And Last Col
				if(i%height ==0) {
				//	System.out.println("1st Elem in between");

					// 1st Elem in the Row
					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
					//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
				//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
					
					k++;
				}
				else if((i+1)% height ==0) {
				//	System.out.println("last Elem in between");
					// Last El in  the Row
					graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
				//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
					
					
					j++;	
					k=0;
					
				}
				else {
					//System.out.println("Between Elem in between");
					// In Between Explicitly
				
					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
					//System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
				//	System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
					
					
					k++;
				
			}
			}
			

			
		}
		return graph;
	}
	public static SimpleWeightedGraph<Integer, DefaultWeightedEdge> eightNeighbored(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		 int width = img.getWidth() ;
		    int height = img.getHeight() ;
		 
		int k=0;
		int j=0;
		for(int i=0;i<(width*height);i++) {
		
			
		//System.out.println(i+":"+j+":"+k);
			if(i < height) {
				// In the First Row
			if(i%height ==0) {
			//	System.out.println("1st Elem in the 1st Row");
				// 1st Elem in the Row
	
				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
				
				// Right Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+height+1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height+1)));
				
				
				
				k++;
				
			}
			else if((i+1)% height ==0) {
				//System.out.println("last Elem in the 1st Row");
				// Last El in  the Row
				
				graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][i]-greyPixels[j+1][i]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
				
				// Left Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+height-1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k-1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height-1)));
			
				j++;
				k=0;
			}
			else {
				//System.out.println("Between 1st row ");
				// In Between but in the first row
		
				

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
				
				graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
				
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
				// Right Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+height+1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height+1)));
				
				// Left Diagonal
				graph.setEdgeWeight(graph.addEdge(i, i+height-1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k-1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height-1)));

			
				k++;
			}
			}
			else if(i>=(width-1)*height) {
				
				// In the Last Row
				if(i%height ==0) {
					//System.out.println("1st Elem in the last Row");
					// 1st Elem in the Row

					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				else if((i+1)% height ==0) {
					//System.out.println("Last Elem in the last Row");

					// Last El in  the Row

					
					
					
					
					
					j++;
					k=0;
					
					
				}
				else {
					//System.out.println("Between Elem in the last Row");

					// In Between but in the last row

				graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
				System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					k++;
				}
				}
			else {

				// Between Fist Col And Last Col
				if(i%height ==0) {
				//	System.out.println("1st Elem in between");

					// 1st Elem in the Row

					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
					
					// Right Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+height+1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height+1)));
				
					
					k++;
				}
				else if((i+1)% height ==0) {
				//	System.out.println("last Elem in between");
					// Last El in  the Row

					
					graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
			
					// Left Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+height-1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k-1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height-1)));
					
					
					j++;	
					k=0;
					
				}
				else {
					//System.out.println("Between Elem in between");
					// In Between Explicitly

					
					graph.setEdgeWeight(graph.addEdge(i, i+1), Math.abs(greyPixels[j][k]-greyPixels[j][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+1)));
					
					graph.setEdgeWeight(graph.addEdge(i, i+height), Math.abs(greyPixels[j][k]-greyPixels[j+1][k]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height)));
					
					// Right Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+height+1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k+1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height+1)));
					// Left Diagonal
					graph.setEdgeWeight(graph.addEdge(i, i+height-1), Math.abs(greyPixels[j][k]-greyPixels[j+1][k-1]));
					System.out.println(graph.getEdgeWeight(graph.getEdge(i, i+height-1)));
					
					
					k++;
				
			}
			}
			

			
		}
		return graph;
	}
	
	
	//PHASE 1 STUFF
	
	public static BufferedImage setImageToPixels(int[][] pixels, BufferedImage img2) {
		for (int y=0 ; y < pixels.length ; y++)
		    for (int x=0 ; x < pixels[y].length ; x++) {
		    	img2.setRGB(x, y, pixels[y][x]);
		    	
		    }
		return img2;
	}
	
	public static int[][] getPixels(int[][] p) throws IOException {
		int [][] tmp = new int[p.length][p[0].length];
		for (int y=0 ; y < tmp.length ; y++)
	        for (int x=0 ; x < tmp[y].length ; x++) {
	        	tmp[y][x] = p[y][x];
	        }
		
		for (int y=0 ; y < tmp.length ; y++)
	        for (int x=0 ; x < tmp[y].length ; x++) {
	        	/*int r = img.getRaster().getSample(x, y, 0) ;
	            int g = img.getRaster().getSample(x, y, 1) ;
	            int b = img.getRaster().getSample(x, y, 2) ;
	            p[y][x] = r+g+b;
	            p[y][x] = (int)( 0.2125 * r + 0.7125 *  g + 0.0721 * b)  + noise();
	            if(p[y][x] < 0)
	            	p[y][x] = 0;
	            else if(p[y][x] > 255)
	            	p[y][x] = 255;
	            p[y][x] = (p[y][x]<<16)|(p[y][x]<<8)|(p[y][x]);*/
	        	tmp[y][x]= img.getRGB(x	, y);
	        	
	           
	            }

	    return tmp ;
	    
	}
	public static int[][] toGreyScale(int[][] p){
		int [][] tmp = new int[p.length][p[0].length];
		for (int y=0 ; y < tmp.length ; y++)
	        for (int x=0 ; x < tmp[y].length ; x++) {
	        	tmp[y][x] = p[y][x];
	        }
		for (int y=0 ; y <tmp.length ; y++)
	        for (int x=0 ; x < tmp[y].length ; x++) {
	        	 int r = (tmp[y][x] >> 16) & 0xFF;
	             int g = (tmp[y][x] >> 8) & 0xFF;
	             int b = (tmp[y][x] & 0xFF);
        tmp[y][x] = r+g+b;
        tmp[y][x] = (int)( 0.2125 * r + 0.7125 *  g + 0.0721 * b)  ;
       
        tmp[y][x] = (tmp[y][x]<<16)|(tmp[y][x]<<8)|(tmp[y][x]);
	        }
		return tmp;
	}
	
	public static int[][] toGreyAndNoised(int[][] p){
		int [][] tmp = new int[p.length][p[0].length];
		for (int y=0 ; y < tmp.length ; y++)
	        for (int x=0 ; x < tmp[y].length ; x++) {
	        	tmp[y][x] = p[y][x];
	        }
		
		
		for (int y=0 ; y < tmp.length ; y++)
	        for (int x=0 ; x < tmp[y].length ; x++) {
	        	int r = (tmp[y][x] >> 16) & 0xFF;
	             int g = (tmp[y][x] >> 8) & 0xFF;
	             int b = (tmp[y][x] & 0xFF);
	            tmp[y][x] = r+g+b;
	            tmp[y][x] = (int)( 0.2125 * r + 0.7125 *  g + 0.0721 * b)+noise()  ;
	           
	            if(tmp[y][x] < 0)
	            	tmp[y][x] = 0;
	            else if(tmp[y][x] > 255)
	            	tmp[y][x] = 255;
	            
	            tmp[y][x] = (tmp[y][x]<<16)|(tmp[y][x]<<8)|(tmp[y][x]);
	        }
		
		return tmp;
	}
	public static int noise() {
		return (int)(255*(Math.random()-0.5));
	}
}

