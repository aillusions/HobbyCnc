package cnc.editor.listener;

import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import cnc.editor.Editor;

public class VisualisationPanelListener implements MouseListener, MouseMotionListener {

	private Editor editor;


	public VisualisationPanelListener(Editor editor){
		this.editor = editor;
	}
	
	public void mousePressed(MouseEvent e) {
	
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();		
		
		editor.mousePressedAt(x,y, e.isControlDown());
	}

	public void mouseDragged(MouseEvent e) {		
			
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();		

		editor.viewMouseDraggedTo(x,y);

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
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();	
		
		editor.viewMouseReleasedAt(x, y);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

}
