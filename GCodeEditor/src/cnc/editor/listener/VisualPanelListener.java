package cnc.editor.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.EditorVertex;
import cnc.editor.GCommand;

public class VisualPanelListener implements MouseListener, MouseMotionListener {

	Editor editor;
	
	public VisualPanelListener(Editor editor){
		this.editor = editor;
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();		

		editor.viewMousePressed(x,y);
	}

	public void mouseReleased(MouseEvent e) {
		
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
			
			if(Math.abs(x-X) < 30 && Math.abs(y-Y) < 30){
				v.setX(EditorStates.convertView_Cnc((long)x));
				v.setY(EditorStates.convertView_Cnc((long)y));
			}
			es.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {

	}

}
