package cnc.editor;

import java.awt.Color;
import java.awt.Graphics;

import cnc.editor.domain.GCommand.GcommandTypes;


public class GCommandG02 extends GCommand{
	
	private Float radius;
	
	public GCommandG02(Float x, Float y, Float z, Float radius) {
		super(x, y, z);
		this.radius = radius;
	}	

	@Override
	public void drawLine(Graphics g) {
		
		float radiusSignum = Math.signum(radius);
		float R = Math.abs(radius);
			
		double X0 = previousCmd.getX();
		double Y0 = previousCmd.getY();
		
		double X1 = getX();
		double Y1 = getY();
				
		//P1-P0
		double sqrD = Math.pow(X0 - X1, 2) + Math.pow(Y0 - Y1, 2);
		double d = Math.sqrt(sqrD);

		if(2*R < d){
			drawError(g);
			System.err.println("drawLine can not be run: no intersections!");
			drawError(g);
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
			
			X3 = X2 - radiusSignum *( h * (Y1-Y0) / d);
			Y3 = Y2 + radiusSignum * (h * (X1-X0) / d);

			{					
				//Draw center of imagined circle
				//int centerX, centerY;
				//int size = 5;
				//centerX = (int)(EditorStates.convertPositionCnc_View((float)X3)-size/2);
				//centerY = (int)(EditorStates.convertPositionCnc_View((float)Y3)-size/2);	
				//g.fillOval(centerX, centerY, size, size);
				
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
				
				double left = X3 - R;	
				double top = Y3 - R;
				//System.out.println(left + ", " + top );
				int viewLeft = (int)EditorStates.convertPositionCnc_View((float)left);
				int viewTop = (int)EditorStates.convertPositionCnc_View((float)top);
				int viewHeight = (int)EditorStates.convertLengthCnc_View((float)(2*R));

				g.drawArc(viewLeft, viewTop, viewHeight, viewHeight, (int)angelA, (int)angelCa);
			}					
		}
	}
	
	public void drawError(Graphics g) {
		
		int prevX = (int)EditorStates.convertPositionCnc_View(previousCmd.getX());
		int prevY = (int)EditorStates.convertPositionCnc_View(previousCmd.getY());
		
		int newX = (int)EditorStates.convertPositionCnc_View(getX()); 
		int newY = (int)EditorStates.convertPositionCnc_View(getY()); 	
		
		Color c = g.getColor();
		g.setColor(Color.magenta);
		g.drawLine(prevX, prevY, newX, newY);
		g.drawLine(prevX+1, prevY+1, newX+1, newY+1);
		g.setColor(c);
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
	
	@Override
	public GcommandTypes getCommandType() {
		return GcommandTypes.G02;
	}	
	
	@Override
	public String toString() {
		return super.toString() + " R" + radius;
	}

	public Float getRadius() {
		return radius;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}

}
