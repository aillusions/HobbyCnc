package cnc.editor.domain;

import cnc.editor.EditorStates;
import cnc.editor.Editor.GcommandTypes;
import cnc.editor.view.GraphicsWrapper;

public class FigureLineG00 extends FigureLine{	

	public FigureLineG00(FigurePoint from, FigurePoint to) {		
		super(from, to);
	}
	
	@Override
	public void drawLine(GraphicsWrapper g) {

		int prevX = (int)EditorStates.convertPositionCnc_View(getPointFrom().getX());
		int prevY = (int)EditorStates.convertPositionCnc_View(getPointFrom().getY());
		
		int newX = (int)EditorStates.convertPositionCnc_View(getPointTo().getX()); 
		int newY = (int)EditorStates.convertPositionCnc_View(getPointTo().getY()); 	
		
		g.drawLineWithScaleThickness(prevX, prevY, newX, newY);
	
	}

	@Override
	public GcommandTypes getLineType() {		
		return GcommandTypes.G00;
	}
}
