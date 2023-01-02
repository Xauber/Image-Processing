// BV Ue1 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-14


package bv_ws2122;

public class GaussFilter {

	private double[][] kernel;

	public double[][] getKernel() {
		return kernel;
	}

	public void apply(RasterImage src, RasterImage dst, int kernelSize, double sigma) {

		// TODO: Implement a Gauss filter of size "kernelSize" x "kernelSize" with given "sigma"

		// Step 1: Allocate appropriate memory for the field variable "kernel" representing a 2D array.
		kernel = new double[kernelSize][kernelSize];
		double kernelSum = 0;

		// Step 2: Fill in appropriate values into the "kernel" array.
		// Hint:
		// Use g(d) = e^(- d^2 / (2 * sigma^2)), where d is the distance of a coefficient's position to the hot spot.
		// Note that in this comment e^ denotes the exponential function and ^2 the square. In Java ^ is a different operator.


		int seiten = (kernelSize-1)/2 ;

		for(int x = 0; x<kernelSize; x++ ) {
			for(int y = 0; y<kernelSize; y++ ) {
				int seiteX = x-seiten ;
				int seiteY = y-seiten;
				double d =  Math.sqrt(Math.pow(seiteY, 2)+Math.pow(seiteX, 2));
				double g = Math.exp(-Math.pow(d, 2)/(2*Math.pow(sigma, 2)));
				kernel[x][y] = g;
				kernelSum = kernelSum + g;
			}
		}


		// Step 3: Normalize the "kernel" such that the sum of all its values is one.

		for(int x = 0; x<kernelSize; x++ ) {
			for(int y = 0; y<kernelSize; y++ ) {
				kernel[x][y] = kernel[x][y]/kernelSum;
			}

		}

		// Step 4: Apply the filter given by "kernel" to the source image "src". The result goes to image "dst".
		// Use "constant continuation" for boundary processing.

		for (int y=0; y<src.height; y++) {
			for (int x=0; x<src.width; x++) {
				int pos = y*src.width + x;


				// summe für Filter
				double summeRotWerte = 0;
				double summeGruenWerte = 0;
				double summeBlauWerte = 0;

				for(int m = -seiten; m < seiten + 1; m++) {
					for(int n = -seiten; n < seiten + 1; n++) {

						int randX = x + m;
						int randY = y + n;

						//Randbehandlung
						if 	(randY < 0) { randY = 0; }
						if 	(randX < 0) { randX = 0; }
						if(randY >= y)	{ randY  = y; }
						if(randX >= x)	{ randX  = x; }

						int rWert =  (src.argb[randY*src.width + randX]>> 16) & 0xff;
						int gWert =  (src.argb[randY*src.width + randX]>>  8) & 0xff;
						int bWert =  src.argb[randY*src.width + randX]        & 0xff;

						summeRotWerte = summeRotWerte + (rWert * kernel[m+seiten][n+seiten]);
						summeGruenWerte = summeGruenWerte + (gWert * kernel[m+seiten][n+seiten]);
						summeBlauWerte = summeBlauWerte + bWert * (kernel[m+seiten][n+seiten]);
					}
				}

				int finalRot = (int) (summeRotWerte);
				int finalBlau = (int) (summeGruenWerte);
				int finalGruen = (int) (summeBlauWerte);

				// Limiter bei Überlauf
				if(finalRot < 0) finalRot = 0;
				if(finalBlau < 0) finalBlau = 0;
				if(finalGruen < 0) finalGruen = 0;
				if(finalRot > 255) finalRot = 255;
				if(finalBlau > 255) finalBlau = 255;
				if(finalGruen > 255) finalGruen = 255;

				dst.argb[pos] = (0xFF<<24) | (finalRot<<16) | (finalBlau<<8) | finalGruen;
			}
		}
	}
}





