package cnc.editor.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cnc.editor.VertexesContainer;

public class GCodesDocListener implements DocumentListener {

	VertexesContainer container;
		
	public GCodesDocListener(VertexesContainer container) {
		this.container = container;
	}

	public void changedUpdate(DocumentEvent e) {		
		container.regenerate(e.getDocument());		
	}

	public void insertUpdate(DocumentEvent e) {
		container.regenerate(e.getDocument());			
	}

	public void removeUpdate(DocumentEvent e) {
		container.regenerate(e.getDocument());	
	}
}
