package cnc.editor.listener;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.view.EditorMainFrame;
import cnc.editor.view.GCodesTextContainer;
import cnc.editor.view.VisualisationPanel;

public class EditorStatesListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	private GCodesTextContainer gCodesTextContainer;
	private EditorMainFrame editorMainFrame;
	private EditorStates es = EditorStates.getInstance();
	
	public EditorStatesListener(VisualisationPanel visualisationPanel, GCodesTextContainer gCodesTextContainer, EditorMainFrame editorMainFrame) {
		this.visualisationPanel = visualisationPanel;
		this.gCodesTextContainer = gCodesTextContainer;
		this.editorMainFrame = editorMainFrame;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals(EditorStates.CMD_REFRESH_ARC_RADUIS)){
			
			if(es.getArcR() != null){
				editorMainFrame.setRadiusValue(es.getArcR().toString());
			}else{
				editorMainFrame.setRadiusValue("");
			}
						
		}else if(arg0.getActionCommand().equals(EditorStates.CMD_REFRESH_ARC_I)){
		
			if(es.getArcI() != null){
				editorMainFrame.setIValue(es.getArcI().toString());
			}else{
				editorMainFrame.setIValue("");
			}
			
		}else if(arg0.getActionCommand().equals(EditorStates.CMD_REFRESH_ARC_J)){
		
			if(es.getArcJ() != null){
				editorMainFrame.setJValue(es.getArcJ().toString());
			}else{
				editorMainFrame.setJValue("");
			}
						
		}
		
		
		EditorStates es = EditorStates.getInstance();
		Set<GCommand> gCommands = es.getSelectedGCommands();

		if(gCommands != null && gCommands.size() > 0){
			
			try{		
				
				int minLineIndex = 0;
				int maxLineIndex = 0;
				
				int i = 0;
				
				for(GCommand gc : gCommands){
					
					int currentLineIndex =  EditorStates.getLineNumberInTextEditor(gc);
					if(i == 0){
						minLineIndex = currentLineIndex;
						maxLineIndex = currentLineIndex;
					}else{
						if(minLineIndex > currentLineIndex){
							minLineIndex = currentLineIndex;
						}
						if(maxLineIndex < currentLineIndex){
							maxLineIndex = currentLineIndex;
						}
					}
					i ++;
				}

				int lineStart = gCodesTextContainer.getLineStartOffset(minLineIndex);
				int lineEnd = gCodesTextContainer.getLineEndOffset(maxLineIndex);
				
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
