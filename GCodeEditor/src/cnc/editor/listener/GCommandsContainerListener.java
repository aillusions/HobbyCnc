package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.Editor.EditModeS;
import cnc.editor.view.GCodesTextContainer;
import cnc.editor.view.VisualisationPanel;

public class GCommandsContainerListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	private GCodesTextContainer gCodesTextContainer;
	
	private EditorStates es = EditorStates.getInstance();
	private GCommandsContainer gcc = GCommandsContainer.getInstance();
	
	public GCommandsContainerListener(VisualisationPanel visualisationPanel, GCodesTextContainer gCodesTextContainer) {
		this.visualisationPanel = visualisationPanel;
		this.gCodesTextContainer = gCodesTextContainer;
	}
	
	public void actionPerformed(ActionEvent event) {
		
		visualisationPanel.repaint();

		Document doc = es.getDocument();

		if(es.getCurrentEditMode() == EditModeS.DRAW 
				&& event.getActionCommand().equals(GCommand.CMD_COORDINATE_CHANGED)){			
			
			try {
				
				GCommand gc = (GCommand)event.getSource();					
				int editorLineIndex = EditorStates.getLineNumberInTextEditor(gc);					
				int lineStart = es.getLineStartOffset(editorLineIndex);
				int lineEnd = es.getLineEndOffset(editorLineIndex);
				
				doc.remove(lineStart, lineEnd-lineStart);
				doc.insertString(lineStart, gc.toString() + "\r\n", null);
				
				Set<GCommand> selected = es.getSelectedCommand();
				
				if(selected != null && selected.size() == 1){
					
					lineEnd = es.getLineEndOffset(editorLineIndex);
					
					SwingUtilities.invokeLater(new Runnable() { 
						public void run() {
							gCodesTextContainer.requestFocus(); 
						}}); 
			
					gCodesTextContainer.select(lineStart, lineEnd);
				}
				
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}		
		
		}else if(event.getActionCommand().equals(GCommandsContainer.CMD_ADDED_BUNCH_OF_COMMANDS)){
			
			try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
			
			final StringBuffer codesBuffer = new StringBuffer();
			
			for(GCommand dc : gcc.getGCommandList()) {
				codesBuffer.append(dc.toString()+"\r\n");
			}

			try {
				doc.insertString(0, codesBuffer.toString(), null);
				
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
			
		}else if(event.getActionCommand().equals(GCommandsContainer.CMD_ADDED_ONE_COMMAND)){
			
			List<GCommand> list = gcc.getGCommandList();			
			GCommand gc = list.get(list.size()-1);
								
			try {				
				doc.insertString(doc.getLength(), gc.toString()+"\r\n", null);
				
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}	
		}
		
		if(event.getActionCommand().equals(GCommandsContainer.CMD_CLEAR_COMMANDS_CONATINER)){
			
			es.clearSelection();
			
			try {
				doc.remove(0, doc.getLength());
			
				final StringBuffer codesBuffer = new StringBuffer();
				
				for(GCommand dc : gcc.getGCommandList()) {
					codesBuffer.append(dc.toString()+"\r\n");
				}

				doc.insertString(0, codesBuffer.toString(), null);
				
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
			
		}

	}
}
