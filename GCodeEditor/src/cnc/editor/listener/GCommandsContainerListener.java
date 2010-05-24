package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cnc.editor.view.VisualisationPanel;

public class GCommandsContainerListener implements ActionListener {

	private VisualisationPanel visualisationPanel;
	
	public GCommandsContainerListener(VisualisationPanel visualisationPanel) {
		
		this.visualisationPanel = visualisationPanel;
		
	}
	
	public void actionPerformed(ActionEvent event) {
		
		visualisationPanel.repaint();

	}
}
