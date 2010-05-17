package cnc.editor;

import cnc.editor.listener.EditorStatesListener;
import cnc.editor.listener.EditorViewFrameListener;
import cnc.editor.listener.GCodesDocListener;
import cnc.editor.listener.GCodesTextContainerListener;
import cnc.editor.listener.GCommandsContainerListener;
import cnc.editor.listener.VisualisationPanelListener;
import cnc.editor.view.EditorViewFrame;
import cnc.editor.view.GCodesTextContainer;
import cnc.editor.view.VisualisationPanel;

public class Main {	
	
	public static void main(String[] args) {	
		
		Editor editor = new Editor();	
		
		VisualisationPanelListener vpl = new VisualisationPanelListener(editor);
		VisualisationPanel vp = new VisualisationPanel(vpl);		
		
		GCommandsContainer gcc = GCommandsContainer.getInstance();
		
		GCodesDocListener gcdl = new GCodesDocListener(gcc);			
		EditorStates.getInstance().getDocument().addDocumentListener(gcdl);
		
		GCodesTextContainerListener gctcl = new GCodesTextContainerListener();
		GCodesTextContainer gctc = new GCodesTextContainer(EditorStates.getInstance().getDocument(), gctcl);
		EditorStates.getInstance().setgCodesTextContainer(gctc);
		
		EditorStatesListener esl = new EditorStatesListener(vp, gctc);			
		EditorStates.getInstance().addActionListener(esl);
		
		GCommandsContainerListener vcl = new GCommandsContainerListener(vp, gctc);
		gcc.addActionListener(vcl);
				
		EditorViewFrameListener vl = new EditorViewFrameListener(editor);
		EditorViewFrame evf = new EditorViewFrame(vl, gctc, vp);
		evf.setVisible(true);
	}
}
