package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cnc.editor.view.VisualisationPanel;

public class EditorStatesListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	
	public EditorStatesListener(VisualisationPanel visualisationPanel) {
		this.visualisationPanel = visualisationPanel;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		visualisationPanel.repaint();
	}

}
