package cnc.editor;

import java.awt.Color;
import java.awt.Graphics;

import cnc.editor.domain.GCommand.GcommandTypes;


public class GCommandG02 extends GCommand{

	public GCommandG02(Float x, Float y, Float z, Float radius) {
		super(x, y, z);
		this.radius = radius;

	}
	private Float radius;

	@Override
	public void draw(Graphics g) {
			
		double X0 = previousCmd.getX();
		double Y0 = previousCmd.getY();
		
		double X1 = getX();
		double Y1 = getY();
		
		//P1-P0
		double sqrD = Math.pow(X0 - X1, 2) + Math.pow(Y0 - Y1, 2);
		double d = Math.sqrt(sqrD);
		
		if(2*radius < d){
			//System.out.println("no intersections!");
		
		}else if(2*radius == d){
			//System.out.println("1 intersection");
		
		}else if(2*radius > d){
			//System.out.println("2 intersections");
			
			double a = d/2;			
			double h = Math.sqrt(radius*radius - a*a);			
			double X2, Y2;
			
			X2 = (X1+X0)/2;
			Y2 = (Y1+Y0)/2;
			
			double X3a , Y3a, X3b , Y3b;
			
			X3a = X2 + h * (Y1-Y0) / d;
			Y3a = Y2 - h * (X1-X0) / d;
			
			X3b = X2 - h * (Y1-Y0) / d;
			Y3b = Y2 + h * (X1-X0) / d;			
			
/*			g.drawLine(
				(int)EditorStates.convertPositionCnc_View((float)X3a), 
				(int)EditorStates.convertPositionCnc_View((float)Y3a), 
				(int)EditorStates.convertPositionCnc_View((float)X3b), 
				(int)EditorStates.convertPositionCnc_View((float)Y3b));*/
			
			{
				int size = 2;
				
				int centerXa, centerYa;
				centerXa = (int)(EditorStates.convertPositionCnc_View((float)X3a)-size/2);
				centerYa = (int)(EditorStates.convertPositionCnc_View((float)Y3a)-size/2);
				
				//g.fillOval(centerXa, centerYa, size, size);
				
				//
				double tgA, tgB;
				tgA = ((Y3a-Y0)/(X0-X3a));
				tgB = ((Y3a-Y1)/(X1-X3a));
			
				
				double angelAa, angelBa;

				angelAa = (180/Math.PI) * Math.atan(tgA);
				angelBa = (180/Math.PI) * Math.atan(tgB);
				
				int quarterAa, quarterBa;
				quarterAa = getQuarter(X3a, Y3a, X0, Y0);
				if(quarterAa == 2){
					angelAa = angelAa + 180;
				}else if(quarterAa == 3){
					angelAa = angelAa + 180;
				}else if(quarterAa == 4){
					angelAa = angelAa + 360;
				}
				
				quarterBa = getQuarter(X3a, Y3a, X1, Y1);
				if(quarterBa == 2){
					angelBa = angelBa + 180;
				}else if(quarterBa == 3){
					angelBa = angelBa + 180;
				}else if(quarterBa == 4){
					angelBa = angelBa + 360;
				}
								
				double left = X3a - radius;	
				double top = Y3a - radius;
				
				int viewLeft = (int)EditorStates.convertPositionCnc_View((float)left);
				int viewTop = (int)EditorStates.convertPositionCnc_View((float)top);
				int viewHeight = (int)EditorStates.convertLengthCnc_View((float)(2*radius));
				
				g.setColor(Color.MAGENTA);
				g.drawArc(viewLeft, viewTop, viewHeight, viewHeight, (int)angelAa, (int)(angelBa - angelAa));

			}					
		}
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
	
	public Float getRadius() {
		return radius;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}

}
