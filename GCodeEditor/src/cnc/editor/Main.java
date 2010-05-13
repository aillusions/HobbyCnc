package cnc.editor;

import cnc.editor.view.EditorViewFrame;

public class Main {	
	public static void main(String[] args) {
		
		Editor editor = new Editor();
				
		EditorViewFrame editorViewFrame = new EditorViewFrame(editor, editor.getDoc());		
		editorViewFrame.setLocationRelativeTo(null);
		editorViewFrame.setVisible(true);
		
		MyDocListener docListener = new MyDocListener(editorViewFrame);		
		editor.getDoc().addDocumentListener(docListener);
	}
}
