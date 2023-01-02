// BV Ue2 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-22
 		   		     	

package bv_ws2122;


public class GeometricTransform {
 		   		     	
	public enum InterpolationType { 
		NEAREST("Nearest Neighbour"), 
		BILINEAR("Bilinear");
		
		private final String name;       
	    private InterpolationType(String s) { name = s; }
	    public String toString() { return this.name; }
	};
	
	public void perspective(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion, InterpolationType interpolation) {
		switch(interpolation) {
		case NEAREST:
			perspectiveNearestNeighbour(src, dst, angle, perspectiveDistortion);
			break;
		case BILINEAR:
			perspectiveBilinear(src, dst, angle, perspectiveDistortion);
			break;
		default:
			break;	
		}
		
	}
 		   		     	
	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	public void perspectiveNearestNeighbour(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {

		// TODO: implement the geometric transformation using nearest neighbour image rendering
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant
		// konstant mit weiß fortsetzen

		double angleToRadians = Math.toRadians(angle);

		int xDstVerschoben;
		int yDstVerschoben;


		for (int y = 0; y < dst.height; y++) {
			for (int x = 0; x < dst.width; x++) {
				int pos = y * dst.width + x;

				xDstVerschoben = x - dst.width / 2;
				yDstVerschoben = y - dst.height / 2;

				double ySrcVerschoben = yDstVerschoben / Math.cos(angleToRadians) - perspectiveDistortion * Math.sin(angleToRadians) * yDstVerschoben;
				double xSrcVerschoben = xDstVerschoben * (perspectiveDistortion * Math.sin(angleToRadians) * ySrcVerschoben + 1);

				double ySource = ySrcVerschoben + src.height / 2;
				double xSource = xSrcVerschoben + src.width / 2;

				if (xSource > src.width - 1 || xSource < 0 || ySource > src.height - 1 || ySource < 0) {
					dst.argb[pos] = 0xFFFFFFFF;
				} else {
					int sourcePosition = ((int)ySource * src.width + (int)xSource);
					dst.argb[pos] = src.argb[sourcePosition];
				}
			}
		}
	}


	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	public void perspectiveBilinear(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {
 		   		     	
		// TODO: implement the geometric transformation using bilinear interpolation
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant

		double angleToRadians = Math.toRadians(angle);

		int xDstVerschoben;
		int yDstVerschoben;


		for (int y = 0; y < dst.height; y++) {
			for (int x = 0; x < dst.width; x++) {
				int pos = y * dst.width + x;

				xDstVerschoben = x - dst.width / 2;
				yDstVerschoben = y - dst.height / 2;

				double ySrcVerschoben = yDstVerschoben / Math.cos(angleToRadians) - perspectiveDistortion * Math.sin(angleToRadians) * yDstVerschoben;
				double xSrcVerschoben = xDstVerschoben * (perspectiveDistortion * Math.sin(angleToRadians) * ySrcVerschoben + 1);

				double ySource = ySrcVerschoben + src.height / 2;
				double xSource = xSrcVerschoben + src.width / 2;

				if (xSource > src.width - 1 || xSource < 0 || ySource > src.height - 1 || ySource < 0) {
					dst.argb[pos] = 0xFFFFFFFF;
				} else {
					//Pixel  A, B, C, D
					int sourcePosition = ((int)ySource * src.width + (int)xSource);

					int fuerRandx = 1;
					int fuerRandy = src.width;

					if(xSource + 1 >= src.width){
						fuerRandx = 0;

					}
					if(ySource + 1 >= src.height){
						fuerRandy = 0;
					}

					int A = src.argb[sourcePosition];
					int B = src.argb[sourcePosition + fuerRandx];
					int C = src.argb[sourcePosition + fuerRandy];
					int D = src.argb[sourcePosition + fuerRandx + fuerRandy];


					//RGB Werte von A, B, C, D
					int rA = (A >> 16) & 0xff;
					int gA = (A >> 8) & 0xff;
					int bA = A & 0xff;

					int rB = (B >> 16) & 0xff;
					int gB = (B >> 8) & 0xff;
					int bB = B & 0xff;

					int rC = (C >> 16) & 0xff;
					int gC = (C >> 8) & 0xff;
					int bC = C & 0xff;

					int rD = (D >> 16) & 0xff;
					int gD = (D >> 8) & 0xff;
					int bD = D & 0xff;

					//Abstand vom alten zum neuen Pixel bestimmen (v und h aus der Formel)
					double h = xSource - (int)xSource;
					double v =  ySource - (int)ySource;

					//Formel aus den Slides der Vorlesungen für die bilineare Interpolation - auch GDM Übung 6
					int rn = (int) (rA*(1-h)*(1-v) + rB*h*(1-v) + rC*(1-h)*v + rD*h*v);
					int gn = (int) (gA*(1-h)*(1-v) + gB*h*(1-v) + gC*(1-h)*v + gD*h*v);
					int bn = (int) (bA*(1-h)*(1-v) + bB*h*(1-v) + bC*(1-h)*v + bD*h*v);


					dst.argb[pos] =(0xff<<24) | (rn<<16) | (gn<<8) | (bn);
					}
				}
			}
		}
	}

 		   		     	



