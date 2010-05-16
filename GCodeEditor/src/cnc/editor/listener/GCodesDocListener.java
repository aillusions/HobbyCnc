package cnc.editor.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import cnc.editor.EditorStates;
import cnc.editor.GCodeParser;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.Editor.EditMode;

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
		
		if(es.getCurrentEditMode() == EditMode.TXT){
			
			try {
				int lineIndex = es.getLineOfOffset(e.getOffset());
				int lineStart = es.getLineStartOffset(lineIndex);
				int lineEnd = es.getLineEndOffset(lineIndex);
				
				String newCommandValue = e.getDocument().getText(lineStart, lineEnd - lineStart);
				
				int prevCommandIndex = lineIndex-1;
				
				GCommand oldCommand = container.getGCommandList().get(lineIndex);
				GCommand prevCommand = container.getGCommandList().get(prevCommandIndex);				
				GCommand newCommand = GCodeParser.parseCommand(newCommandValue, prevCommand.getVertex());
				
				oldCommand.getVertex().setX(newCommand.getVertex().getX());
				oldCommand.getVertex().setY(newCommand.getVertex().getY());
				oldCommand.getVertex().setZ(newCommand.getVertex().getZ());

			} catch (BadLocationException e1) {
				throw new RuntimeException(e1);
			}	
		}
		
	}
}
