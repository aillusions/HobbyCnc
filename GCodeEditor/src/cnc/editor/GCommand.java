package cnc.editor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cnc.editor.Editor.GcommandTypes;
import cnc.editor.view.GraphicsWrapper;

public abstract class GCommand {
	
	public static final String CMD_COORDINATE_CHANGED = "cmd_coordinate_changed";
	
	protected EditorStates es = EditorStates.getInstance();	
	protected GCommand previousCmd;
	
	private Float X;
	private Float Y;
	private Float Z;	

	public abstract GcommandTypes getCommandType();	
	public abstract void drawLine(GraphicsWrapper g);

	public void draw(GraphicsWrapper g){
		
		Color lineColor = Color.black;
		Color pointColor = Color.black;
		int pointSize;
		
		Set<GCommand> selectedCCmd = es.getSelectedGCommands();
		Set<GCommand> nearSelection = es.getNearSelectedGCommands();
		int size = (int)EditorStates.NODE_CIRCLE_SIZE;

		pointSize = 2;
		lineColor = Color.black;
		
		if(nearSelection != null && nearSelection.contains(this)){
			pointColor = Color.gray;
			pointSize = size;
		}

		if(selectedCCmd != null && selectedCCmd.contains(this)){
			pointColor = Color.red;
			lineColor = Color.red;	
			pointSize = size;			
		}
		
		if(this.getZ() > 0){
			lineColor = Color.blue;
		}
		
		int newX = (int)EditorStates.convertPositionCnc_View(getX()); 
		int newY = (int)EditorStates.convertPositionCnc_View(getY()); 	
		
		g.setColor(pointColor);		
		g.drawBullet(newX, newY, pointSize);
		
		if(!es.isDisplayOnlyZ0() || this.getZ() <= 0){
			g.setColor(lineColor);
			drawLine(g);	
		}
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

	public GCommand(Float x, Float y, Float z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}

	
	public static BigDecimal getRounded(double d){
		BigDecimal bd = new BigDecimal(Math.round(d * 100));
		bd = bd.divide(new BigDecimal(100));
		return bd;
	}
	
	public Float getX() {
		if(X != null){
			return X;
		}else{
			return  previousCmd != null ? previousCmd.getX() : null;
		}
	}

	public void setX(Float x) {
		this.X = x;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getY() {
		if(Y != null){
			return Y;
		}else{
			return  previousCmd != null ? previousCmd.getY() : null;
		}
	}

	public void setY(Float y) {
		this.Y = y;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getZ() {
		if(Z != null){
			return Z;
		}else{
			return previousCmd != null ? previousCmd.getZ() : null;
		}
	}

	public void setZ(Float z) {
		this.Z = z;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}


	@Override
	public String toString() {
		String strX = X != null ? " X" + getRounded(X) : "" ;
		String strY = Y != null ? " Y" + getRounded(Y) : "";
		String strZ = Z != null ? " Z" + getRounded(Z) : "";		
		
		return getCommandType() + strX + strY + strZ;
	}
	
	public GCommand getPreviousCmd() {
		return previousCmd;
	}

	public void setPreviousCmd(GCommand previousCmd) {
		this.previousCmd = previousCmd;
	}

}
