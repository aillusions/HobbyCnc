package cnc.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.GCommandsContainer;
import cnc.editor.view.EditorMainFrame;

public class EditorMainFrameListener implements ActionListener {

	private Editor editor;	
	private EditorStates es = EditorStates.getInstance();
	
	public EditorMainFrameListener(Editor editor) {
		this.editor = editor;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("ConvertImageToGCodes")){
			editor.convertImageToGCodes();
		}else if(e.getActionCommand().equals("AddGCodesFromFile")){
			editor.addGCodesFromFile();
		}else if(e.getActionCommand().equals("Clear")){
			GCommandsContainer.getInstance().clear(this);
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
			editor.downWorkHead();
		}else if(e.getActionCommand().equals("SetFutureCommandsType_G02")){
			es.setCurrentGCmdType(Editor.GcommandTypes.G02);
		}else if(e.getActionCommand().equals("SetFutureCommandsType_G00")){
			es.setCurrentGCmdType(Editor.GcommandTypes.G00);
		}else if(e.getActionCommand().equals("SetFutureCommandsType_G01")){
			es.setCurrentGCmdType(Editor.GcommandTypes.G01);
		}else if(e.getActionCommand().equals("SetFutureCommandsType_G03")){
			es.setCurrentGCmdType(Editor.GcommandTypes.G03);
		}else if(e.getActionCommand().equals(EditorMainFrame.CMD_SHOW_ONLY_Z0_SWITCHED)){
			es.setDisplayOnlyZ0(((JCheckBox)e.getSource()).isSelected());
		}else if(e.getActionCommand().equals(EditorMainFrame.CMD_LIFT_FOR_EACH_STROKE)){
			es.setLiftForEachStroke(((JCheckBox)e.getSource()).isSelected());
			
		}else if(e.getActionCommand().equals(EditorMainFrame.CMD_UNDO)){
			
			editor.undo();
			
		}else if(e.getActionCommand().equals(EditorMainFrame.CMD_REDO)){
			
			editor.redo();
			
		}
	}

	public static class SetRaduisKeyListener extends KeyAdapter{ 

		public void keyReleased(KeyEvent e) {

			JTextField field = (JTextField)e.getSource();
			String txt = field.getText().trim();

			if(!txt.equals("")){
				EditorStates.getInstance().setArcR(Float.parseFloat(txt), false);
			}else{
				EditorStates.getInstance().setArcR(null, false);
			}
		} 
	}
	
	public static class SetIKeyListener extends KeyAdapter{ 
		
		public void keyReleased(KeyEvent e) {
		
			JTextField field = (JTextField)e.getSource();
			String txt = field.getText().trim();
			
			if(!txt.equals("")){
				EditorStates.getInstance().setArcI(Float.parseFloat(txt), false);
			}else{
				EditorStates.getInstance().setArcI(null, false);
			}
		} 
	}
	
	public static class SetJKeyListener extends KeyAdapter{ 
		
		public void keyReleased(KeyEvent e) {

			JTextField field = (JTextField)e.getSource();
			String txt = field.getText().trim();
			
			if(!txt.equals("")){
				EditorStates.getInstance().setArcJ(Float.parseFloat(txt), false);
			}else{
				EditorStates.getInstance().setArcJ(null, false);
			}
		} 
	}

}
