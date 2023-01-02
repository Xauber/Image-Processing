// BV Ue4 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-23
 		   		     	

package bv_ws2122;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToneCurve {
 		   		     	
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    
    private int[] grayTable = new int[grayLevels];
 		   		     	
	public int[] getGrayTable() {
		return grayTable;
	}

	public ToneCurve(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public void updateTable(int minInput, int maxInput, int minOutput, int maxOutput) {
		
		// TODO: Fill the grayTable[] array to map gay input values to gray output values.
		// It will be used as follows: grayOut = grayTable[grayIn].
		//
		// Use minInput, maxInput, minOutput, and maxOutput settings.
		float delta = (((float)(maxOutput-minOutput)/(float)(maxInput-minInput)));
		for(int i = 0; i < grayTable.length; i ++) {
			
			if(i<=minInput) {
			grayTable[i] = minOutput;
			}
			if(i>=maxInput) {
				grayTable[i] = maxOutput;
			}
			if(minInput<i && i<maxInput) {
			grayTable[i]=(int) (((i-minInput)*delta)+minOutput);	
			}
		}

	}
	
	public void applyTo(RasterImage image) {
		
		// TODO: apply the gray value mapping to the given image
		for (int i=0; i<image.height*image.width; i++) {
			int newArgb = image.argb[i];
			
			int value =  newArgb        & 0xff;
			
			int newValue = grayTable[value];
			
			int rn = newValue;
			int gn = newValue;
			int bn = newValue;
			
			//Ueberlaeufe begrenzen
			if(rn > 255) rn = 255;
			if(gn > 255) gn = 255;
			if(bn > 255) bn = 255;

			if(rn < 0) rn = 0;
			if(gn < 0) gn = 0;
			if(bn < 0) bn = 0;

			image.argb[i] = (0xFF<<24) | (rn<<16) | (gn<<8) | bn;
		}

	}
	
	public void draw(Color lineColor) {
		if(gc == null) return;
		gc.clearRect(0, 0, grayLevels, grayLevels);
		gc.setStroke(lineColor);
		gc.setLineWidth(3);
		
		// TODO: draw the tone curve into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers 
		
		double shift = 0.5;
		for(int i = 1; i < grayTable.length; i++) {
		System.out.println(grayTable[i]);
		}

		// Remark: This is some dummy code to give you an idea for graphics drawing using paths		
		gc.beginPath();
		gc.moveTo(0 + shift, 255 - grayTable[0] + shift);
		for(int i = 1; i < grayTable.length; i++) {
			gc.lineTo(i + shift, 255 - grayTable[i] + shift);
		}
		gc.stroke();
	}

 		   		     	
}
 		   		     	




