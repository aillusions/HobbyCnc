package cnc.editor.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import cnc.editor.Editor;

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
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {

	}

}
