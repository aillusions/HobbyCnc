package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class EditorVertex {
	
	public static final String CMD_COORDINATE_CHANGED = "cmd_coordinate_changed";
	
	private float x;
	private float y;
	private float z;	
	private GCommand gCommand;
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();
		
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(ActionEvent ae){
		
		for(ActionListener al : listeners){			
			al.actionPerformed(ae);
		}
	}
		
	public EditorVertex() {

	}

	public EditorVertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
		ActionEvent ae = new ActionEvent(this , -1, CMD_COORDINATE_CHANGED);
		notifyAllAboutChanges(ae);
	}

	@Override
	public String toString() {
		return String.format(java.util.Locale.US, "%1$f;%2$f;%3$f;",x,y,z);
	}

	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if(arg0 != null && arg0 instanceof EditorVertex)
		{
			EditorVertex inVertex = (EditorVertex) arg0;
			if(x == inVertex.getX() &&	y == inVertex.getY() && z == inVertex.getZ()){
				res = true;
			}
		}
		
		return res;
	}
	
	public GCommand getgCommand() {
		return gCommand;
	}
	
	public void setgCommand(GCommand gCommand) {
		this.gCommand = gCommand;
	}
}
