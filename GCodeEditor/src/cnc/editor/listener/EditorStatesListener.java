package cnc.editor.listener;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.view.GCodesTextContainer;
import cnc.editor.view.VisualisationPanel;

public class EditorStatesListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	private GCodesTextContainer gCodesTextContainer;
	
	public EditorStatesListener(VisualisationPanel visualisationPanel, GCodesTextContainer gCodesTextContainer) {
		this.visualisationPanel = visualisationPanel;
		this.gCodesTextContainer = gCodesTextContainer;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals(EditorStates.CMD_CLEAR_SELECTION)){

		}

		EditorStates es = EditorStates.getInstance();
		GCommand gCommand1 = es.getSelectedVertex();

		if(gCommand1 != null){
			
			try{							
				int editorLineIndex = EditorStates.getLineNumberInTextEditor(gCommand1);
				int lineStart = gCodesTextContainer.getLineStartOffset(editorLineIndex);
				int lineEnd = gCodesTextContainer.getLineEndOffset(editorLineIndex);
				
				SwingUtilities.invokeLater(new Runnable() { 
					public void run() {
						gCodesTextContainer.requestFocus(); 
					}}); 
		
				gCodesTextContainer.select(lineStart, lineEnd);
				gCodesTextContainer.scrollRectToVisible(new Rectangle(1,1,1,1));
				
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
		}else{
			gCodesTextContainer.select(0, 0);
		}
		visualisationPanel.repaint();
	}

}
