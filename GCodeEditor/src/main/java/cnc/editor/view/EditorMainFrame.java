package cnc.editor.view;

import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import cnc.editor.listener.EditorMainFrameListener;
import cnc.editor.listener.EditorMainFrameListener.SetIKeyListener;
import cnc.editor.listener.EditorMainFrameListener.SetJKeyListener;
import cnc.editor.listener.EditorMainFrameListener.SetRaduisKeyListener;

public class EditorMainFrame extends javax.swing.JFrame {

	public static final String CMD_REDO = "redo";
	public static final String CMD_UNDO = "undo";
	public static final String CMD_LIFT_FOR_EACH_STROKE = "liftForEachStroke";
	public static final String CMD_SHOW_ONLY_Z0_SWITCHED = "showOnlyZ0Switched";
	private static final long serialVersionUID = 13423452354765L;
	private static final String[] scaleValues =  
						new String[]{"1", "1.5", "2", "3", "5", "10", "15", "20", "50", "0.1", "0.5"};	
	
	private VisualisationPanel pnl_GraphicOutput;
	
	private JButton btn_AddGCodesFromFile;
	private JButton btn_ConvertImageToGCodes;
	private JButton btn_Clear;
	private JButton btn_Save;
	
	private JButton btn_Undo;
	private JButton btn_Redo;
	
	private ButtonGroup btnGr_headPosition;
	private JRadioButton rbtn_Lift;
	private JRadioButton rbtn_Down;
	
	private ButtonGroup btnGr_toolBar;
	private JRadioButton rbtn_Tool_SimpleDraw;
	private JRadioButton rbtn_Tool_ContinuousDraw;
	private JRadioButton rbtn_Tool_SelectVertex;
	
	private JComboBox comBox_Scale;
	
	private JPanel panel_Tools;
	
	//private JScrollPane scrollPane_GCodesEditor;
	private JScrollPane scrollPane_GraphicOutput;
	
	private ButtonGroup btnGr_commandTypes;
	private JRadioButton rbtn_commandType_G00;
	private JRadioButton rbtn_commandType_G01;
	private JRadioButton rbtn_commandType_G02;
	private JRadioButton rbtn_commandType_G03;
	
	private JTextField txtFld_ArcR;
	private JTextField txtFld_ArcI;
	private JTextField txtFld_ArcJ;
	
	private JCheckBox chkBx_liftForEachStroke;
	private JCheckBox chkBx_showOnlyZ0;
	
	private JLabel lab_R;
	private JLabel lab_I;
	private JLabel lab_J;
	
	public VisualisationPanel getVisualPanelViewOutput() {
		return pnl_GraphicOutput;
	}
	
	public void setVisualPanelViewOutput(VisualisationPanel visualisationPanel) {
		pnl_GraphicOutput = visualisationPanel;
	}

	//Constructor
	public EditorMainFrame(EditorMainFrameListener editorViewListener/*, GCodesTextContainer textContainer*/, VisualisationPanel visualisationPanel) {

		pnl_GraphicOutput = visualisationPanel;	
		
		scrollPane_GraphicOutput = new JScrollPane();			
		scrollPane_GraphicOutput.setBounds(5, 5, 1070, 730);	
		scrollPane_GraphicOutput.setViewportView(pnl_GraphicOutput);
		
		panel_Tools = new JPanel();
		panel_Tools.setBounds(1075, 5, 200, 730);	
		panel_Tools.setLayout(null);
		
		btn_Clear = new JButton();
		btn_Clear.setText("clear");
		btn_Clear.setBounds(5, 5, 85, 21);
		btn_Clear.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Clear.setActionCommand("Clear");
		btn_Clear.addActionListener(editorViewListener);
		
		btn_Save = new JButton();
		btn_Save.setText("save");
		btn_Save.setBounds(5, 29, 85, 21);
		btn_Save.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Save.setActionCommand("Save");
		btn_Save.addActionListener(editorViewListener);
		
		btn_ConvertImageToGCodes = new JButton();
		btn_ConvertImageToGCodes.setText("image");
		btn_ConvertImageToGCodes.setBounds(5, 53, 85, 21);
		btn_ConvertImageToGCodes.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_ConvertImageToGCodes.setActionCommand("ConvertImageToGCodes");
		btn_ConvertImageToGCodes.addActionListener(editorViewListener);
				
		btn_AddGCodesFromFile = new JButton();			
		btn_AddGCodesFromFile.setText("open");
		btn_AddGCodesFromFile.setBounds(5, 77, 85, 21);
		btn_AddGCodesFromFile.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_AddGCodesFromFile.setActionCommand("AddGCodesFromFile");
		btn_AddGCodesFromFile.addActionListener(editorViewListener);
		
		//Tool bar controls
		rbtn_Tool_SimpleDraw = new JRadioButton();		
		rbtn_Tool_SimpleDraw.setText("line");
		rbtn_Tool_SimpleDraw.setBounds(5, 100, 84, 21);
		rbtn_Tool_SimpleDraw.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Tool_SimpleDraw.addActionListener(editorViewListener);
		rbtn_Tool_SimpleDraw.setActionCommand("switchToolsTo_SimpleDraw");
		
		rbtn_Tool_ContinuousDraw = new JRadioButton();
		rbtn_Tool_ContinuousDraw.setText("pen");
		rbtn_Tool_ContinuousDraw.setBounds(5, 120, 84, 21);
		rbtn_Tool_ContinuousDraw.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Tool_ContinuousDraw.addActionListener(editorViewListener);
		rbtn_Tool_ContinuousDraw.setActionCommand("switchToolsTo_ContinuousDraw");
		
		rbtn_Tool_SelectVertex = new JRadioButton();
		rbtn_Tool_SelectVertex.setText("select");
		rbtn_Tool_SelectVertex.setBounds(5, 140, 84, 21);
		rbtn_Tool_SelectVertex.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Tool_SelectVertex.addActionListener(editorViewListener);
		rbtn_Tool_SelectVertex.setActionCommand("switchToolsTo_SelectVertexes");
	
		btnGr_toolBar = new ButtonGroup();
		btnGr_toolBar.add(rbtn_Tool_SimpleDraw);
		btnGr_toolBar.add(rbtn_Tool_SelectVertex);
		btnGr_toolBar.add(rbtn_Tool_ContinuousDraw);
		rbtn_Tool_SimpleDraw.setSelected(true);
		
		ComboBoxModel comBoxModel_Scale = new DefaultComboBoxModel(scaleValues);						
		comBox_Scale = new JComboBox();			
		comBox_Scale.setModel(comBoxModel_Scale);
		comBox_Scale.setBounds(5, 190, 84, 21);
		comBox_Scale.setActionCommand("Scale");
		comBox_Scale.addActionListener(editorViewListener);
		
		rbtn_Lift = new JRadioButton();
		rbtn_Lift.setText("UP");
		rbtn_Lift.setBounds(5, 220, 84, 21);
		rbtn_Lift.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Lift.setActionCommand("LiftWorkHead");
		rbtn_Lift.addActionListener(editorViewListener);
		rbtn_Lift.setSelected(true);
		
		rbtn_Down = new JRadioButton();
		rbtn_Down.setText("DOWN");
		rbtn_Down.setBounds(5, 240, 84, 21);
		rbtn_Down.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Down.setActionCommand("DescendWorkHead");
		rbtn_Down.addActionListener(editorViewListener);

		
		btnGr_headPosition = new ButtonGroup();
		btnGr_headPosition.add(rbtn_Lift);
		btnGr_headPosition.add(rbtn_Down);
				
		rbtn_commandType_G00 = new JRadioButton();
		rbtn_commandType_G00.setText("G00");
		rbtn_commandType_G00.setBounds(5, 270, 45, 21);
		rbtn_commandType_G00.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_commandType_G00.setActionCommand("SetFutureCommandsType_G00");
		rbtn_commandType_G00.addActionListener(editorViewListener);
		rbtn_commandType_G00.setSelected(true);
		
		rbtn_commandType_G01 = new JRadioButton();
		rbtn_commandType_G01.setText("G01");
		rbtn_commandType_G01.setBounds(50, 270, 45, 21);
		rbtn_commandType_G01.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_commandType_G01.setActionCommand("SetFutureCommandsType_G01");
		rbtn_commandType_G01.addActionListener(editorViewListener);
		
		rbtn_commandType_G02 = new JRadioButton();
		rbtn_commandType_G02.setText("G02");
		rbtn_commandType_G02.setBounds(5, 290, 45, 21);
		rbtn_commandType_G02.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_commandType_G02.setActionCommand("SetFutureCommandsType_G02");
		rbtn_commandType_G02.addActionListener(editorViewListener);
		
		rbtn_commandType_G03 = new JRadioButton();
		rbtn_commandType_G03.setText("G03");
		rbtn_commandType_G03.setBounds(50, 290, 45, 21);
		rbtn_commandType_G03.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_commandType_G03.setActionCommand("SetFutureCommandsType_G03");
		rbtn_commandType_G03.addActionListener(editorViewListener);
		
		btnGr_commandTypes = new ButtonGroup();
		btnGr_commandTypes.add(rbtn_commandType_G00);
		btnGr_commandTypes.add(rbtn_commandType_G01);
		btnGr_commandTypes.add(rbtn_commandType_G02);
		btnGr_commandTypes.add(rbtn_commandType_G03);
		
		txtFld_ArcR = new JTextField();
		txtFld_ArcR.setText("20");
		txtFld_ArcR.setBounds(100, 290, 30, 21);
		txtFld_ArcR.setMargin(new java.awt.Insets(0, 0, 0, 0));
		txtFld_ArcR.addKeyListener(new SetRaduisKeyListener());
		
		txtFld_ArcI = new JTextField();
		txtFld_ArcI.setText("");
		txtFld_ArcI.setBounds(130, 290, 30, 21);
		txtFld_ArcI.setMargin(new java.awt.Insets(0, 0, 0, 0));
		txtFld_ArcI.addKeyListener(new SetIKeyListener());
		
		txtFld_ArcJ = new JTextField();
		txtFld_ArcJ.setText("");
		txtFld_ArcJ.setBounds(160, 290, 30, 21);
		txtFld_ArcJ.setMargin(new java.awt.Insets(0, 0, 0, 0));
		txtFld_ArcJ.addKeyListener(new SetJKeyListener());
		
		lab_R = new JLabel("R");
		lab_R.setBounds(100, 270, 30, 21);
		
		lab_I = new JLabel("I");
		lab_I.setBounds(129, 270, 30, 21);
		
		lab_J = new JLabel("J");
		lab_J.setBounds(160, 270, 30, 21);
		
		chkBx_liftForEachStroke = new JCheckBox();
		chkBx_liftForEachStroke.setText("auto lift");
		chkBx_liftForEachStroke.setBounds(5, 320, 84, 21);
		chkBx_liftForEachStroke.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkBx_liftForEachStroke.setActionCommand(CMD_LIFT_FOR_EACH_STROKE);
		chkBx_liftForEachStroke.addActionListener(editorViewListener);		
		
		btn_Undo = new JButton();
		btn_Undo.setText(CMD_UNDO);
		btn_Undo.setBounds(5, 345, 40, 21);
		btn_Undo.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Undo.setActionCommand(CMD_UNDO);
		btn_Undo.addActionListener(editorViewListener);
		
		btn_Redo = new JButton();
		btn_Redo.setText(CMD_REDO);
		btn_Redo.setBounds(47, 345, 40, 21);
		btn_Redo.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Redo.setActionCommand(CMD_REDO);
		btn_Redo.addActionListener(editorViewListener);
		
		
		chkBx_showOnlyZ0 = new JCheckBox();
		chkBx_showOnlyZ0.setText("show Z0");
		chkBx_showOnlyZ0.setBounds(5, 160, 84, 21);;
		chkBx_showOnlyZ0.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkBx_showOnlyZ0.setActionCommand(CMD_SHOW_ONLY_Z0_SWITCHED);
		chkBx_showOnlyZ0.addActionListener(editorViewListener);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("GCodes editor");
		
		panel_Tools.add(btn_Clear);			
		panel_Tools.add(btn_Save);			
		panel_Tools.add(btn_ConvertImageToGCodes);			
		panel_Tools.add(btn_AddGCodesFromFile);	
		panel_Tools.add(rbtn_Tool_SimpleDraw);		
		panel_Tools.add(rbtn_Tool_ContinuousDraw);		
		panel_Tools.add(rbtn_Tool_SelectVertex);		
		panel_Tools.add(comBox_Scale);
		panel_Tools.add(rbtn_Down);
		panel_Tools.add(rbtn_Lift);
		panel_Tools.add(rbtn_commandType_G00);
		panel_Tools.add(rbtn_commandType_G02);
		panel_Tools.add(rbtn_commandType_G01);
		panel_Tools.add(rbtn_commandType_G03);
		panel_Tools.add(txtFld_ArcR);
		panel_Tools.add(chkBx_liftForEachStroke);
		panel_Tools.add(txtFld_ArcI);
		panel_Tools.add(txtFld_ArcJ);	
		
		panel_Tools.add(lab_R);
		panel_Tools.add(lab_I);
		panel_Tools.add(lab_J);
		
		panel_Tools.add(btn_Undo);
		panel_Tools.add(btn_Redo);
		panel_Tools.add(chkBx_showOnlyZ0);
		
		add(scrollPane_GraphicOutput);
		add(panel_Tools);
		
	
		pack();
		setSize(1280, 770);
		setLocation(0, 0);
	}

	public void setRadiusValue(String str){
		txtFld_ArcR.setText(str);
	}
	
	public void setIValue(String str){
		txtFld_ArcI.setText(str);
	}
	
	public void setJValue(String str){
		txtFld_ArcJ.setText(str);
	}
	
	public static File openFileChooser(String dir, final List<String> desirableExt){
		
		final JFileChooser fc = new JFileChooser(new File(dir));
		fc.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return null;
			}
			
			@Override
			public boolean accept(File f) {
				String ext = f.getName().substring(f.getName().indexOf(".") + 1);
				if (f.isDirectory() || (f.isFile() && desirableExt.contains(ext))) {
					return true;
				}
				return false;
			}
		});

		if(fc.showOpenDialog(null) == 0){
			return fc.getSelectedFile();
		}
		
		return null;
	}
}
