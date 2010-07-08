package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cnc.editor.domain.figure.Figure;
import cnc.editor.domain.figure.FLine;
import cnc.editor.domain.figure.FPoint;
import cnc.editor.domain.gcmd.GCommand;
import cnc.editor.view.GraphicsWrapper;

public class FiguresContainer {
	
	public static final String CMD_COMMAND_REPLACED_MANUALLY = "commandReplacedManually";
	public static final String CMD_REMOVED_COMMANDS = "removedCommands";
	public static final String CMD_CLEAR_COMMANDS_CONATINER = "clear_commands_conatiner";
	public static final String CMD_ADDED_BUNCH_OF_COMMANDS = "added_bunch_of_commands";
	public static final String CMD_ADDED_ONE_COMMAND = "close_current_figure";
	public static final String CMD_CLOSE_CURRENT_FIGURE = "added_one_command";

	private EditorStates es = EditorStates.getInstance();
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();	
	
	private final List<Figure> figuresList = new LinkedList<Figure>();

	private static final FiguresContainer instance = new FiguresContainer();

	public static FiguresContainer getInstance() {
		return instance;
	}

	Figure currentFigure = null;

	public Figure getCurrentFigure() {

		if (currentFigure == null) {
			currentFigure = new Figure();
			figuresList.add(currentFigure);
		}

		return currentFigure;
	}

	public void draw(GraphicsWrapper g) {

		for (Figure f : figuresList) {

			f.draw(g);
		}
	}
	
	public List<FPoint> findPointsNear(float cncX, float cncY) {
		
		EditorStates es = EditorStates.getInstance();
		List<FPoint> result = new ArrayList<FPoint>();
		
		for(Figure v : figuresList){
			for(FPoint p : v.getFigurePoints()){
				if(0.8/es.getScale() > Math.abs(p.getX() - cncX) 
						&& 0.8/es.getScale() > Math.abs(p.getY() - cncY)){
					result.add(p);
				}
			}
		}
		return result;
	}
		
	public List<FPoint> findVertexesInRegion(float startX, float startY, float endX, float endY) {
		
		float left = Math.min(startX, endX);
		float top = Math.min(startY, endY);
		float rigth = Math.max(startX, endX);
		float bottom = Math.max(startY, endY);
				
		List<FPoint> result = new ArrayList<FPoint>();
		
		for(Figure v : figuresList){
			for(FPoint p : v.getFigurePoints()){
				if(left <= p.getX() 
					&& rigth >= p.getX() 
					&& top <= p.getY()
					&& bottom >= p.getY()){
					result.add(p);
				}
			}
		}
		return result;
	}
	
	public List<FPoint> getNeighbourVertexes(FPoint gCommand) {
		
		List<FPoint> result = new ArrayList<FPoint>();
		
		for(Figure v : figuresList){
			
			if(v.getFigurePoints().contains(gCommand)){
				
				int indexOfCurrent = v.getFigurePoints().indexOf(gCommand);
				if(indexOfCurrent - 1 >= 0){
					result.add(v.getFigurePoints().get(indexOfCurrent - 1));
				}
				if(indexOfCurrent + 1 < v.getFigurePoints().size()){
					result.add(v.getFigurePoints().get(indexOfCurrent + 1));
				}
				break;
			}
		}
		
		return result;
	}
	
	
	public void clear() {
		
		figuresList.clear();
		currentFigure = null;
		
		ActionEvent ae = new ActionEvent(this , -1, CMD_CLEAR_COMMANDS_CONATINER);
		notifyAllAboutChanges(ae);		
	}
	
	private void notifyAllAboutChanges(ActionEvent e){	
		
		for(ActionListener al : listeners){
			al.actionPerformed(e);
		}
	}	
	
	public void addActionListener(ActionListener al){
		
		listeners.add(al);
	}
	
	
	public void createNewFigure() {	
		currentFigure = new Figure();
		figuresList.add(currentFigure);		
	}

	public void closeFigure() {
		
		currentFigure.closeFigure(es.getCurrentGCmdType());
		
		ActionEvent ae = new ActionEvent(this , -1, CMD_CLOSE_CURRENT_FIGURE);
		notifyAllAboutChanges(ae);
	}

	public List<FPoint> getAllPointsList() {

		List<FPoint> result = new ArrayList<FPoint>();
		
		for(Figure v : figuresList){
			result.addAll(v.getFigurePoints());		
		}
		return result;
	}
	
	public List<FLine> getAllLinesList() {

		List<FLine> result = new ArrayList<FLine>();
		
		for(Figure v : figuresList){
			result.addAll(v.getFigureLines());		
		}
		return result;
	}

	public void removePoints(List<FPoint> selectedPoints) {
	
		for(Figure f : figuresList){			
			f.removePoints(selectedPoints);
		}
		
		ActionEvent ae = new ActionEvent(this , -1, CMD_REMOVED_COMMANDS);
		notifyAllAboutChanges(ae);
	}

	public void addCommandsBunch(String commands) {
		
		//butchOfCmdsAddingInProgress = true;
		
		String[] cmdArray = commands.replace("\r", "").split("\n");		
		
		for (int i = 0; i < cmdArray.length; i++) {
			
			try{
				
				GCommand gc = GCodeParser.parseCommand(cmdArray[i]);
				
				if (gc != null) {	
					
					if(gc.getZ() != null){
						
						if(gc.getZ() > 0 && getCurrentFigure().getFigurePoints().size()>0){						
							createNewFigure();
						}
						continue;
					}
					
					getCurrentFigure().addPoint(gc);					
				}	
				
			}catch(Exception e){
				e.printStackTrace();
			}		
		}

		ActionEvent ae = new ActionEvent(this , -1, CMD_ADDED_BUNCH_OF_COMMANDS);
		notifyAllAboutChanges(ae);	
			
		//System.out.println(gCommandList.size());
		//butchOfCmdsAddingInProgress = false;
		
	}

}
