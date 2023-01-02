// BV Ue3 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-22
 		   		     	

package bv_ws2122;

import java.util.Arrays;

public class MorphologicFilter {
 		   		     	
	// filter implementations go here:
	
	public void copy(RasterImage src, RasterImage dst) {
		// TODO: just copy the image

		for (int y_dst = 0; y_dst < dst.height; y_dst++) {
			for (int x_dst = 0; x_dst < dst.width; x_dst++) {
				int y = y_dst;
				int x = x_dst;

				if (y < src.height && x < src.width) {
					int pos_n = y_dst * dst.width + x_dst;
					int pos = y * src.width + x;

					dst.argb[pos_n] = src.argb[pos];
					}
				}
			}
		}

	
	public void dilation(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// kernel's first dimension: y (row), second dimension: x (column)
		// TODO: dilate the image using the given kernel
		int kernelSides = (kernel[0].length-1)/2;
		Arrays.fill(dst.argb, 0xFFFFFFFF);

		for (int y=0; y<src.height; y++) {
			for (int x=0; x<src.width; x++) {
				int pos = y*src.width + x;

				if(src.argb[pos]== 0xFF000000) { // Nur wenn der Pixel schwarz laufe über den Kernel
					for(int m = -kernelSides; m < kernelSides + 1; m++) {
						for (int n = -kernelSides; n < kernelSides + 1; n++) {
							int yK = y + m;
							int xK = x + n;

							if((yK > src.height-1)||(xK > src.width-1)||(yK < 0)||(xK < 0)){
							}
								else{

								if(kernel[m+kernelSides][n+kernelSides] == true) { // wenn der Pixel im Kernel gesetzt ist,
									dst.argb[yK * src.width + xK] = 0xFF000000; //  Position im Sourcebild bestimmen und diesen schwarz färben

									}
								}
							}
						}
					}
				}
			}
		}



 		   		     	
	public void erosion(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// This is already implemented. Nothing to do.
		// It will function once you implemented dilation and RasterImage invert()
		src.invert();
		dilation(src, dst, kernel);
		dst.invert();
		src.invert();
	}
	
	public void opening(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement opening by using dilation() and erosion()
		RasterImage zwischenBild = new RasterImage(src.width, src.height);
		erosion(src, zwischenBild, kernel);
		dilation(zwischenBild,dst, kernel );
	}
	
	public void closing(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement closing by using dilation() and erosion()
		RasterImage zwischenBild = new RasterImage(src.width, src.height);
		dilation(src, zwischenBild, kernel);
		erosion(zwischenBild,dst, kernel );
	}
	
	
 		   		     		
	

}
 		   		     	




