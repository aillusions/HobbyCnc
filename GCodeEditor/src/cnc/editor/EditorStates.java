package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import cnc.editor.Editor.EditModeS;
import cnc.editor.Editor.EditorTolls;
import cnc.editor.Editor.GcommandTypes;
import cnc.editor.view.GCodesTextContainer;

//Singleton
public class EditorStates {

	public static final String CMD_REFRESH_ARC_RADUIS = "refreshArcRaduis";
	public static final String CMD_REFRESH_ARC_J = "refreshArcJ";
	public static final String CMD_REFRESH_ARC_I = "refreshArcI";
	public static final String CMD_CLEAR_SELECTION = "clearSelection";
	public static final String CMD_SET_NEW_CORRENT_CMD_TYPE = "setNewCmdType";
	
	public static final float NODE_CIRCLE_SIZE = 8f;
	public static final float BMP_TO_CNC_COORD_RATIO = 5f;	
	
	private EditorStates(){}
	
	private static final EditorStates instance = new EditorStates();
	public static EditorStates getInstance(){
		return instance;
	}	
	
	/**
	 * measured in view coordinates: pixel
	 */
	public class SelectedRegion{
		
		private boolean selectionStarted = false;
		private int startX;
		private int startY;
		private int endX;
		private int endY;
		
		public boolean isSelectionStarted() {
			return selectionStarted;
		}

		public void startSelection(int x, int y){
			startX = x;
			startY = y;
			endX = x;
			endY = y;
			selectionStarted = true;
		}
		
		public void setEndOfSelection(int x, int y){
			
			if(!selectionStarted){
				throw new RuntimeException("selection was not started");
			}
			endX = x;
			endY = y;
			
			ActionEvent ae = new ActionEvent(this, -1, "changedSelectedRegion");
			notifyAllAboutChanges(ae);
		}
		
		public int getStartX() {
			return startX;
		}
		public int getStartY() {
			return startY;
		}
		public int getEndX() {
			return endX;
		}
		public int getEndY() {
			return endY;
		}

		public void clear() {
			startX = 0;
			startY = 0;
			endX = 0;
			endY = 0;
			selectionStarted = false;
		}
	}
	
	private List<ActionListener> listeners = new ArrayList<ActionListener>();		
	private EditorTolls currentSelectedTool = EditorTolls.SIMPLE_EDIT;		
	private int theGap = 20;
	private int viewCoordLenghtX = 600;		
	private int viewCoordLenghtY = 200;		
	private float viewScale = 1;	
	private Set<GCommand> selectedCommands;
	private Editor.EditModeS currentEditMode = EditModeS.DRAW;
	private boolean importInProgress;
	private final Document document = new PlainDocument();
	private GCodesTextContainer gCodesTextContainer;
	private GcommandTypes currentGCmdType = Editor.GcommandTypes.G00;
	private Float arcRadius = 20f;
	private Float arcI;
	private Float arcJ;
	private boolean liftForEachStroke = false;
	private boolean displayOnlyZ0 = false;
	
	//View coordinates (pixels)
	private SelectedRegion selectedRegion;
	
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
	
	public static long convertPositionCnc_View(float cncCoord){
		return Math.round(cncCoord * instance.getScale() * BMP_TO_CNC_COORD_RATIO)+ instance.getGap();
	}
	
	public static float convertPositionView_Cnc(long viewCoord){
		return (viewCoord - instance.getGap()) / instance.getScale() / BMP_TO_CNC_COORD_RATIO;
	}	
	
	public static long convertLengthCnc_View(float cncCoord){
		return Math.round(cncCoord * instance.getScale() * BMP_TO_CNC_COORD_RATIO);
	}
	
	public static float convertLengthView_Cnc(long viewCoord){
		return (viewCoord) / instance.getScale() / BMP_TO_CNC_COORD_RATIO;
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
		ActionEvent ae = new ActionEvent(this, -1, "setScale");
		notifyAllAboutChanges(ae);
	}
	
	public int getGap(){
		return theGap;
	}
	
	public void setGap(int gap){
		theGap = gap;
		ActionEvent ae = new ActionEvent(this, -1, "setGap");
		notifyAllAboutChanges(ae);
	}
	
	public int getViewCoordLenghtX() {
		return viewCoordLenghtX;
	}

	public void setViewCoordLenghtX(int viewCoordLenghtX) {
		this.viewCoordLenghtX = viewCoordLenghtX;
		ActionEvent ae = new ActionEvent(this, -1, "coordLenghtChangedX");
		notifyAllAboutChanges(ae);
	}

	public int getViewCoordLenghtY() {
		return viewCoordLenghtY;
	}

	public void setViewCoordLenghtY(int viewCoordLenghtY) {
		this.viewCoordLenghtY = viewCoordLenghtY;
		ActionEvent ae = new ActionEvent(this, -1, "coordLenghtChangedY");
		notifyAllAboutChanges(ae);
	}

	public EditorTolls getCurrentSelectedTool() {
		return currentSelectedTool;
	}

	public void setCurrentSelectedTool(EditorTolls currentSelectedTool) {
		this.currentSelectedTool = currentSelectedTool;
	}
	
	public Set<GCommand> getSelectedGCommands() {
		return selectedCommands;
	}

	public Set<GCommand> getNearSelectedGCommands() {
		
		if(selectedCommands != null && selectedCommands.size() == 1){
			return GCommandsContainer.getInstance().getNeighbourVertexes(((GCommand)selectedCommands.toArray()[0]));
		}
		return null;
	}
	
	public void removeGCommandsFromSelected(Collection<GCommand> vertexes) {
		selectedCommands.removeAll(vertexes);
		ActionEvent ae = new ActionEvent(this, -1, "setSelectedVertex");
		notifyAllAboutChanges(ae);
	}
	
	public void setSelectedGCommands(List<GCommand> selectedGCommands) {

		this.selectedCommands = new HashSet<GCommand>();
		this.selectedCommands.addAll(selectedGCommands);
		ActionEvent ae = new ActionEvent(this, -1, "setSelectedVertex");
		notifyAllAboutChanges(ae);
	}
	
	public void addToSelectedGCommands(List<GCommand> selectedGCommands) {
	
		if(this.selectedCommands == null){
			this.selectedCommands = new HashSet<GCommand>();
		}

		this.selectedCommands.addAll(selectedGCommands);
		
		ActionEvent ae = new ActionEvent(this, -1, "setSelectedVertex");
		notifyAllAboutChanges(ae);
	}
	
	public void clearSelection(){
		this.selectedCommands = null;
		ActionEvent ae = new ActionEvent(this , -1, CMD_CLEAR_SELECTION);
		notifyAllAboutChanges(ae);
	}
		
	public Editor.EditModeS getCurrentEditMode() {
		return currentEditMode;
	}

	public void setCurrentEditMode(Editor.EditModeS currentEditMode) {
		this.currentEditMode = currentEditMode;
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

	
	public Editor.GcommandTypes getCurrentGCmdType() {
		return currentGCmdType;
	}

	public void setCurrentGCmdType(Editor.GcommandTypes currentGCmdType) {
		this.currentGCmdType = currentGCmdType;
	}
	
	public Float getArcR() {
		return arcRadius;
	}

	public void setArcR(Float radius, boolean updateMainFrame) {
		
		this.arcRadius = radius;
		if(this.arcRadius != null){
			setArcJ(null, true);
			setArcI(null, true);
		}
		
		if(updateMainFrame){
			ActionEvent ae = new ActionEvent(this, -1, CMD_REFRESH_ARC_RADUIS);
			notifyAllAboutChanges(ae);
		}
	}	
	
	public Float getArcI() {
		return arcI;
	}

	public void setArcI(Float arcI, boolean updateMainFrame) {
		
		this.arcI = arcI;
		if(this.arcI != null){
			setArcR(null, true);
		}		
		
		if(updateMainFrame){
			ActionEvent ae = new ActionEvent(this, -1, CMD_REFRESH_ARC_I);
			notifyAllAboutChanges(ae);
		}
	}

	public Float getArcJ() {
		return arcJ;
	}

	public void setArcJ(Float arcJ, boolean updateMainFrame) {
		
		this.arcJ = arcJ;
		if(this.arcJ != null){
			setArcR(null, true);
		}

		if(updateMainFrame){
			ActionEvent ae = new ActionEvent(this, -1, CMD_REFRESH_ARC_J);
			notifyAllAboutChanges(ae);
		}
	}

	public boolean isLiftForEachStroke() {
		return liftForEachStroke;
	}

	public void setLiftForEachStroke(boolean liftHeadForNextStroke) {
		this.liftForEachStroke = liftHeadForNextStroke;
	}

	public boolean isDisplayOnlyZ0() {
		return displayOnlyZ0;
	}

	public void setDisplayOnlyZ0(boolean displayOnlyZ0) {
		this.displayOnlyZ0 = displayOnlyZ0;
		ActionEvent ae = new ActionEvent(this, -1, "displayOnlyZ0Switched");
		notifyAllAboutChanges(ae);
	}


	public SelectedRegion getSelRegion() {
		if(selectedRegion == null){
			selectedRegion = new SelectedRegion();
		}
		return selectedRegion;
	}

	public void clearSelRegion() {
		if(selectedRegion == null){
			selectedRegion = new SelectedRegion();
		}else{
			selectedRegion.clear();
		}
		ActionEvent ae = new ActionEvent(this, -1, "clearSelectedRegion");
		notifyAllAboutChanges(ae);
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
