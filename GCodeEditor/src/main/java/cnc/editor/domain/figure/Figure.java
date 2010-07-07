package cnc.editor.domain.figure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.Editor.GcommandTypes;
import cnc.editor.view.GraphicsWrapper;

public class Figure {

	private final List<FPoint> figurePoints = new LinkedList<FPoint>();
	private final List<FLine> figureLines = new LinkedList<FLine>();
	
	protected EditorStates es = EditorStates.getInstance();	

	public void addPoint(float x, float y, Editor.GcommandTypes lineType){		
		
		boolean useExisting = false;
		FPoint fp = new FPoint(x, y);
		
		if(figurePoints.contains(fp)){
			int index = figurePoints.indexOf(fp);
			fp = figurePoints.get(index);
			useExisting = true;
		}
			
		if(figurePoints.size() > 0){
			
			FLine line = null;
			
			if(lineType == GcommandTypes.G00){					
				line = new FLineG00(figurePoints.get(figurePoints.size()-1), fp);
			}else if(lineType == GcommandTypes.G01){
				line = new LineG01(figurePoints.get(figurePoints.size()-1), fp);
			}else if(lineType == GcommandTypes.G02){
				if(es.getArcR() != null){
					line = new LineG02(figurePoints.get(figurePoints.size()-1), fp, es.getArcR());
				}else{
					line = new LineG02(figurePoints.get(figurePoints.size()-1), fp, es.getArcI(), es.getArcJ());
				}
			}else if(lineType == GcommandTypes.G03) {
				if(es.getArcR() != null){
					line = new LineG03(figurePoints.get(figurePoints.size()-1), fp, es.getArcR());
				}else{
					line = new LineG03(figurePoints.get(figurePoints.size()-1), fp, es.getArcI(), es.getArcJ());
				}
			}
			
			if(line != null){
				figureLines.add(line);
			}
		}
		
		if(!useExisting){
			figurePoints.add(fp);	
		}
			
	}
	
	public void draw(GraphicsWrapper g){
		
		List<FPoint> selectedCCmd = es.getSelectedGCommands();
		List<FPoint> nearSelection = es.getNearSelectedGCommands();

		for(FLine l : figureLines){
			
			if(selectedCCmd != null && selectedCCmd.contains(l.getPointTo())){
				g.setColor(Color.ORANGE);	
			}else{
				g.setColor(Color.black);
			}
			
			l.draw(g);
		}
		
		for(FPoint p : figurePoints){
				
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

	public List<FPoint> getFigurePoints() {
		return figurePoints;
	}

	public List<FLine> getFigureLines() {
		return figureLines;
	}

	public void close(Editor.GcommandTypes lineType) {
		if(figurePoints.size() > 1){
			
			if(lineType.equals(Editor.GcommandTypes.G00)){
				FLine line = new FLineG00(figurePoints.get(figurePoints.size()-1), figurePoints.get(0));
				figureLines.add(line);
			}
		}		
	}

	
	public List<FLine> getLinesForFromPoint(FPoint point){
	
		List<FLine> result = new ArrayList<FLine>();
		
		for(FLine l : figureLines){			
			if(l.getPointFrom().equals(point)){ 
				result.add(l);
			}
		}
		return result;
	}
	
	public void removePoints(List<FPoint> selectedPoints) {
		
		List<FLine> toRemove = new ArrayList<FLine>();
		
		for(FPoint p : selectedPoints){
			for(FLine l : figureLines){
				
				if(l.getPointTo().equals(p)){ 
					toRemove.add(l);
				}
			}
		}
		
		if(toRemove.size() == 0){
			return;
		}
		
		for(FLine l : toRemove){
			
			FPoint removePoint = l.getPointTo();
			FPoint rebasePoint = l.getPointFrom();
			List<FLine> linesToRabase = getLinesForFromPoint(removePoint);			
			
			for(FLine ltr : linesToRabase){
				ltr.setPointFrom(rebasePoint);
			}
		}
		
		figureLines.removeAll(toRemove);
		
		figurePoints.removeAll(selectedPoints);		
	}
	
}
