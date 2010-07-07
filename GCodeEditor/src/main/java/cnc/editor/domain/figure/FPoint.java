package cnc.editor.domain.figure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cnc.editor.EditorStates;
import cnc.editor.view.GraphicsWrapper;

public class FPoint {
	
	public static final String CMD_COORDINATE_CHANGED = "cmd_coordinate_changed";
	
	protected EditorStates es = EditorStates.getInstance();	
	
	private Float X;
	private Float Y;

	public void draw(GraphicsWrapper g){
		
		int newX = (int)EditorStates.convertPositionCnc_View(X); 
		int newY = (int)EditorStates.convertPositionCnc_View(Y); 	

		int thickness = (int)(EditorStates.getInstance().getScale() + 5);	
		g.drawBullet(newX, newY, thickness);
	}
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();
		
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(ActionEvent ae){		
		for(ActionListener al : listeners){			
			al.actionPerformed(ae);
		}
	}

	public FPoint(Float x, Float y) {
		this.X = x;
		this.Y = y;
	}
	
	public static BigDecimal getRounded(double d){
		BigDecimal bd = new BigDecimal(Math.round(d * 100));
		bd = bd.divide(new BigDecimal(100));
		return bd;
	}
	
	public Float getX() {
		return X;
	}

	public void setX(Float x) {
		this.X = x;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getY() {		
		return Y;
	}

	public void setY(Float y) {
		this.Y = y;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getZ() {		
		return EditorStates.getInstance().getCuttingDepth();
	}

	@Override
	public String toString() {
		String strX = X != null ? "X" + getRounded(X) : "" ;
		String strY = Y != null ? " Y" + getRounded(Y) : "";
		
		return strX + strY;
	}

	@Override
	public boolean equals(Object arg0) {		
		if (arg0 != null && arg0 instanceof FPoint) {
			
			FPoint inVertex = (FPoint) arg0;
			
			if (X.equals(inVertex.getX()) && Y.equals(inVertex.getY())){
				
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return  X.hashCode()*5 + Y.hashCode()*3 ;
	}


}
