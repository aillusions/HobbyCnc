package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import cnc.editor.Editor.EditMode;
import cnc.editor.Editor.EditorTolls;

//Singleton
public class EditorStates {


	public static final float SELECTIO_CIRCLE_SIZE = 8f;
	public static final float BMP_TO_CNC_COORD_RATIO = 5f;	
	
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
	private GCommand selectedVertex;
	private List<GCommand> nearSelectedVertex;
	private Editor.EditMode currentEditMode = EditMode.DRAW;
	
	public float getBmpToCncCoordRatio() {
		return BMP_TO_CNC_COORD_RATIO;
	}

	public static long convertCnc_View(float cncCoord){
		return Math.round(cncCoord * getInstance().getScale() * BMP_TO_CNC_COORD_RATIO);
	}
	
	public static float convertView_Cnc(long viewCoord){
		return viewCoord / getInstance().getScale() / BMP_TO_CNC_COORD_RATIO;
	}	
	
	//Getters - setters
	public float getScale(){
		return viewScale;
	}
	
	public void setScale(float scale){
		viewScale = scale;
		ActionEvent ae = new ActionEvent(new Object() , -1, "chemaChanged");
		notifyAllAboutChanges(ae);
	}
	
	public int getGap(){
		return theGap;
	}
	
	public void setGap(int gap){
		theGap = gap;
		ActionEvent ae = new ActionEvent(new Object() , -1, "chemaChanged");
		notifyAllAboutChanges(ae);
	}
	
	public int getCoordLength(){
		return viewCoordLenght;
	}
	
	public void setCoordLength(int coordLenght){
		viewCoordLenght = coordLenght;
		ActionEvent ae = new ActionEvent(new Object() , -1, "chemaChanged");
		notifyAllAboutChanges(ae);
	}		
	
	public EditorTolls getCurrentSelectedTool() {
		return currentSelectedTool;
	}

	public void setCurrentSelectedTool(EditorTolls currentSelectedTool) {
		this.currentSelectedTool = currentSelectedTool;
	}
	
	public GCommand getSelectedVertex() {
		return selectedVertex;
	}

	public void setSelectedVertex(GCommand selectedVertex, List<GCommand> nearSelectedVertex) {
		this.selectedVertex = selectedVertex;
		this.nearSelectedVertex = nearSelectedVertex;
		ActionEvent ae = new ActionEvent(new Object() , -1, "chemaChanged");
		notifyAllAboutChanges(ae);
	}
	
	public void repaint(){
		ActionEvent ae = new ActionEvent(new Object() , -1, "chemaChanged");
		notifyAllAboutChanges(ae);
	}
	
	
	public List<GCommand> getNearSelectedVertex() {
		return nearSelectedVertex;
	}
	
	public Editor.EditMode getCurrentEditMode() {
		return currentEditMode;
	}

	public void setCurrentEditMode(Editor.EditMode currentEditMode) {
		this.currentEditMode = currentEditMode;
	}

	//------------
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(ActionEvent ae){
		for(ActionListener al : listeners){			
			al.actionPerformed(ae);
		}
	}

}
