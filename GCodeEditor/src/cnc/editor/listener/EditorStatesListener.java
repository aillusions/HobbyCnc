package cnc.editor.listener;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cnc.editor.EditorStates;
import cnc.editor.view.VisualisationPanel;

public class EditorStatesListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	
	public EditorStatesListener(VisualisationPanel visualisationPanel) {
		this.visualisationPanel = visualisationPanel;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals(EditorStates.CMD_CLEAR_VIEW)){
			visualisationPanel.setSize(new Dimension(0,0));
		}
		
		visualisationPanel.repaint();
	}

}
