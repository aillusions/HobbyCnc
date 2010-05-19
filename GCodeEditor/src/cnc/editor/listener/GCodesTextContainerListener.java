package cnc.editor.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cnc.editor.Editor;
import cnc.editor.EditorStates;

public class GCodesTextContainerListener implements FocusListener, MouseListener, KeyListener {

	public void focusGained(FocusEvent e) {
		EditorStates.getInstance().setCurrentEditMode(Editor.EditModeS.TXT);		
	}

	public void focusLost(FocusEvent e) {
		
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
		EditorStates.getInstance().setCurrentEditMode(Editor.EditModeS.TXT);
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() != 17){
			EditorStates.getInstance().setCurrentEditMode(Editor.EditModeS.TXT);	
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

}
