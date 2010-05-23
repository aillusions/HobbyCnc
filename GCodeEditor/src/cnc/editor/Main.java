package cnc.editor;

import cnc.editor.listener.EditorStatesListener;
import cnc.editor.listener.EditorMainFrameListener;
import cnc.editor.listener.GCodesDocListener;
import cnc.editor.listener.GCodesTextContainerListener;
import cnc.editor.listener.GCommandsContainerListener;
import cnc.editor.listener.VisualisationPanelListener;
import cnc.editor.view.EditorMainFrame;
import cnc.editor.view.GCodesTextContainer;
import cnc.editor.view.VisualisationPanel;

public class Main {	
	
	public static void main(String[] args) {	
		
		Editor editor = new Editor();	
		
		VisualisationPanelListener vpl = new VisualisationPanelListener(editor);
		VisualisationPanel visualP = new VisualisationPanel(vpl);		
		
		GCommandsContainer gcc = GCommandsContainer.getInstance();
		
		GCodesTextContainerListener gctcl = new GCodesTextContainerListener();
		GCodesTextContainer gctc = new GCodesTextContainer(EditorStates.getInstance().getDocument(), gctcl);
		EditorStates.getInstance().setgCodesTextContainer(gctc);
		
		GCodesDocListener gcdl = new GCodesDocListener(gctc);			
		EditorStates.getInstance().getDocument().addDocumentListener(gcdl);
		
		EditorMainFrameListener emfl = new EditorMainFrameListener(editor);
		EditorMainFrame emf = new EditorMainFrame(emfl, gctc, visualP);
		
		EditorStatesListener esl = new EditorStatesListener(visualP, gctc, emf);			
		EditorStates.getInstance().addActionListener(esl);
		
		GCommandsContainerListener gccl = new GCommandsContainerListener(visualP, gctc);
		gcc.addActionListener(gccl);
				
		emf.setVisible(true);
	}
}
