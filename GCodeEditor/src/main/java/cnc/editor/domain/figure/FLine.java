package cnc.editor.domain.figure;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cnc.editor.EditorStates;
import cnc.editor.Editor.GcommandTypes;
import cnc.editor.view.GraphicsWrapper;

public abstract class FLine {


	protected EditorStates es = EditorStates.getInstance();	
	
	protected FPoint pointFrom;
	protected FPoint pointTo;	

	public abstract GcommandTypes getLineType();	
	public abstract void drawLine(GraphicsWrapper g);

	public FPoint getPointFrom() {
		return pointFrom;
	}
	public void setPointFrom(FPoint pointFrom) {
		this.pointFrom = pointFrom;
	}
	public FPoint getPointTo() {
		return pointTo;
	}
	public void setPointTo(FPoint pointTo) {
		this.pointTo = pointTo;
	}
	public void draw(GraphicsWrapper g){		

		drawLine(g);
	}
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();
		
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}

	public FLine(FPoint pointFrom, FPoint pointTo) {
		this.pointFrom = pointFrom;
		this.pointTo = pointTo;
	}
	
	public static BigDecimal getRounded(double d){
		BigDecimal bd = new BigDecimal(Math.round(d * 100));
		bd = bd.divide(new BigDecimal(100));
		return bd;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof FLine) {
			
			FLine lineObj = (FLine) obj;
			
			if (pointFrom.equals(lineObj.pointFrom) && pointTo.equals(lineObj.pointTo)){				
				return true;
			}
		}

		return false;
	}
	
	@Override
	public int hashCode() {
		return  pointFrom.hashCode()*5 + pointTo.hashCode()*3 ;
	}
	@Override
	public String toString() {		
		//return getLineType().toString()+ " from: " + pointFrom.toString() + " to: " + pointTo.toString();
		
		String strX = pointTo.getX() != null ? " X" + getRounded(pointTo.getX()) : "" ;
		String strY = pointTo.getY() != null ? " Y" + getRounded(pointTo.getY()) : "";

		
		return getLineType() + strX + strY;
	}
	
}
