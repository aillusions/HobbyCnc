package cnc.editor;

import cnc.editor.view.EditorViewFrame;

public class Main {	
	
	public static void main(String[] args) {		
		Editor editor = new Editor();
		VertexesContainer container = new VertexesContainer();
		EditorViewFrameListener viewListener = new EditorViewFrameListener(editor);
		
		EditorViewFrame editorViewFrame = new EditorViewFrame(viewListener, editor.getDoc(), container);
		VertexesContainerListener containerListener = new VertexesContainerListener(editorViewFrame);
		
		container.addActionListener(containerListener);
		
		GCodesDocListener2 docListener = new GCodesDocListener2(container);			
		editor.getDoc().addDocumentListener(docListener);
	}
}
