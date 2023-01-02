// BV Ue6 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-12-10
 		   		     	

package bv_ws2122;

import java.io.File;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RasterImage {
	 		   		     	
	private static final int gray  = 0xffa0a0a0;
	    
	public int[] argb;	// pixels represented as ARGB values in scanline order
	public int width;	// image width in pixels
	public int height;	// image height in pixels
	
	public RasterImage(int width, int height) {
		// creates an empty RasterImage of given size
		this.width = width;
		this.height = height;
		argb = new int[width * height];
		Arrays.fill(argb, gray);
	}
	
	public RasterImage(RasterImage src) {
		// copy constructor
		this.width = src.width;
		this.height = src.height;
		argb = src.argb.clone();
	}
	
	public RasterImage(File file) {
		// creates an RasterImage by reading the given file
		Image image = null;
		if(file != null && file.exists()) {
			image = new Image(file.toURI().toString());
		}
		if(image != null && image.getPixelReader() != null) {
			width = (int)image.getWidth();
			height = (int)image.getHeight();
			argb = new int[width * height];
			image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
		} else {
			// file reading failed: create an empty RasterImage
			this.width = 256;
			this.height = 256;
			argb = new int[width * height];
			Arrays.fill(argb, gray);
		}
	}
	 		   		     	
	public RasterImage(ImageView imageView) {
		// creates a RasterImage from that what is shown in the given ImageView
		Image image = imageView.getImage();
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		argb = new int[width * height];
		image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
	}
	
	public void setToView(ImageView imageView) {
		// sets the current argb pixels to be shown in the given ImageView
		if(argb != null) {
			WritableImage wr = new WritableImage(width, height);
			PixelWriter pw = wr.getPixelWriter();
			pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
			imageView.setImage(wr);
		}
	}
		
	// image point operations to be added here

	public void convertToGray() {

		for (int i=0; i<height*width; i++) {
			int newArgb = argb[i];

			int r = (newArgb >> 16) & 0xff;
			int g = (newArgb >>  8) & 0xff;
			int b =  newArgb        & 0xff;

			int grey = (r + g +b)/3;

			int rn = grey;
			int gn = grey;
			int bn = grey;

			//Ueberlaeufe begrenzen
			if(rn > 255) rn = 255;
			if(gn > 255) gn = 255;
			if(bn > 255) bn = 255;

			if(rn < 0) rn = 0;
			if(gn < 0) gn = 0;
			if(bn < 0) bn = 0;

			argb[i] = (0xFF<<24) | (rn<<16) | (gn<<8) | bn;
		}

	}
	
	public double getEntropy() {
		
		double entropy = Double.NaN;
		
		// TODO: calculate and return the entropy of the image

		return entropy;
	}
 		   		     	
} 		   		     	




 		   		     	


