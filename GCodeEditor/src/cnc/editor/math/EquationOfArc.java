package cnc.editor.math;

public class EquationOfArc {

	double top;
	double left;		
	double diametr;	
	double startAngle;	
	double arcAngle;
	

	public EquationOfArc(double Ax, double Ay, 
			double Bx, double By, 
			double Rx, double Ry, 
			double radius, boolean clockWise){

		double R = Math.abs(radius);
		
		double X0 = Ax;
		double Y0 = Ay;
		
		double X1 = Bx;
		double Y1 = By;
		
		double X3, Y3;
		
		X3 = Rx;
		Y3 = Ry;
		
		//tangent of angles between:
		//- two vectors: R0(P3, P0) and abscissa; 
		//- and between: R1(P3, P1) and abscissa;
		//P3 (X3,Y3) - center of imagined circle. P0(X0,Y0) - point A. P1(X1,Y1) - point B. 
		//Direction of movement: from A to B.
		double tgA, tgB;
		tgA = ((Y3-Y0)/(X0-X3));
		tgB = ((Y3-Y1)/(X1-X3));			
		
		double angleA, angleB;

		angleA = (180/Math.PI) * Math.atan(tgA);
		angleB = (180/Math.PI) * Math.atan(tgB);
		
		int quarterAa, quarterB;
		
		quarterAa = getQuarter(X3, Y3, X0, Y0);
		if(quarterAa == 2){
			angleA = angleA + 180;
		}else if(quarterAa == 3){
			angleA = angleA + 180;
		}else if(quarterAa == 4){
			angleA = angleA + 360;
		}
		
		quarterB = getQuarter(X3, Y3, X1, Y1);
		if(quarterB == 2){
			angleB = angleB + 180;
		}else if(quarterB == 3){
			angleB = angleB + 180;
		}else if(quarterB == 4){
			angleB = angleB + 360;
		}

		//Needed angle between two vectors
		double angleC = 0;
		
		//Actually there are two angles. We need one of them. In case CW - one, in case CCW - another.
		double angleCa = Math.abs(angleA - angleB);
		double angleCb = 360 - angleCa;
		
		// How to find out, which angle belongs to CW direction, and which one - to CCW direction?
		// CW directions from OA to OB means angle between them should become smaller as long as A moved to B
		// Lets imagine that AO has 'alpha' angles and moves in CW direction. 
		// If OA made 1 degree movement - angle should become 1 degree less. Now what is needed is recalculate 
		// angles between OA and OB. That of them which became less - needed angle for CW movement.
		double angleCaTest = Math.abs((angleA + 1) - angleB);

		if(clockWise){
			if(angleCaTest > angleCa){
				angleC = -angleCa;
			}else{
				angleC = -angleCb;
			}
		}else{
			if(angleCaTest < angleCa){
				angleC = angleCa;
			}else{
				angleC = angleCb;
			}
		}		
	
		startAngle = angleA;
		arcAngle = angleC;
		
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
