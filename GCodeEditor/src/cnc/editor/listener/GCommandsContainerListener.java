package cnc.editor.listener;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import cnc.editor.EditorStates;
import cnc.editor.EditorVertex;
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
				&& event.getActionCommand().equals(EditorVertex.CMD_COORDINATE_CHANGED)){
			
			EditorVertex vertex = (EditorVertex)event.getSource();
			
			GCommand gc = vertex.getgCommand();
			int editorLineIndex = EditorStates.getLineNumberInTextEditor(gc);
			
			try {
				int lineStart = es.getLineStartOffset(editorLineIndex);
				int lineEnd = es.getLineEndOffset(editorLineIndex);
				
				doc.remove(lineStart, lineEnd-lineStart);
				doc.insertString(lineStart, gc.toString() + "\r\n", null);
				
				lineEnd = es.getLineEndOffset(editorLineIndex);
				
				SwingUtilities.invokeLater(new Runnable() { 
					public void run() {
						gCodesTextContainer.requestFocus(); 
					}}); 
		
				gCodesTextContainer.select(lineStart, lineEnd);
				gCodesTextContainer.scrollRectToVisible(new Rectangle(1,1,1,1));
				
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
			GCommand gc = list.get(list.size()-1);
								
			try {				
				doc.insertString(doc.getLength(), gc.toString()+"\r\n", null);
				
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}	
		}
		
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
