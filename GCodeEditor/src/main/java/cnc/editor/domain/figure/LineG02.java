package cnc.editor.domain.figure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import cnc.editor.EditorStates;
import cnc.editor.Editor.GcommandTypes;
import cnc.editor.math.CirclesIntersectionFinder;
import cnc.editor.math.EquationOfArc;
import cnc.editor.view.GraphicsWrapper;

public class LineG02  extends FLine {

	@Override
	public GcommandTypes getLineType() {
		return GcommandTypes.G02;
	}
	
	
	private Float radius;
	private Float I;
	private Float J;
	
	//flag which means type of command definition.
	//true - radius is defined
	//false - i,j are defined
	protected boolean radiusSpecified;
	protected boolean clockWise;
	
	public LineG02(FPoint from, FPoint to, Float radius) {
		super(from, to);
		this.radius = radius;
		this.radiusSpecified = true;
		this.clockWise = true;
	}	
	
	public LineG02(FPoint from, FPoint to, Float i, Float j) {
		super(from, to);
		this.I = i;
		this.J = j;
		this.radiusSpecified = false;
		this.clockWise = true;
	}

	@Override
	public void drawLine(GraphicsWrapper gw) {	
		
		//Arc radius
		double R;
		
		//A (from A)
		double X0 = getPointFrom().getX();
		double Y0 = getPointFrom().getY();
		
		//B (to B)
		double X1 = getPointTo().getX();
		double Y1 = getPointTo().getY();
		
		//Arc center (with center)
		Double X3 = null, Y3 = null;
		
		if(radiusSpecified){	
			
			CirclesIntersectionFinder cif = new CirclesIntersectionFinder(X0, Y0, X1, Y1, radius, this.clockWise);
			X3 = cif.getX();
			Y3 = cif.getY();	
			R = radius;
			
		}else{
			
			X3 = (double)this.I + X0;
			Y3 = (double)this.J + Y0;
			
			R = Math.sqrt(Math.pow(X3 - X0, 2) + Math.pow(Y3 - Y0, 2));			
			double R1 = Math.sqrt(Math.pow(X3 - X1, 2) + Math.pow(Y3 - Y1, 2));
			
			if(!getRounded(R1).equals(getRounded(R))){
				
				System.err.println(getRounded(R) + " != " + getRounded(R1));
				drawError(gw);
				darawRadiusPoint(gw, X3, Y3);
				return;
			}
		}
				
		if(X3 == null || Y3 == null){
			drawError(gw);
			return;
		}
		
		darawRadiusPoint(gw, X3, Y3);
		EquationOfArc eoa = new EquationOfArc(X0, Y0, X1, Y1, X3, Y3, R, this.clockWise );
		
		int viewLeft = (int)EditorStates.convertPositionCnc_View((float)eoa.getLeft());
		int viewTop = (int)EditorStates.convertPositionCnc_View((float)eoa.getTop());
		int viewHeight = (int)EditorStates.convertLengthCnc_View((float)(eoa.getDiametr()));
		
		gw.drawArc(viewLeft, viewTop, viewHeight, (int)eoa.getStartAngle(), (int)eoa.getArcAngle());

	}
	
	public void drawError(GraphicsWrapper gw) {
		
		int prevX = (int)EditorStates.convertPositionCnc_View(getPointFrom().getX());
		int prevY = (int)EditorStates.convertPositionCnc_View(getPointFrom().getY());
		
		int newX = (int)EditorStates.convertPositionCnc_View(getPointTo().getX()); 
		int newY = (int)EditorStates.convertPositionCnc_View(getPointTo().getY()); 	
		
		Color c = gw.getColor();
		gw.setColor(Color.magenta);
		
	    Graphics2D g2 = (Graphics2D)gw.getG();
		float thickness = 2.0f;	
		Stroke s = new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[]{4f}, 3f);
		Stroke olds = g2.getStroke();
		g2.setStroke(s);	
		
		gw.drawLineWithScaleThickness(prevX, prevY, newX, newY);
		
		gw.setColor(c);
		g2.setStroke(olds);
	}
	
	protected void darawRadiusPoint(GraphicsWrapper gw, double x, double y){
		
		//Draw center of imagined circle
		int centerX, centerY;
		int size = 4;
		centerX = (int)(EditorStates.convertPositionCnc_View((float)x));
		centerY = (int)(EditorStates.convertPositionCnc_View((float)y));	
		gw.drawBullet(centerX, centerY, size);
	}
		

	
	@Override
	public String toString() {
		if(radiusSpecified)
			return super.toString() + " R" + radius;
		else
			return super.toString() + " I" + I + " J" + J;
	}

	public Float getRadius() {
		return radius;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}

}
