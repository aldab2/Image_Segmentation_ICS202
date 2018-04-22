import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;

public class ImageSegmentation {
	static BufferedImage img = null;
	static  int rows = 4;
	static  int cols = 4;
	static int[][] coloeredPixels= {{10,2,4,7},{5,6,11,8},{9,13,3,2},{10,15,13,4}};
	static WeightedGraph<Integer> graph;
	static WeightedGraph<Integer> fourWeightedGraph;
	
	public static void main (String...strings) throws DatatypeConfigurationException {
		
		fourWeightedGraph = new WeightedGraph<>();
		fourWeightedGraph=fourNeighbor();
graph = fourWeightedGraph;

		
		//getNode(0).doubleWayAdd(100, graph.nodes.get(1));		
		for(WeightedGraph<Integer>.Node n : fourWeightedGraph.nodes)
			System.out.println(n.edges);
		
		
		System.out.println("-----------------------------------");
		
		System.out.println(getNode(10).edges);
	
	}

	public static WeightedGraph<Integer> fourNeighbor() {
		WeightedGraph<Integer> graph = new WeightedGraph<>();
		int k=0;
		int j=0;
		for(int i=0;i<(rows*cols);i++) {
			System.out.println(i+":"+j+":"+k);
			if(i < cols) {
				// In the First Row
			if(i%cols ==0) {
				// 1st Elem in the Row
				
				graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][i]-coloeredPixels[j][i+1]), i+1);
				//getNode(i).add(Math.abs(coloeredPixels[j][i]-coloeredPixels[j][i+1]), getNode(i+1));
				graph.addOneWayEdge(i,Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]), i+cols);
				//getNode(i).add(Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]),getNode(i+cols));
	
				
				k++;
				
			}
			else if((i+1)% cols ==0) {
				// Last El in  the Row
				System.out.println("In this Stage i:"+i+" J:"+j+" K:"+k);
				graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][i]-coloeredPixels[j][i-1]), i-1);
				graph.addOneWayEdge(i,Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]), i+cols);
			
				j++;
				k=0;
			}
			else {
				// In Between but in the first row
				graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][i]-coloeredPixels[j][i+1]), i+1);
				graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][i]-coloeredPixels[j][i-1]), i-1);
				graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][i]-coloeredPixels[j+1][i]), i+cols);
			
				k++;
			}
			}
			else if(i>=(rows-1)*cols) {
				// In the Last Row
				if(i%cols ==0) {
					// 1st Elem in the Row
				
					graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]), i+1);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j-1][k]), i-cols);
					
					k++;
				}
				else if((i+1)% cols ==0) {
					// Last El in  the Row
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k-1]), i-1);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j-1][k]), i-cols);
					
					j++;
					k=0;
					
					
				}
				else {
					// In Between but in the last row
					graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]), i+1);
					graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k-1]), i-1);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j-1][k]), i-cols);
				
					k++;
				}
				}
			else {
				// Between Fist Col And Last Col
				if(i%cols ==0) {
					// 1st Elem in the Row
					
					graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]), i+1);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]), i+cols);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j-1][k]), i-cols);
					k++;
				}
				else if((i+1)% cols ==0) {
					// Last El in  the Row
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k-1]), i-1);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]), i+cols);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j-1][k]), i-cols);
					
					j++;	
					k=0;
					
				}
				else {
					// In Between Explicitly
					graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k+1]), i+1);
					graph.addOneWayEdge(i,  Math.abs(coloeredPixels[j][k]-coloeredPixels[j][k-1]), i-1);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j+1][k]), i+cols);
					graph.addOneWayEdge(i, Math.abs(coloeredPixels[j][k]-coloeredPixels[j-1][k]), i-cols);
					
					k++;
				
			}
			}
			

			
		}
		return graph;
	}
	
	public static WeightedGraph<Integer>.Node getNode(int index) {
		
		
		return graph.nodes.get(index);
	}
	
	public static BufferedImage segmentImage(int[][] greyPixels, int numOfSegments) {
		WeightedGraph<Integer> graph = new WeightedGraph<>();
		int k=0;
		
		return null;
	}
	
	
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
	        
	        	tmp[y][x]= img.getRGB(x	, y);
	        	
	           
	            }

	    return tmp ;
	    
	}
	
}
