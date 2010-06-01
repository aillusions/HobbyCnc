package cnc.editor;

import cnc.editor.listener.EditorMainFrameListener;
import cnc.editor.listener.EditorStatesListener;
import cnc.editor.listener.GCommandsContainerListener;
import cnc.editor.listener.VisualisationPanelListener;
import cnc.editor.view.EditorMainFrame;
import cnc.editor.view.VisualisationPanel;

public class Main {	
	
	public static void main(String[] args) {	
		
		Editor editor = new Editor();	
		
		VisualisationPanelListener vpl = new VisualisationPanelListener(editor);
		VisualisationPanel visualP = new VisualisationPanel(vpl);		
		
		GCommandsContainer gcc = GCommandsContainer.getInstance();		
		
		EditorMainFrameListener emfl = new EditorMainFrameListener(editor);
		EditorMainFrame emf = new EditorMainFrame(emfl, visualP);
		
		EditorStatesListener esl = new EditorStatesListener(visualP, emf);			
		EditorStates.getInstance().addActionListener(esl);
		
		GCommandsContainerListener gccl = new GCommandsContainerListener(visualP);
		gcc.addActionListener(gccl);
			
		emf.setVisible(true);
	}
}
