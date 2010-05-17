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
import cnc.editor.Editor.EditorTolls;

public class VisualisationPanelListener implements MouseListener, MouseMotionListener {

	private Editor editor;
	private boolean dragStarted = false;
	private boolean continuousDrawStarted = false;
	private EditorStates es = EditorStates.getInstance();
	
	public VisualisationPanelListener(Editor editor){
		this.editor = editor;
	}
	
	public void mousePressed(MouseEvent e) {
		
		es.setCurrentEditMode(Editor.EditModeS.DRAW);
		
		if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){
			continuousDrawStarted = true;
		}
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();		

		editor.viewMousePressed(x,y);
	}

	public void mouseDragged(MouseEvent e) {
		
		if(continuousDrawStarted){
			
			double x = e.getPoint().getX();
			double y = e.getPoint().getY();		

			editor.viewMousePressed(x,y);
			
		}else{		
			
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
	}
	
	private float getSpan(){
		
		if(dragStarted){
			return EditorStates.SELECTIO_CIRCLE_SIZE * 100;
		}
		
		return EditorStates.SELECTIO_CIRCLE_SIZE;
	}

	public void mouseMoved(MouseEvent e) {	
		
		//TODO add condition - vertex hover
		((JPanel)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void focusGained(FocusEvent e) {
					
	}
	
	public void focusLost(FocusEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
		dragStarted = false;
		continuousDrawStarted = false;
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

}
