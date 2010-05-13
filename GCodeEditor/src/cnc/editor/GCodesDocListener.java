package cnc.editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cnc.editor.view.EditorViewFrame;

public class GCodesDocListener implements DocumentListener {

	EditorViewFrame frame;
		
	public EditorViewFrame getFrame() {
		return frame;
	}

	public void setFrame(EditorViewFrame frame) {
		this.frame = frame;
	}

	public GCodesDocListener(EditorViewFrame editorViewFrame) {
		this.frame = editorViewFrame;
	}

	public void changedUpdate(DocumentEvent e) {
		frame.repaintVisualPanel();		
	}

	public void insertUpdate(DocumentEvent e) {
		frame.repaintVisualPanel();			
	}

	public void removeUpdate(DocumentEvent e) {
		frame.repaintVisualPanel();			
	}

}
