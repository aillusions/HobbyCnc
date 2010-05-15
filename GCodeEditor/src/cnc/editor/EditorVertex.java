package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class EditorVertex {
	private float x;
	private float y;
	private float z;
	
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();
	
	
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(){
		
		for(ActionListener al : listeners){
			ActionEvent ae = new ActionEvent(this , -1, "changed");
			al.actionPerformed(ae);
		}
	}
	
	
	public EditorVertex() {
		super();
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
		notifyAllAboutChanges();
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		notifyAllAboutChanges();
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
		notifyAllAboutChanges();
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
	
	
	

}
