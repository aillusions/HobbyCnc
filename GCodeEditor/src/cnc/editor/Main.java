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
		Editor editor = new Editor();
		VertexesContainer container = VertexesContainer.getInstance();
		EditorViewFrameListener viewListener = new EditorViewFrameListener(editor);
		VisualPanelListener vpl = new VisualPanelListener(editor);
		VisualisationPanel view = new VisualisationPanel(vpl);
		

		EditorStatesListener esl = new EditorStatesListener(view);
		VertexesContainerListener containerListener = new VertexesContainerListener(view);		
		
		EditorStates.getInstance().addActionListener(esl);
		
		container.addActionListener(containerListener);
		
		GCodesDocListener docListener = new GCodesDocListener(container);			
		editor.getDoc().addDocumentListener(docListener);
		EditorViewFrame editorViewFrame = new EditorViewFrame(viewListener, editor.getDoc(), view);
		editorViewFrame.setVisible(true);
	}
}
