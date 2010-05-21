package cnc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cnc.editor.Editor.GcommandTypes;

public abstract class GCommand {
	
	public static final String CMD_COORDINATE_CHANGED = "cmd_coordinate_changed";
	
	protected EditorStates es = EditorStates.getInstance();	
	protected GCommand previousCmd;
	//protected GCommand nextCmd;
	
	private Float X;
	private Float Y;
	private Float Z;	

	public abstract GcommandTypes getCommandType();	
	public abstract void drawLine(Graphics g);

	public void draw(Graphics g){
		
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
		g.fillOval((int)(newX-pointSize/2), (int)(newY-pointSize/2), pointSize, pointSize);
		
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

	public Float getX() {
		if(X != null){
			return X;
		}else{
			return previousCmd.getX();
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
			return previousCmd.getY();
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
			return previousCmd.getZ();
		}
	}

	public void setZ(Float z) {
		this.Z = z;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}


	@Override
	public String toString() {
		String strX = X != null ? " X" + X : "" ;
		String strY = Y != null ? " Y" + Y : "";
		String strZ = Z != null ? " Z" + Z : "";		
		
		return getCommandType() + " " + strX + strY + strZ;
	}
	
/*	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if(arg0 != null && arg0 instanceof GCommand)
		{
			GCommand gCommand = (GCommand) arg0;
			if((getCommandType() == gCommand.getCommandType()) 
					&& ((getX() == null && gCommand.getX() == null) ||(getX() != null && gCommand.getX() != null && getX().equals(gCommand.getX())))
					&& ((getY() == null && gCommand.getY() == null) ||(getY() != null && gCommand.getY() != null && getY().equals(gCommand.getY()))) 
					&& ((getZ() == null && gCommand.getZ() == null) ||(getZ() != null && gCommand.getZ() != null && getZ().equals(gCommand.getZ())))){
				res = true;
			}
		}		
		return res;
	}*/

	public GCommand getPreviousCmd() {
		return previousCmd;
	}

	public void setPreviousCmd(GCommand previousCmd) {
		//previousCmd.setNextCmd(this);
		this.previousCmd = previousCmd;
	}
/*	public GCommand getNextCmd() {
		return nextCmd;
	}
	public void setNextCmd(GCommand nextCmd) {
		this.nextCmd = nextCmd;
	}*/
	
	

}
