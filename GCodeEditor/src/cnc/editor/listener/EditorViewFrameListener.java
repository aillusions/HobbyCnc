package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.GCommandsContainer;

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
			GCommandsContainer.getInstance().clear();
		}else if(e.getActionCommand().equals("Scale")){
			float scale = Float.parseFloat(((JComboBox)e.getSource()).getSelectedItem().toString());
			EditorStates.getInstance().setScale(scale);
		}else if(e.getActionCommand().equals("switchToolsTo_SimpleDraw")){
			EditorStates.getInstance().setCurrentSelectedTool(Editor.EditorTolls.SIMPLE_EDIT);
		}else if(e.getActionCommand().equals("switchToolsTo_SelectVertexes")){
			EditorStates.getInstance().setCurrentSelectedTool(Editor.EditorTolls.VERTEX_SELECT);
		}else if(e.getActionCommand().equals("switchToolsTo_ContinuousDraw")){
			EditorStates.getInstance().setCurrentSelectedTool(Editor.EditorTolls.CONTINUOUS_EDIT);
		}		
	}
}
