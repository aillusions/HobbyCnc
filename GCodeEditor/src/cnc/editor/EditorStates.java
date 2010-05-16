package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import cnc.editor.Editor.EditMode;
import cnc.editor.Editor.EditorTolls;
import cnc.editor.view.GCodesTextContainer;

//Singleton
public class EditorStates {

	public static final String CMD_CLEAR_VIEW = "clearView";
	public static final float SELECTIO_CIRCLE_SIZE = 8f;
	public static final float BMP_TO_CNC_COORD_RATIO = 5f;	
	
	private EditorStates(){}
	
	private static final EditorStates instance = new EditorStates();
	public static EditorStates getInstance(){
		return instance;
	}	
	
	private List<ActionListener> listeners = new ArrayList<ActionListener>();		
	private EditorTolls currentSelectedTool = EditorTolls.SIMPLE_EDIT;		
	private int theGap = 15;
	private int viewCoordLenghtX = 600;		
	private int viewCoordLenghtY = 200;		
	private float viewScale = 1;	
	private GCommand selectedVertex;
	private List<GCommand> nearSelectedVertex;
	private Editor.EditMode currentEditMode = EditMode.DRAW;
	private boolean importInProgress;
	private final Document document = new PlainDocument();
	private GCodesTextContainer gCodesTextContainer;
	
	//CNC coordinates (mm) - not pixels!!
	private int gridStep = 5;
	
	//CNC coordinates (mm) - not pixels!!
	private int maxCncY = 46;
	
	//CNC coordinates (mm) - not pixels!!
	private int maxCncX = 134;
	
	public int getLineStartOffset(int editorLineIndex) throws BadLocationException{
		return gCodesTextContainer.getLineStartOffset(editorLineIndex);
	}
	
	public int getLineEndOffset(int editorLineIndex) throws BadLocationException{
		return gCodesTextContainer.getLineEndOffset(editorLineIndex);
	}
	
	public int getLineOfOffset(int offset) throws BadLocationException{
		return gCodesTextContainer.getLineOfOffset(offset);
	}
	
	public static long convertCnc_View(float cncCoord){
		return Math.round(cncCoord * instance.getScale() * BMP_TO_CNC_COORD_RATIO)+ instance.getGap();
	}
	
	public static float convertView_Cnc(long viewCoord){
		return (viewCoord - instance.getGap()) / instance.getScale() / BMP_TO_CNC_COORD_RATIO;
	}	
	
	//In case there is no spaces in text editor
	public static int getLineNumberInTextEditor(GCommand cmd){
		return GCommandsContainer.getInstance().getGCommandList().indexOf(cmd);
	}
	//In case there is no spaces in text editor
	public static int getGCommandByLineNumber(int lineNumber){
		return lineNumber;
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
	
	public int getViewCoordLenghtX() {
		return viewCoordLenghtX;
	}

	public void setViewCoordLenghtX(int viewCoordLenghtX) {
		this.viewCoordLenghtX = viewCoordLenghtX;
		ActionEvent ae = new ActionEvent(new Object() , -1, "coordLenghtChangedX");
		notifyAllAboutChanges(ae);
	}

	public int getViewCoordLenghtY() {
		return viewCoordLenghtY;
	}

	public void setViewCoordLenghtY(int viewCoordLenghtY) {
		this.viewCoordLenghtY = viewCoordLenghtY;
		ActionEvent ae = new ActionEvent(new Object() , -1, "coordLenghtChangedY");
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
	
	public void clearSelection(){
		this.selectedVertex = null;
		this.nearSelectedVertex = null;
		ActionEvent ae = new ActionEvent(new Object() , -1, CMD_CLEAR_VIEW);
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
		if(currentEditMode == EditMode.TXT){
			 clearSelection();
		}
	}
	
	public boolean isImportInProgress() {
		return importInProgress;
	}

	public void setImportInProgress(boolean importInProgress) {
		this.importInProgress = importInProgress;
	}
	
	public Document getDocument() {
		return document;
	}
	
	
	public GCodesTextContainer getgCodesTextContainer() {
		return gCodesTextContainer;
	}

	public void setgCodesTextContainer(GCodesTextContainer gCodesTextContainer) {
		this.gCodesTextContainer = gCodesTextContainer;
	}
	
	/**
	 * 	CNC coordinates (mm) - not pixels!!
	 */
	public int getGridStep() {
		return gridStep;
	}
	
	/**
	 * 	CNC coordinates (mm) - not pixels!!
	 */
	public void setGridStep(int gridStep) {
		this.gridStep = gridStep;
	}
	
	/**
	 * 	CNC coordinates (mm) - not pixels!!
	 */
	public int getMaxCncY() {
		return maxCncY;
	}

	/**
	 * 	CNC coordinates (mm) - not pixels!!
	 */
	public void setMaxCncY(int maxCncY) {
		this.maxCncY = maxCncY;
	}

	/**
	 * 	CNC coordinates (mm) - not pixels!!
	 */
	public int getMaxCncX() {
		return maxCncX;
	}

	/**
	 * 	CNC coordinates (mm) - not pixels!!
	 */
	public void setMaxCncX(int maxCncX) {
		this.maxCncX = maxCncX;
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
