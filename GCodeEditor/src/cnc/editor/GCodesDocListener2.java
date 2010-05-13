package cnc.editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GCodesDocListener2 implements DocumentListener {

	VertexesContainer container;
		
	public GCodesDocListener2(VertexesContainer container) {
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
