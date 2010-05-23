package cnc.editor.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import cnc.editor.EditorStates;
import cnc.editor.GCodeParser;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.doc.MyDocumentEvent;
import cnc.editor.doc.MyPlainDocument.EditSource;
import cnc.editor.view.GCodesTextContainer;

public class GCodesDocListener implements DocumentListener {

	private GCommandsContainer gcc = GCommandsContainer.getInstance();
	private EditorStates es = EditorStates.getInstance();
	private GCodesTextContainer gtc;
	
	public GCodesDocListener(GCodesTextContainer gtc){
		this.gtc = gtc;
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
		
		if(e instanceof MyDocumentEvent){
			MyDocumentEvent myE = (MyDocumentEvent)e;
			if(myE.getEditSource().equals(EditSource.GUI)){
				//System.out.println("nothing to do");
			}else{
				try {
					//es.clearSelection();
					
					int lineIndex = es.getLineOfOffset(e.getOffset());
					int lineStart = es.getLineStartOffset(lineIndex);
					int lineEnd = es.getLineEndOffset(lineIndex);
					
					if(e.getLength() <= lineEnd-lineStart){
						String newCommandValue = e.getDocument().getText(lineStart, lineEnd - lineStart);
						GCommand oldCmd = gcc.getGCommandList().get(lineIndex);	
						GCommand newCommand = GCodeParser.parseCommand(newCommandValue);
						
						if(newCommand != null){							
							gcc.replaceCommandManually(oldCmd, newCommand, this);
						}else{
							System.err.println("Wrong format of new Command");
						}
					}else{
						String str = e.getDocument().getText(0, e.getDocument().getLength());
						gcc.clear(this);
						gcc.addCommandsBunch(str, this);
						gtc.repaint();
					}

				} catch (BadLocationException e1) {
					throw new RuntimeException(e1);
				}	
			}
		}else{
			throw new RuntimeException("strange");
		}
	}
}
