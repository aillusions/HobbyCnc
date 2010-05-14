package cnc.editor;

import cnc.editor.listener.EditorListener;
import cnc.editor.listener.EditorStatesListener;
import cnc.editor.listener.EditorViewFrameListener;
import cnc.editor.listener.GCodesDocListener;
import cnc.editor.listener.VertexesContainerListener;
import cnc.editor.listener.VisualPanelListener;
import cnc.editor.view.EditorViewFrame;
import cnc.editor.view.GCodesTextContainer;
import cnc.editor.view.VisualisationPanel;

public class Main {	
	
	public static void main(String[] args) {	
		
		Editor editor = new Editor();		
				
		EditorViewFrameListener vl = new EditorViewFrameListener(editor);
		VisualPanelListener vpl = new VisualPanelListener(editor);
		VisualisationPanel vp = new VisualisationPanel(vpl);		

		EditorStatesListener esl = new EditorStatesListener(vp);				
		
		EditorStates.getInstance().addActionListener(esl);
		
		GCommandsContainer vc = GCommandsContainer.getInstance();
		VertexesContainerListener vcl = new VertexesContainerListener(vp);
		vc.addActionListener(vcl);
		
		GCodesDocListener gcdl = new GCodesDocListener(vc);			
		editor.getDoc().addDocumentListener(gcdl);
		
		GCodesTextContainer gctc = new GCodesTextContainer(editor.getDoc());
		EditorListener el = new EditorListener(gctc);
		editor.addActionListener(el);
		
		EditorViewFrame evf = new EditorViewFrame(vl, gctc, vp);
		evf.setVisible(true);
	}
}
