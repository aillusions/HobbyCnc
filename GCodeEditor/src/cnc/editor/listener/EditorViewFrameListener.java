package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import cnc.editor.Editor;
import cnc.editor.view.EditorStates;

public class EditorViewFrameListener implements ActionListener {

	private Editor editor;
		
	public EditorViewFrameListener(Editor editor) {
		super();
		this.editor = editor;
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("ConvertImageToGCodes")){
			editor.convertImageToGCodes();
		}else if(e.getActionCommand().equals("AddGCodesFromFile")){
			editor.addGCodesFromFile();
		}else if(e.getActionCommand().equals("Clear")){
			editor.clearDocument();
		}else if(e.getActionCommand().equals("Scale")){
			float scale = Float.parseFloat(((JComboBox)e.getSource()).getSelectedItem().toString());
			EditorStates.getInstance().setScale(scale);
		}else if(e.getActionCommand().equals("switchToolsTo_SimpleDraw")){
			EditorStates.getInstance().setCurrentSelectedTool(EditorStates.EditorTolls.SIMPLE_EDIT);
		}else if(e.getActionCommand().equals("switchToolsTo_SelectVertexes")){
			EditorStates.getInstance().setCurrentSelectedTool(EditorStates.EditorTolls.VERTEX_SELECT);
		}		
	}
}
