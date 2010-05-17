package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GCommand {
	
	public static final String CMD_COORDINATE_CHANGED = "cmd_coordinate_changed";
	public enum GcommandTypes{G00, G01, G02}	
		
	private final GcommandTypes gCommandType;	
	private GCommand previousCmd;
	private Float x;
	private Float y;
	private Float z;	
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();
		
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(ActionEvent ae){		
		for(ActionListener al : listeners){			
			al.actionPerformed(ae);
		}
	}

	public GCommand(GcommandTypes type, Float x, Float y, Float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.gCommandType = type;
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

	public GcommandTypes getGcommandType() {
		return gCommandType;
	}
	
	@Override
	public String toString() {
		String strX = x != null ? " X" + x : "" ;
		String strY = y != null ? " Y" + y : "";
		String strZ = z != null ? " Z" + z : "";		
		
		return gCommandType + strX + strY + strZ;
	}
	
	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if(arg0 != null && arg0 instanceof GCommand)
		{
			GCommand gCommand = (GCommand) arg0;
			if(gCommandType == gCommand.gCommandType
					&& ((getX() == null && gCommand.getX() == null) ||(getX() != null && gCommand.getX() != null && getX().equals(gCommand.getX())))
					&& ((getY() == null && gCommand.getY() == null) ||(getY() != null && gCommand.getY() != null && getY().equals(gCommand.getY()))) 
					&& ((getZ() == null && gCommand.getZ() == null) ||(getZ() != null && gCommand.getZ() != null && getZ().equals(gCommand.getZ())))){
				res = true;
			}
		}		
		return res;
	}

	public GCommand getPreviousCmd() {
		return previousCmd;
	}

	public void setPreviousCmd(GCommand previousCmd) {
		this.previousCmd = previousCmd;
	}

}
