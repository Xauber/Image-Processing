// BV Ue4 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-23
 		   		     	

package bv_ws2122;

import java.util.Arrays;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Histogram {
 		   		     	
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    private int maxHeight;
    
    private int pixelCount;
    
    private int[] histogram = new int[grayLevels];
 		   		     	
    public Histogram() {
	}
    
	public Histogram(GraphicsContext gc, int maxHeight) {
		this.gc = gc;
		this.maxHeight = maxHeight;
	}
	
	public int[] getValues() {
		return histogram;
	}

	public void setImageRegion(RasterImage image, int regionStartX, int regionStartY, int regionWidth, int regionHeight) {
		
		// TODO: calculate histogram[] out of the gray values found the given image region
		Arrays.fill(histogram, 0);
		
		for (int y=regionStartY; y<regionHeight + regionStartY; y++) {
			for (int x=regionStartX; x<regionWidth + regionStartX; x++) {
				int pos = y*image.width + x;
				
				int value =  image.argb[y*image.width + x]        & 0xff;
				histogram[value] = histogram[value] + 1;
			}
		}
		
		pixelCount = regionWidth * regionHeight;

	}
	
	public Integer getMinimum() {
		// Will be used in Exercise 5.
		Integer iReturn;
		int i = 0;
		if(histogram[i]==0) {
			while(histogram[i]==0) {
				i++;
				if(histogram[i]!=0) {
					iReturn = Integer.valueOf(i);
					return iReturn;
				}
			}
		}
		iReturn = Integer.valueOf(i);
		return iReturn;
	}
 		   		     	
	public Integer getMaximum() {
		// Will be used in Exercise 5.
		Integer iReturn;
		int i = 255;
		if(histogram[i]==0) {
			while(histogram[i]==0) {
				i--;
				if(histogram[i]!=0) {
					iReturn = Integer.valueOf(i);
					return iReturn;
				}
			}
		}
		iReturn = Integer.valueOf(i);
		return iReturn;
	}
 		   		     	
	public Double getMean() {
		// Will be used in Exercise 5.
		double sum = 0;
		for(int i = 0; i <= 255; i++) {
			sum = sum + histogram[i]*i;
		}
		return (double) (sum/pixelCount);
	}
 		   		     	
	public Integer getMedian() {
		// Will be used in Exercise 5.
		boolean uneven = (pixelCount % 2 == 1);
		int count = 0;
		int i = 0;
		while(count < pixelCount/2) {
			count = count + histogram[i];
			if(count > pixelCount/2) {
					return Integer.valueOf(i);
			}else if(count == pixelCount/2) {
				if(uneven) {
					return Integer.valueOf(i);
				}else {
					int x = i + 1;
					while(histogram[x] == 0) {
						x++;
					}
					return Integer.valueOf((int) Math.round((i+x)/2.0));
				}
			}
			i++;
		}
		return null;
	}
 		   		     	
	public Double getVariance() {
		// Will be used in Exercise 5.
		double sigmaSqaure = 0;
		for(int i = 0; i <= 255; i++) {
			if(histogram[i] != 0) {
				sigmaSqaure = sigmaSqaure +  Math.pow(i - getMean(), 2.0) * histogram[i]/pixelCount;
			}
		}
		return sigmaSqaure;
	}
 		   		     	
	public Double getEntropy() {
		double entropie = 0;
		// Will be used in Exercise 5.
		for(int i = 0; i <= 255; i++) {
			if(histogram[i] != 0) {
				double probability = histogram[i] / (double) pixelCount;
				entropie = entropie + probability * (-(Math.log(probability) / Math.log(2)));
			}
		}
		return entropie;
	}
 		   		     	
	public void draw(Color lineColor) {
		if(gc == null) return;
		gc.clearRect(0, 0, grayLevels, maxHeight);
		gc.setStroke(lineColor);
		gc.setLineWidth(1);
 		   		     	
		// TODO: draw histogram[] into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers 
		int mostCommon = 0;
		for(int j = 0; j < histogram.length; j++) {
			if(histogram[j]>mostCommon) {
				mostCommon = histogram[j];
			}
		}
		
		double shift = 0.5;
		
		// Remark: This is some dummy code to give you an idea for line drawing		
		//gc.strokeLine(shift, shift, grayLevels-1 + shift, maxHeight-1 + shift);
		//gc.strokeLine(grayLevels-1 + shift, shift, shift, maxHeight-1 + shift);
		for(int i = 0; i < histogram.length; i++) {
			float scale = (float)histogram[i]/(float)mostCommon;
			int yheight = (int) (maxHeight-(scale * maxHeight));
			gc.strokeLine(i+shift, maxHeight + shift , i+shift, yheight + shift);
		}
		
	}
 		   		     	
}
 		   		     	






