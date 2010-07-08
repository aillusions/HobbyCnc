package cnc.editor.math;

import cnc.editor.domain.gcmd.GCommand;

/**
 * from 2circle.gif
 *
 */
public class CirclesIntersectionFinder {

	private Double x;
	private Double y;

	/**
	 * input: coordinated of two circles and radius (assume radiuses are equals)
	 * @param Xa
	 * @param Ya
	 * @param Xb
	 * @param Yb
	 * @param radius
	 */
	public CirclesIntersectionFinder(double Xa, double Ya, double Xb, double Yb, double radius, boolean CW){
		
		double R = Math.abs(radius);
		double signum = Math.signum(radius);
		
		double X0 = Xa;
		double Y0 = Ya;
		
		double X1 = Xb;
		double Y1 = Yb;
		
		if(GCommand.getRounded(X0).equals(GCommand.getRounded(X1)) 
				&& GCommand.getRounded(Y0).equals(GCommand.getRounded(Y1))){
			x = X0 + radius;
			y = Y0;
			return;
		}
				
		//P1-P0
		double sqrD = Math.pow(X0 - X1, 2) + Math.pow(Y0 - Y1, 2);
		double d = Math.sqrt(sqrD);

		if(2*R < d){
			
			System.err.println("There is no intersections!");		
			
		}else if(2*R == d){	
			
			x = (X0 + X1) / 2;
			y = (Y0 + Y1) / 2;
		
		}else if(2*R > d){
			
			double a = d/2;			
			double h = Math.sqrt(R*R - a*a);			
			double X2, Y2;
			
			X2 = (X1+X0)/2;
			Y2 = (Y1+Y0)/2;
			double X3 , Y3;
			
			if(CW){
				X3 = X2 + signum * (h * (Y1-Y0) / d);
				Y3 = Y2 - signum * (h * (X1-X0) / d);
			}else{
				X3 = X2 - signum * (h * (Y1-Y0) / d);
				Y3 = Y2 + signum * (h * (X1-X0) / d);
			}
			
			x = X3;
			y = Y3;
		}
	}	
	
	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}
	
}
