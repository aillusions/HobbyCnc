package cnc.editor.listener;

import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.EditorVertex;
import cnc.editor.GCommand;

public class VisualisationPanelListener implements MouseListener, MouseMotionListener {

	private Editor editor;
	private boolean dragStarted = false;
	
	public VisualisationPanelListener(Editor editor){
		this.editor = editor;
	}
	
	public void mousePressed(MouseEvent e) {
		
		EditorStates.getInstance().setCurrentEditMode(Editor.EditModeS.DRAW);
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();		

		editor.viewMousePressed(x,y);
	}

	public void mouseDragged(MouseEvent e) {
		
		EditorStates es = EditorStates.getInstance();
		GCommand gc = es.getSelectedVertex();

		if(gc != null){
			
			EditorVertex v =  es.getSelectedVertex().getVertex();
			
			double x = e.getPoint().getX();
			double y = e.getPoint().getY();		
			
			double X = EditorStates.convertCnc_View(v.getX());
			double Y = EditorStates.convertCnc_View(v.getY());
			
			if(Math.abs(x-X) < getSpan() && Math.abs(y-Y) < getSpan() ){
				dragStarted = true;
				v.setX(EditorStates.convertView_Cnc((long)x));
				v.setY(EditorStates.convertView_Cnc((long)y));
			}
		}
	}
	
	private float getSpan(){
		
		if(dragStarted){
			return EditorStates.SELECTIO_CIRCLE_SIZE * 100;
		}
		
		return EditorStates.SELECTIO_CIRCLE_SIZE;
	}

	public void mouseMoved(MouseEvent e) {
		((JPanel)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void focusGained(FocusEvent e) {
					
	}

	public void focusLost(FocusEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		dragStarted = false;
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

}
