package cnc.editor.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import cnc.editor.EditorStates;
import cnc.editor.GCodeParser;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.Editor.EditModeS;

public class GCodesDocListener implements DocumentListener {

	private GCommandsContainer container;
	private EditorStates es = EditorStates.getInstance();
	
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
		
		if(true){
			return;
		}
		
		if(es.getCurrentEditMode() == EditModeS.TXT){
			
			try {
				int lineIndex = es.getLineOfOffset(e.getOffset());
				int lineStart = es.getLineStartOffset(lineIndex);
				int lineEnd = es.getLineEndOffset(lineIndex);
				
				String newCommandValue = e.getDocument().getText(lineStart, lineEnd - lineStart);
				
				int prevCommandIndex = lineIndex-1;
				
				GCommand oldCommand = container.getGCommandList().get(lineIndex);
				GCommand newCommand = GCodeParser.parseCommand(newCommandValue);
				
				oldCommand.setX(newCommand.getX());
				oldCommand.setY(newCommand.getY());
				oldCommand.setZ(newCommand.getZ());

			} catch (BadLocationException e1) {
				throw new RuntimeException(e1);
			}	
		}
		
	}
}
