package cnc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cnc.editor.domain.GCommand.GcommandTypes;

public abstract class GCommand {
	
	public static final String CMD_COORDINATE_CHANGED = "cmd_coordinate_changed";
	
	protected EditorStates es = EditorStates.getInstance();	
	protected GCommand previousCmd;
	private Float x;
	private Float y;
	private Float z;	

	public abstract GcommandTypes getCommandType();	
	public abstract void drawLine(Graphics g);

	public void draw(Graphics g){
		
		Color lineColor = Color.black;
		Color pointColor = Color.black;
		int pointSize;
		
		Set<GCommand> selectedCCmd = es.getSelectedCommand();
		Set<GCommand> nearSelection = es.getNearSelectedCommans();
		int size = (int)EditorStates.NODE_CIRCLE_SIZE;
		if(selectedCCmd != null)
			System.out.println(selectedCCmd.size());
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
		
		g.setColor(lineColor);
		drawLine(g);
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
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Float getX() {
		if(x != null){
			return x;
		}else{
			return previousCmd.getX();
		}
	}

	public void setX(Float x) {
		this.x = x;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getY() {
		if(y != null){
			return y;
		}else{
			return previousCmd.getY();
		}
	}

	public void setY(Float y) {
		this.y = y;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public Float getZ() {
		if(z != null){
			return z;
		}else{
			return previousCmd.getZ();
		}
	}

	public void setZ(Float z) {
		this.z = z;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}


	@Override
	public String toString() {
		String strX = x != null ? " X" + x : "" ;
		String strY = y != null ? " Y" + y : "";
		String strZ = z != null ? " Z" + z : "";		
		
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
		this.previousCmd = previousCmd;
	}

}
