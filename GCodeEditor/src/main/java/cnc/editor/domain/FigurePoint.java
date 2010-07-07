package cnc.editor.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cnc.editor.EditorStates;
import cnc.editor.view.GraphicsWrapper;

public class FigurePoint {
	
	public static final String CMD_COORDINATE_CHANGED = "cmd_coordinate_changed";
	
	protected EditorStates es = EditorStates.getInstance();	
	
	private Float X;
	private Float Y;

	public void draw(GraphicsWrapper g){
		
		int newX = (int)EditorStates.convertPositionCnc_View(X); 
		int newY = (int)EditorStates.convertPositionCnc_View(Y); 	

		float thickness = EditorStates.getInstance().getScale();	
		g.drawBullet(newX, newY, (int)(thickness * 3));
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

	public FigurePoint(Float x, Float y/*, Float z*/) {
		this.X = x;
		this.Y = y;
		//this.Z = z;
	}
	
	public static BigDecimal getRounded(double d){
		BigDecimal bd = new BigDecimal(Math.round(d * 100));
		bd = bd.divide(new BigDecimal(100));
		return bd;
	}
	
	public Float getX() {
		return X;
/*		if(X != null){
			return X;
		}else{
			return  previousCmd != null ? previousCmd.getX() : 0;
		}*/
	}

	public void setX(Float x) {
		this.X = x;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getY() {
		
		return Y;
/*		if(Y != null){
			return Y;
		}else{
			return  previousCmd != null ? previousCmd.getY() : 0;
		}*/
	}

	public void setY(Float y) {
		this.Y = y;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getZ() {
		
		return EditorStates.getInstance().getCuttingDepth();
/*		if(Z != null){
			return Z;
		}else{
			return previousCmd != null ? previousCmd.getZ() : 0;
		}*/
	}

/*	public void setZ(Float z) {
		this.Z = z;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}*/


	@Override
	public String toString() {
		String strX = X != null ? " X" + getRounded(X) : "" ;
		String strY = Y != null ? " Y" + getRounded(Y) : "";
		//String strZ = Z != null ? " Z" + getRounded(Z) : "";		
		
		return strX + strY;
	}


}
