package cnc.editor;

import cnc.editor.listener.EditorStatesListener;
import cnc.editor.listener.EditorViewFrameListener;
import cnc.editor.listener.GCodesDocListener;
import cnc.editor.listener.VertexesContainerListener;
import cnc.editor.listener.VisualPanelListener;
import cnc.editor.view.EditorStates;
import cnc.editor.view.EditorViewFrame;
import cnc.editor.view.VisualisationPanel;

public class Main {	
	
	public static void main(String[] args) {	
		
		Editor e = new Editor();
				
		EditorViewFrameListener vl = new EditorViewFrameListener(e);
		VisualPanelListener vpl = new VisualPanelListener(e);
		VisualisationPanel vp = new VisualisationPanel(vpl);		

		EditorStatesListener esl = new EditorStatesListener(vp);				
		
		EditorStates.getInstance().addActionListener(esl);
		
		VertexesContainer vc = VertexesContainer.getInstance();
		VertexesContainerListener vcl = new VertexesContainerListener(vp);
		vc.addActionListener(vcl);
		
		GCodesDocListener gcdl = new GCodesDocListener(vc);			
		e.getDoc().addDocumentListener(gcdl);
		
		EditorViewFrame evf = new EditorViewFrame(vl, e.getDoc(), vp);
		evf.setVisible(true);
	}
}
