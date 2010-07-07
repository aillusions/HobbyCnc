package cnc.editor.domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.view.GraphicsWrapper;

public class Figure {

	private final List<FigurePoint> figurePoints = new LinkedList<FigurePoint>();
	private final List<FigureLine> figureLines = new LinkedList<FigureLine>();
	
	protected EditorStates es = EditorStates.getInstance();	

	public void addPoint(float x, float y, Editor.GcommandTypes lineType){
		
		FigurePoint fp = new FigurePoint(x, y);
		
		if(figurePoints.size() > 0){
			
			if(lineType.equals(Editor.GcommandTypes.G00)){
				FigureLine line = new FigureLineG00(figurePoints.get(figurePoints.size()-1), fp);
				figureLines.add(line);
			}
		}
		
		figurePoints.add(fp);		
	}
	
	public void draw(GraphicsWrapper g){
		
		Set<FigurePoint> selectedCCmd = es.getSelectedGCommands();
		Set<FigurePoint> nearSelection = es.getNearSelectedGCommands();

		for(FigureLine l : figureLines){
			
			if(selectedCCmd != null && selectedCCmd.contains(l.getPointTo())){
				g.setColor(Color.ORANGE);	
			}else{
				g.setColor(Color.black);
			}
			
			l.draw(g);
		}
		
		for(FigurePoint p : figurePoints){
				
			if(selectedCCmd != null && selectedCCmd.contains(p)){
				g.setColor(Color.ORANGE);
			}else if(nearSelection != null && nearSelection.contains(p)){
				g.setColor(Color.gray);	
			}else{
				g.setColor(Color.black);	
			}
						
			p.draw(g);
		}
		
	}

	public List<FigurePoint> getFigurePoints() {
		return figurePoints;
	}

	public List<FigureLine> getFigureLines() {
		return figureLines;
	}

	public void close(Editor.GcommandTypes lineType) {
		if(figurePoints.size() > 1){
			
			if(lineType.equals(Editor.GcommandTypes.G00)){
				FigureLine line = new FigureLineG00(figurePoints.get(figurePoints.size()-1), figurePoints.get(0));
				figureLines.add(line);
			}
		}		
	}

	
	public List<FigureLine> getLinesForFromPoint(FigurePoint point){
	
		List<FigureLine> result = new ArrayList<FigureLine>();
		
		for(FigureLine l : figureLines){			
			if(l.getPointFrom().equals(point)){ 
				result.add(l);
			}
		}
		return result;
	}
	
	public void removePoints(Set<FigurePoint> selectedPoints) {
		
		List<FigureLine> toRemove = new ArrayList<FigureLine>();
		
		for(FigurePoint p : selectedPoints){
			for(FigureLine l : figureLines){
				
				if(l.getPointTo().equals(p)){ 
					toRemove.add(l);
				}
			}
		}
		
		if(toRemove.size() == 0){
			return;
		}
		
		for(FigureLine l : toRemove){
			
			FigurePoint removePoint = l.getPointTo();
			FigurePoint rebasePoint = l.getPointFrom();
			List<FigureLine> linesToRabase = getLinesForFromPoint(removePoint);			
			
			for(FigureLine ltr : linesToRabase){
				ltr.setPointFrom(rebasePoint);
			}
		}
		
		figureLines.removeAll(toRemove);
		
		figurePoints.removeAll(selectedPoints);		
	}
	
}
