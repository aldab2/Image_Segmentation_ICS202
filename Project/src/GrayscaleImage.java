import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.awt.Color;
import java.awt.image.*;

import javax.imageio.ImageIO;

public class GrayscaleImage {
	static int[][] coloeredPixels;
	static int[][] greyPixels;
	static int[][] greyNoisePixels;
	static BufferedImage img = null;
	static BufferedImage greyImage;
	 static BufferedImage greyNoisedImage;
	public static void main(String...strings) throws IOException {
		  img = ImageIO.read(new File("Images/tm.png")); // Grayscale Image output
		  greyImage =  ImageIO.read(new File("Images/tm.png"));
		  greyNoisedImage=   ImageIO.read(new File("Images/tm.png"));
		    final int width = img.getWidth() ;
		    final int height = img.getHeight() ;
		    coloeredPixels = new int[height][width] ;
		    
		    coloeredPixels = getPixels(coloeredPixels);
		    greyPixels = toGreyScale(coloeredPixels);
		    greyImage =  setImageToPixels(greyPixels,greyImage);
		    
		    
		    greyNoisePixels = toGreyAndNoised(coloeredPixels);
		     
		     greyNoisedImage =setImageToPixels(greyNoisePixels,greyNoisedImage);
		    
		    try {
		    	File outputFile = new File("Images/GreyImage.jpg");
		    	ImageIO.write(greyImage, "jpg", outputFile);

		    	File outputFile2 = new File("Images/GreyNoisedImage.jpg");
			    		ImageIO.write(greyNoisedImage, "jpg", outputFile2);
		    }
		    catch(IOException e) {
		    	
		    }
		   
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
		int r = img.getRaster().getSample(x, y, 0) ;
        int g = img.getRaster().getSample(x, y, 1) ;
        int b = img.getRaster().getSample(x, y, 2) ;
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
	        	int r = img.getRaster().getSample(x, y, 0) ;
	            int g = img.getRaster().getSample(x, y, 1) ;
	            int b = img.getRaster().getSample(x, y, 2) ;
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