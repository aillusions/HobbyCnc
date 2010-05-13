package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cnc.editor.view.EditorViewFrame;

public class VertexesContainerListener implements ActionListener {

	private EditorViewFrame editorViewFrame;
	
	public VertexesContainerListener(EditorViewFrame editorViewFrame) {
		super();
		this.editorViewFrame = editorViewFrame;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		editorViewFrame.repaint();
	}



}
