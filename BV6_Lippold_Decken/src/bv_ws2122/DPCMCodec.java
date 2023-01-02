// BV Ue6 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-12-10
 		   		     	

package bv_ws2122;


public class DPCMCodec {
	 		   		     	
	public enum PredictionType { 
		A("A (horizontal)"), 
		B("B (vertical)"), 
		C("C (diagonal)"),
		ABC("A+B-C"), 
		AB_MEAN("(A+B)/2"),
		ADAPTIVE("adaptive");
		
		private final String name;       
	    private PredictionType(String s) { name = s; }
	    public String toString() { return this.name; }
	};


	public void processDPCM(RasterImage originalImage, RasterImage errorImage, RasterImage reconstructedImage, double quantizationDelta, PredictionType type) {
		
		// TODO: Encode the originalImage with DPCM using the given prediction type, 
		// visualize the prediction error in errorImage, and
		// decode the prediction error into reconstructedImage.
		
		// Hint: You can implement encoding and decoding with a single iteration over the pixels of the given image.
		
		// Optional: Implement DPCM with quantization. The quantization step size is given in quantizationDelta.
		

	}
	
	public double getMSE(RasterImage originalImage, RasterImage reconstructedImage) {
		
		double mse = Double.NaN;
		
		// TODO: calculate and return the Mean Square Error between the given images
		
		return mse;
	}
	
 		   		     	
}
 		   		     	







