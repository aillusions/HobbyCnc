package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//Singleton
public class EditorStates {

	public enum EditorTolls{SIMPLE_EDIT, VERTEX_SELECT};
	
	private EditorStates(){}
	private static final EditorStates instance = new EditorStates();
	public static EditorStates getInstance(){
		return instance;
	}	
	
	private List<ActionListener> listeners = new ArrayList<ActionListener>();	
	
	private EditorTolls currentSelectedTool = EditorTolls.SIMPLE_EDIT;		
	private int theGap = 0;
	private int viewCoordLenght = 200;		
	private float viewScale = 1;	
	
	
	public static long convertCnc_View(float cncCoord){
		return Math.round(cncCoord * getInstance().getScale());
	}
	
	public static float convertView_Cnc(long viewCoord){
		return viewCoord / getInstance().getScale();
	}
	
	
	//Getters - setters
	public float getScale(){
		return viewScale;
	}
	
	public void setScale(float scale){
		viewScale = scale;
		notifyAllAboutChanges();
	}
	
	public int getGap(){
		return theGap;
	}
	
	public void setGap(int gap){
		theGap = gap;
		notifyAllAboutChanges();
	}
	
	public int getCoordLength(){
		return viewCoordLenght;
	}
	
	public void setCoordLength(int coordLenght){
		viewCoordLenght = coordLenght;
		notifyAllAboutChanges();
	}		
	
	public EditorTolls getCurrentSelectedTool() {
		return currentSelectedTool;
	}

	public void setCurrentSelectedTool(EditorTolls currentSelectedTool) {
		this.currentSelectedTool = currentSelectedTool;
	}
	
	
	//------------
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(){
		for(ActionListener al : listeners){
			ActionEvent ae = new ActionEvent(new Object() , -1, "chemaChanged");
			al.actionPerformed(ae);
		}
	}
}
