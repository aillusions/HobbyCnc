package cnc.editor.math;


public class EquationOfArc {

	double top;
	double left;		
	double diametr;	
	double startAngle;	
	double arcAngle;
	

	public EquationOfArc(double Ax, double Ay, double Bx, double By, double Rx, double Ry, double radius){

		double R = Math.abs(radius);
		
		double X0 = Ax;
		double Y0 = Ay;
		
		double X1 = Bx;
		double Y1 = By;
		
		double X3, Y3;
		
		X3 = Rx;
		Y3 = Ry;
		
		//tangent of angels between:
		//- two vectors: R0(P3, P0) and abscissa; 
		//- and between: R1(P3, P1) and abscissa;
		//P3 (X3,Y3) - center of imagined circle. P0(X0,Y0) - point A. P1(X1,Y1) - point B. 
		//Direction of movement: from A to B.
		double tgA, tgB;
		tgA = ((Y3-Y0)/(X0-X3));
		tgB = ((Y3-Y1)/(X1-X3));			
		
		double angelA, angelB;

		angelA = (180/Math.PI) * Math.atan(tgA);
		angelB = (180/Math.PI) * Math.atan(tgB);
		
		int quarterAa, quarterB;
		quarterAa = getQuarter(X3, Y3, X0, Y0);
		if(quarterAa == 2){
			angelA = angelA + 180;
		}else if(quarterAa == 3){
			angelA = angelA + 180;
		}else if(quarterAa == 4){
			angelA = angelA + 360;
		}
		
		quarterB = getQuarter(X3, Y3, X1, Y1);
		if(quarterB == 2){
			angelB = angelB + 180;
		}else if(quarterB == 3){
			angelB = angelB + 180;
		}else if(quarterB == 4){
			angelB = angelB + 360;
		}
		
		//Angel between: R0 and R1;
		double angelCa = (angelB - angelA);
		if(Math.abs(angelCa) > 180){
			angelCa = -Math.signum(angelCa) * (360 - Math.abs(angelCa));
		}
		
		startAngle = angelA;
		arcAngle = angelCa;
		
		left = X3 - R;	
		top = Y3 - R;

		diametr = 2*R;
		
	}
	
	private int getQuarter(double xStart, double yStart, double xEnd, double yEnd){
		
		if(xEnd < xStart){
			if(yEnd < yStart)
				return 2;
			else if(yEnd > yStart)
				return 3;
		}else if(xEnd > xStart){
			if(yEnd < yStart)
				return 1;
			else if(yEnd > yStart)
				return 4;
		}
		return 0;
	}

	public double getTop() {
		return top;
	}
	
	public double getLeft() {
		return left;
	}

	public double getDiametr() {
		return diametr;
	}

	public double getStartAngle() {
		return startAngle;
	}

	public double getArcAngle() {
		return arcAngle;
	}

}
