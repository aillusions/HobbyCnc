package cnc.editor;

import java.awt.Graphics;

import cnc.editor.domain.GCommand.GcommandTypes;
import cnc.editor.math.EquationOfCircle;


public class GCommandG02 extends GCommand{

	public GCommandG02(Float x, Float y, Float z, Float radius) {
		super(x, y, z);
		this.radius = radius;

	}
	private Float radius;

	@Override
	public void draw(Graphics g) {
			
		//EquationOfCircle eoc = new EquationOfCircle(previousCmd.getX(), previousCmd.getY(), getX(), getY(), radius);
		
		//int top = (int)EditorStates.convertCnc_View((float)eoc.getLeft());
		//int leeft = (int)EditorStates.convertCnc_View((float)eoc.getTop());		
		//int diametr = (int)EditorStates.convertCnc_View((float)eoc.getDiametr()); 

		
		//g.drawArc(top, leeft, diametr, diametr, (int)eoc.getAngelGamma(), (int)eoc.getAngelPhy());	
		
		//g.drawLine((int)EditorStates.convertCnc_View((float)eoc.Rx1), 
		///		(int)EditorStates.convertCnc_View((float)eoc.Ry1), 
			//			(int)EditorStates.convertCnc_View((float)eoc.Rx2), 
			//					(int)EditorStates.convertCnc_View((float)eoc.Ry2));
		
		double X0 = previousCmd.getX();
		double Y0 = previousCmd.getY();
		
		double X1 = getX();
		double Y1 = getY();
		
		double d = Math.sqrt(Math.pow(X0 - X1, 2) + Math.pow(Y0 - Y1, 2));
		//System.out.println("P0(" + (int)X0 + ", " + (int)Y0 + "); P1(" + (int)X1 + ", " +(int)Y1 + ")");
		//System.out.println("2R=" + 2*radius + "; d=" + d);
		if(2*radius < d){
			//System.out.println("no intersections!");
		}else if(2*radius == d){
			//System.out.println("1 intersection");
		}else if(2*radius > d){
			
			//System.out.println("2 intersections");

			double a = d/2;
			
			//System.out.println("a=" + a);
			
			double h = Math.sqrt(radius*radius - a*a);
			//System.out.println("h=" + h);
			
			double X2, Y2;
			//P2 = P0 + a ( P1 - P0 ) / d ;
			X2 = (X1+X0)/2;
			Y2 = (Y1+Y0)/2;
			//System.out.println("P2(" + (int)X2 + ", " + (int)Y2 + ");");
			
			double X3a , Y3a, X3b , Y3b;
			X3a = X2 + h * (Y1-Y0) / d;
			Y3a = Y2 - h * (X1-X0) / d;
			
			X3b = X2 - h * (Y1-Y0) / d;
			Y3b = Y2 + h * (X1-X0) / d;
			
			
			g.drawLine((int)EditorStates.convertCnc_View((float)X3a), 
				(int)EditorStates.convertCnc_View((float)Y3a), 
					(int)EditorStates.convertCnc_View((float)X3b), 
								(int)EditorStates.convertCnc_View((float)Y3b));
		}
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
