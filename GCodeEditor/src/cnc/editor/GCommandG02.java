package cnc.editor;

import java.awt.Color;
import java.awt.Graphics;

import cnc.editor.Editor.GcommandTypes;
import cnc.editor.math.CirclesIntersectionFinder;
import cnc.editor.math.EquationOfArc;

public class GCommandG02 extends GCommand{
	
	private Float radius;
	private Float I;
	private Float J;
	
	protected float signum = 1;
	
	public GCommandG02(Float x, Float y, Float z, Float radius) {
		super(x, y, z);
		this.radius = radius;
		this.signum = Math.signum(radius);
	}	
	
	public GCommandG02(Float x, Float y, Float z, Float i, Float j) {
		super(x, y, z);
		this.I = i;
		this.J = j;
	}

	@Override
	public void drawLine(Graphics g) {	
		
		double X0 = previousCmd.getX();
		double Y0 = previousCmd.getY();
		
		double X1 = getX();
		double Y1 = getY();
		
		Double X3 , Y3;
		
		CirclesIntersectionFinder cif = new CirclesIntersectionFinder(X0, Y0, X1, Y1, radius);
		X3 = cif.getX();
		Y3 = cif.getY();
		
		if(X3 == null || Y3 == null){
			drawError(g);
			return;
		}
		
		//Draw center of imagined circle
		//int centerX, centerY;
		//int size = 5;
		//centerX = (int)(EditorStates.convertPositionCnc_View((float)X3)-size/2);
		//centerY = (int)(EditorStates.convertPositionCnc_View((float)Y3)-size/2);	
		//g.fillOval(centerX, centerY, size, size);
			
		EquationOfArc eoa = new EquationOfArc(X0, Y0, X1, Y1, X3, Y3, radius);
		
		int viewLeft = (int)EditorStates.convertPositionCnc_View((float)eoa.getLeft());
		int viewTop = (int)EditorStates.convertPositionCnc_View((float)eoa.getTop());
		int viewHeight = (int)EditorStates.convertLengthCnc_View((float)(eoa.getDiametr()));
		
		g.drawArc(viewLeft, viewTop, viewHeight, viewHeight, (int)eoa.getStartAngle(), (int)eoa.getArcAngle());

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
