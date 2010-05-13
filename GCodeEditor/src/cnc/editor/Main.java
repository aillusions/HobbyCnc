package cnc.editor;

import cnc.editor.view.EditorViewFrame;

public class Main {	
	
	public static void main(String[] args) {		
		Editor editor = new Editor();
		EditorViewFrameListener viewListener = new EditorViewFrameListener(editor);		
		EditorViewFrame editorViewFrame = new EditorViewFrame(viewListener, editor.getDoc());
		GCodesDocListener docListener = new GCodesDocListener(editorViewFrame);			
		editor.getDoc().addDocumentListener(docListener);
	}
}
