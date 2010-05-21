package cnc.editor.math;

/**
 * from 2circle.gif
 * @author Oleksandr_Zaliznyak
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
	public CirclesIntersectionFinder(double Xa, double Ya, double Xb, double Yb, double radius){
		
		double R = Math.abs(radius);
		double signum = Math.signum(radius);
		
		double X0 = Xa;
		double Y0 = Ya;
		
		double X1 = Xb;
		double Y1 = Yb;
				
		//P1-P0
		double sqrD = Math.pow(X0 - X1, 2) + Math.pow(Y0 - Y1, 2);
		double d = Math.sqrt(sqrD);

		if(2*R < d){
			
			System.err.println("drawLine can not be run: no intersections!");		
			
		}else if(2*R == d){	
			
			System.err.println(" 1 intersections -- untreated");		
		
		}else if(2*R > d){
			//System.out.println("2 intersections");
			
			double a = d/2;			
			double h = Math.sqrt(R*R - a*a);			
			double X2, Y2;
			
			X2 = (X1+X0)/2;
			Y2 = (Y1+Y0)/2;
			double X3 , Y3;
			
			X3 = X2 - signum * (h * (Y1-Y0) / d);
			Y3 = Y2 + signum * (h * (X1-X0) / d);
			
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
