package cnc.editor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cnc.editor.Editor.EditorTolls;
import cnc.editor.Editor.GcommandTypes;
import cnc.editor.domain.FigurePoint;
import cnc.editor.domain.FiguresContainer;

//Singleton
public class EditorStates {

	public static final String CMD_SET_SCALE = "setScale";
	public static final String CMD_REFRESH_ARC_RADUIS = "refreshArcRaduis";
	public static final String CMD_REFRESH_ARC_J = "refreshArcJ";
	public static final String CMD_REFRESH_ARC_I = "refreshArcI";
	public static final String CMD_CLEAR_SELECTION = "clearSelection";
	public static final String CMD_SET_NEW_CORRENT_CMD_TYPE = "setNewCmdType";
	
	public static final float NODE_CIRCLE_SIZE = 8f;
	public static final float BMP_TO_CNC_COORD_RATIO = 5f;	
	
	private static final EditorStates instance = new EditorStates();
	
	public static EditorStates getInstance(){
		return instance;
	}
	
	private float cuttingDepth = 0f;
	
	
	public float getCuttingDepth() {
		return cuttingDepth;
	}

	private List<ActionListener> listeners = new ArrayList<ActionListener>();		
	private EditorTolls currentSelectedTool = EditorTolls.SIMPLE_EDIT;		
	private int theGap = 25;
	private int viewCoordLenghtX = 600;		
	private int viewCoordLenghtY = 200;		
	private float viewScale = 1;	
	private List<FigurePoint> selectedCommands;
	private boolean importInProgress;
	private GcommandTypes currentGCmdType = Editor.GcommandTypes.G00;
	private Float arcRadius = 20f;
	private Float arcI;
	private Float arcJ;
	private boolean liftForEachStroke = false;
	private boolean displayOnlyZ0 = false;
	private Point mousePosition = new Point();
	private boolean drawFacilities = true;
	//View coordinates (pixels)
	private SelectedRegion selectedRegion;
	//CNC coordinates (mm) - not pixels!!
	private int gridStep = 5;	
	//CNC coordinates (mm) - not pixels!!
	private int maxCncY = 46;	
	//CNC coordinates (mm) - not pixels!!
	private int maxCncX = 134;
	
	private EditorStates(){}
	
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
	
	//Getters - setters
	public float getScale(){
		return viewScale;
	}
	
	public void setScale(float scale){
		viewScale = scale;
		ActionEvent ae = new ActionEvent(this, -1, CMD_SET_SCALE);
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
	
	public List<FigurePoint> getSelectedGCommands() {
		return selectedCommands;
	}

	public List<FigurePoint> getNearSelectedGCommands() {
		
		if(selectedCommands != null && selectedCommands.size() == 1){
			return FiguresContainer.getInstance().getNeighbourVertexes(((FigurePoint)selectedCommands.toArray()[0]));
		}
		return null;
	}
	
	public void removeGCommandsFromSelected(Collection<FigurePoint> vertexes) {
		selectedCommands.removeAll(vertexes);
		ActionEvent ae = new ActionEvent(this, -1, "setSelectedVertex");
		notifyAllAboutChanges(ae);
	}
	
	public void setSelectedGCommands(List<FigurePoint> selectedGCommands) {

		this.selectedCommands = new ArrayList<FigurePoint>();
		this.selectedCommands.addAll(selectedGCommands);
		ActionEvent ae = new ActionEvent(this, -1, "setSelectedVertex");
		notifyAllAboutChanges(ae);
	}
	
	public void addToSelectedGCommands(List<FigurePoint> selectedGCommands) {
	
		if(this.selectedCommands == null){
			this.selectedCommands = new ArrayList<FigurePoint>();
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
	
	public boolean isImportInProgress() {
		return importInProgress;
	}

	public void setImportInProgress(boolean importInProgress) {
		this.importInProgress = importInProgress;
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
	
	
	public boolean isDrawFacilities() {
		return drawFacilities;
	}

	public void setDrawFacilities(boolean drawFacilities) {
		
		this.drawFacilities = drawFacilities;
		
		ActionEvent ae = new ActionEvent(this, -1, "drawFacilitiesSwitched");
		notifyAllAboutChanges(ae);
	}
	
	public Point getMousePosition() {
		return mousePosition;
	}

	public void setMousePosition(Point mousePosition) {
		
		this.mousePosition = mousePosition;
		
		ActionEvent ae = new ActionEvent(this, -1, "mouseMoved");
		notifyAllAboutChanges(ae);
	}

}
