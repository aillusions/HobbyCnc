package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.Editor.EditMode;
import cnc.editor.view.VisualisationPanel;

public class GCommandsContainerListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	
	public GCommandsContainerListener(VisualisationPanel visualisationPanel) {
		this.visualisationPanel = visualisationPanel;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		visualisationPanel.repaint();
		
		if(arg0.getActionCommand().equals("clear")){
			Document doc = EditorStates.getInstance().getDocument();
			try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
		}
		
		if(EditorStates.getInstance().getCurrentEditMode() == EditMode.DRAW){
			Document doc = EditorStates.getInstance().getDocument();
			try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
			
			final StringBuffer codesBuffer = new StringBuffer();
			
			for(GCommand dc : GCommandsContainer.getInstance().getGCommandList()) {
				codesBuffer.append("\r\n" + dc.toString());
			}					
			try {
				doc.insertString(0, codesBuffer.toString(), null);
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}	
		}

	}
}
