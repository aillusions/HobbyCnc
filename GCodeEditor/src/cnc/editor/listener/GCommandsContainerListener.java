package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import cnc.editor.EditorStates;
import cnc.editor.EditorVertex;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.Editor.EditMode;
import cnc.editor.view.VisualisationPanel;

public class GCommandsContainerListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	private EditorStates es = EditorStates.getInstance();
	private GCommandsContainer gcc = GCommandsContainer.getInstance();
	
	public GCommandsContainerListener(VisualisationPanel visualisationPanel) {
		this.visualisationPanel = visualisationPanel;
	}
	
	public void actionPerformed(ActionEvent event) {
		
		visualisationPanel.repaint();
		
		//if(es.getCurrentEditMode() == EditMode.DRAW){
			
			Document doc = es.getDocument();
			
			if(es.getCurrentEditMode() == EditMode.DRAW && event.getActionCommand().equals(EditorVertex.CMD_COORDINATE_CHANGED)){
				
				EditorVertex vertex = (EditorVertex)event.getSource();
				
				GCommand gc = vertex.getgCommand();
				int editorLineIndex = EditorStates.getLineNumberInTextEditor(gc);
				
				try {
					int lineStart = es.getLineStartOffset(editorLineIndex);
					int lineEnd = es.getLineEndOffset(editorLineIndex);
					
					doc.remove(lineStart, lineEnd-lineStart);
					doc.insertString(lineStart, gc.toString() + "\r\n", null);
					
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
				}			
		
			}else if(event.getActionCommand().equals(GCommandsContainer.CMD_ADDED_BUNCH_OF_COMMANDS)){

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
				GCommand dc = list.get(list.size()-1);
									
				try {
					doc.insertString(doc.getLength(), dc.toString()+"\r\n", null);
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
				}	
			}
		//}
		
		if(event.getActionCommand().equals(GCommandsContainer.CMD_CLEAR_COMMANDS_CONATINER)){
			
			//if(EditorStates.getInstance().getCurrentEditMode() == EditMode.DRAW){
				
				//doc = EditorStates.getInstance().getDocument();				
				try {
					doc.remove(0, doc.getLength());
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
				}
			}
		//}
	}
}
