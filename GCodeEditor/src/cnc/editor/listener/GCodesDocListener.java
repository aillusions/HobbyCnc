package cnc.editor.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cnc.editor.EditorStates;
import cnc.editor.GCommandsContainer;
import cnc.editor.Editor.EditMode;

public class GCodesDocListener implements DocumentListener {

	private GCommandsContainer container;
		
	public GCodesDocListener(GCommandsContainer container) {
		this.container = container;
	}

	public void changedUpdate(DocumentEvent e) {	
		//nothing  to do
	}

	public void insertUpdate(DocumentEvent e) {
		docContentChanged(e);		
	}

	public void removeUpdate(DocumentEvent e) {
		docContentChanged(e);
	}
	
	private void docContentChanged(DocumentEvent e){
		//if(EditorStates.getInstance().getCurrentEditMode() == EditMode.TXT){
			container.regenerate(e.getDocument());	
		//}
		
	}
}
