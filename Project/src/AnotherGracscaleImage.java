import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.awt.Color;
import java.awt.image.*;

import javax.imageio.ImageIO;

public class AnotherGracscaleImage {
	static int[][] pixels;
	static BufferedImage img = null;
	public static void main(String...strings) throws IOException {
		  img = ImageIO.read(new File("Images/im16.jpg")); // Grayscale Image output
		    final int width = img.getWidth() ;
		    final int height = img.getHeight() ;
		    pixels = new int[height][width] ;
		    
		    pixels = readimage(pixels);
		    
		    for (int y=0 ; y < pixels.length ; y++)
		        for (int x=0 ; x < pixels[y].length ; x++) {
		        	img.setRGB(x, y, pixels[y][x]);
		        	
		        }
		    
		    try {
		    	/*File outputFile = new File("Images/pleasework.jpg");
		    	ImageIO.write(img, "jpg", outputFile);*/
		    	 makeGray(img);
		    }
		    catch(Exception e) {
		    	
		    }
		   
		   
	}
	
	public static int[][] readimage(int[][] p) throws IOException {
		for (int y=0 ; y < p.length ; y++)
	        for (int x=0 ; x < p[y].length ; x++) {
	        	
	        	
	        	
	        	
	        	
	            int r = img.getRaster().getSample(x, y, 0) ;
	            int g = img.getRaster().getSample(x, y, 1) ;
	            int b = img.getRaster().getSample(x, y, 2) ;
	            p[y][x] = 1*(r + g + b) /3 ;
	            
	            r = p[y][x]; 
	            g = p[y][x]; 
	            b = p[y][x]; 
	            }

	    return p ;
	    
	}
	public static void makeGray(BufferedImage img)
	{
	    for (int x = 0; x < img.getWidth(); ++x)
	    for (int y = 0; y < img.getHeight(); ++y)
	    {
	        int rgb = img.getRGB(x, y);
	        int r = (rgb >> 16) & 0xFF;
	        int g = (rgb >> 8) & 0xFF;
	        int b = (rgb & 0xFF);

	        int grayLevel = (r + g + b) / 3;
	        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel; 
	        img.setRGB(x, y, gray);
	        try {
		    	File outputFile = new File("Images/pleaseworkfsdfds.jpg");
		    	ImageIO.write(img, "jpg", outputFile);
		    	 
		    }
		    catch(Exception e) {
		    	
		    }
	    }
	}
}