package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

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
		}else if(e.getActionCommand().equals("Scale")){
			editor.scale(Float.parseFloat(((JComboBox)e.getSource()).getSelectedItem().toString()));
		}else if(e.getActionCommand().equals("Clear")){
			editor.clear();
		}
	}
}
