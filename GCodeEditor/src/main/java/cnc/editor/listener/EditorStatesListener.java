package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cnc.editor.EditorStates;
import cnc.editor.view.EditorMainFrame;
import cnc.editor.view.VisualisationPanel;

public class EditorStatesListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	private EditorMainFrame editorMainFrame;
	private EditorStates es = EditorStates.getInstance();
	
	public EditorStatesListener(VisualisationPanel visualisationPanel, EditorMainFrame editorMainFrame) {
		this.visualisationPanel = visualisationPanel;
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
						
		}else if(arg0.getActionCommand().equals(EditorStates.CMD_SET_SCALE)){
			visualisationPanel.scaleImage();						
		}

		visualisationPanel.repaint();
	}

}
