package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.GCommandsContainer;

public class EditorMainFrameListener implements ActionListener {

	private Editor editor;
	private EditorStates es = EditorStates.getInstance();
	public EditorMainFrameListener(Editor editor) {
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
			es.setScale(scale);
		}else if(e.getActionCommand().equals("switchToolsTo_SimpleDraw")){
			es.setCurrentSelectedTool(Editor.EditorTolls.SIMPLE_EDIT);
		}else if(e.getActionCommand().equals("switchToolsTo_SelectVertexes")){
			es.setCurrentSelectedTool(Editor.EditorTolls.VERTEX_SELECT);
		}else if(e.getActionCommand().equals("switchToolsTo_ContinuousDraw")){
			es.setCurrentSelectedTool(Editor.EditorTolls.CONTINUOUS_EDIT);
		}else if(e.getActionCommand().equals("LiftWorkHead")){
			editor.liftWorkHead();
		}else if(e.getActionCommand().equals("DescendWorkHead")){
			editor.descendWorkHead();
		}	
	}
}
