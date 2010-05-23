package cnc.editor.math;

import java.math.BigDecimal;

import cnc.editor.GCommand;

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
		
		double X3 = Rx;
		double Y3 = Ry;

		double angleA = getAngle(X3, Y3, X0, Y0);
		double angleB = getAngle(X3, Y3, X1, Y1);		

		//Needed angle between two vectors
		double angleC = 0;
		
		//Actually there are two angles. We need one of them. In case CW - one, in case CCW - another.
		double angleCa, angleCb = 0;
		
		angleCa = angleB - angleA;	
		
		if(angleCa > 0){
			angleCb = angleCa - 360;
		}else if(angleCa < 0){
			angleCb = angleCa + 360;
		}
		
		BigDecimal bda = GCommand.getRounded(angleCa);
		BigDecimal bdb = GCommand.getRounded(angleCb);		
		
		if(clockWise){
		
			if(bda.compareTo(new BigDecimal(0)) == 1){
				angleC = bda.doubleValue();
			}else if(bda.compareTo(new BigDecimal(0)) == -1){
				angleC = bdb.doubleValue();
			}else if(bda.compareTo(new BigDecimal(0)) == 0){
				if(radius < 0){
					angleC = -360;
				}else{
					angleC = 0;
				}
			}
		}else{
			if(bda.compareTo(new BigDecimal(0)) == -1){
				angleC = bda.doubleValue();
			}else if(bda.compareTo(new BigDecimal(0)) == 1){
				angleC = bdb.doubleValue();
			}else if(bda.compareTo(new BigDecimal(0)) == 0){
				if(radius < 0){
					angleC = 360;
				}else{
					angleC = 0;
				}
			}
		}		
			
		startAngle = angleA;
		arcAngle = angleC;
		
		left = X3 - R;	
		top = Y3 - R;

		diametr = 2*R;		
	}
	
	private double getAngle(double xStart, double yStart, double xEnd, double yEnd){
	
		//tangent of angles between:
		//- two vectors: R0(P3, P0) and abscissa; 
		//- and between: R1(P3, P1) and abscissa;
		//P3 (X3,Y3) - center of imagined circle. P0(X0,Y0) - point A. P1(X1,Y1) - point B. 
		//Direction of movement: from A to B.	
		
		double tg = ((yStart-yEnd)/(xEnd-xStart));			
		double angle = (180/Math.PI) * Math.atan(tg);
		int quarter = 0;

		if(xStart == xEnd){
			if(yStart < yEnd){
				angle = 90;
			}else if(yStart > yEnd){
				angle = 270;
			}else{
				throw new RuntimeException("inposible");
			}
		}else if(yStart == yEnd){
			if(xStart < xEnd){
				angle = 0;
			}else if(xStart > xEnd){
				angle = 180;
			}else{
				throw new RuntimeException("inposible");
			}
		}else{
			
			if(xEnd < xStart){
				if(yEnd < yStart)
					quarter = 2;
				else if(yEnd > yStart)
					quarter =  3;
			}else if(xEnd > xStart){
				if(yEnd < yStart)
					quarter = 1;
				else if(yEnd > yStart)
					quarter = 4;
			}
			
			if(quarter == 2){
				angle = angle + 180;
			}else if(quarter == 3){
				angle = angle + 180;
			}else if(quarter == 4){
				angle = angle + 360;
			}
		}
		
		return angle;
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
