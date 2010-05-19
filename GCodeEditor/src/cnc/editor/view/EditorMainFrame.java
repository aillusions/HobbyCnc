package cnc.editor.view;

import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import cnc.editor.listener.EditorMainFrameListener;

public class EditorMainFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 13423452354765L;
	private static final String[] scaleValues =  
						new String[]{"1", "1.5", "2", "3", "5", "10", "15", "20", "50", "0.1", "0.5"};	
	
	private VisualisationPanel pnl_GraphicOutput;
	
	private JButton btn_AddGCodesFromFile;
	private JButton btn_ConvertImageToGCodes;
	private JButton btn_Clear;
	
	private ButtonGroup btnGr_headPosition;
	private JRadioButton rbtn_Lift;
	private JRadioButton rbtn_Down;
	
	private ButtonGroup btnGr_toolBar;
	private JRadioButton rbtn_Tool_SimpleDraw;
	private JRadioButton rbtn_Tool_ContinuousDraw;
	private JRadioButton rbtn_Tool_SelectVertex;
	
	private JComboBox comBox_Scale;
	
	private JTextArea txtArea_GCodes;
	
	private JScrollPane scrollPane_GCodesEditor;
	private JScrollPane scrollPane_GraphicOutput;
	
	private ButtonGroup btnGr_commandTypes;
	private JRadioButton rbtn_commandType_G00;
	private JRadioButton rbtn_commandType_G02;
	private JTextField txtFld_G02Radius;
	
	private JCheckBox chkBx_liftForEachStroke;
	
	public VisualisationPanel getVisualPanelViewOutput() {
		return pnl_GraphicOutput;
	}
	
	public void setVisualPanelViewOutput(VisualisationPanel visualisationPanel) {
		pnl_GraphicOutput = visualisationPanel;
	}

	//Constructor
	public EditorMainFrame(EditorMainFrameListener editorViewListener, GCodesTextContainer textContainer, VisualisationPanel visualisationPanel) {
		
		txtArea_GCodes = textContainer;
		
		pnl_GraphicOutput = visualisationPanel;	
		
		scrollPane_GraphicOutput = new JScrollPane();			
		scrollPane_GraphicOutput.setBounds(6, 5, 740, 464);	
		scrollPane_GraphicOutput.setViewportView(pnl_GraphicOutput);
		
		scrollPane_GCodesEditor = new JScrollPane(txtArea_GCodes);
		scrollPane_GCodesEditor.setBounds(750, 5, 146, 464);	
		scrollPane_GCodesEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
		btn_Clear = new JButton();
		btn_Clear.setText("clear");
		btn_Clear.setBounds(901, 10, 84, 21);
		btn_Clear.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Clear.setActionCommand("Clear");
		btn_Clear.addActionListener(editorViewListener);
		
		btn_ConvertImageToGCodes = new JButton();
		btn_ConvertImageToGCodes.setText("image");
		btn_ConvertImageToGCodes.setBounds(901, 40, 84, 21);
		btn_ConvertImageToGCodes.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_ConvertImageToGCodes.setActionCommand("ConvertImageToGCodes");
		btn_ConvertImageToGCodes.addActionListener(editorViewListener);
				
		btn_AddGCodesFromFile = new JButton();			
		btn_AddGCodesFromFile.setText("open");
		btn_AddGCodesFromFile.setBounds(901, 70, 84, 21);
		btn_AddGCodesFromFile.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_AddGCodesFromFile.setActionCommand("AddGCodesFromFile");
		btn_AddGCodesFromFile.addActionListener(editorViewListener);
		
		//Tool bar controls
		rbtn_Tool_SimpleDraw = new JRadioButton();		
		rbtn_Tool_SimpleDraw.setText("draw");
		rbtn_Tool_SimpleDraw.setBounds(901, 100, 84, 21);
		rbtn_Tool_SimpleDraw.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Tool_SimpleDraw.addActionListener(editorViewListener);
		rbtn_Tool_SimpleDraw.setActionCommand("switchToolsTo_SimpleDraw");
		
		rbtn_Tool_SelectVertex = new JRadioButton();
		rbtn_Tool_SelectVertex.setText("select");
		rbtn_Tool_SelectVertex.setBounds(901, 120, 84, 21);
		rbtn_Tool_SelectVertex.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Tool_SelectVertex.addActionListener(editorViewListener);
		rbtn_Tool_SelectVertex.setActionCommand("switchToolsTo_SelectVertexes");
		
		rbtn_Tool_ContinuousDraw = new JRadioButton();
		rbtn_Tool_ContinuousDraw.setText("continue");
		rbtn_Tool_ContinuousDraw.setBounds(901, 140, 84, 21);
		rbtn_Tool_ContinuousDraw.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Tool_ContinuousDraw.addActionListener(editorViewListener);
		rbtn_Tool_ContinuousDraw.setActionCommand("switchToolsTo_ContinuousDraw");
		
		btnGr_toolBar = new ButtonGroup();
		btnGr_toolBar.add(rbtn_Tool_SimpleDraw);
		btnGr_toolBar.add(rbtn_Tool_SelectVertex);
		btnGr_toolBar.add(rbtn_Tool_ContinuousDraw);
		rbtn_Tool_SimpleDraw.setSelected(true);
		
		ComboBoxModel comBoxModel_Scale = new DefaultComboBoxModel(scaleValues);						
		comBox_Scale = new JComboBox();			
		comBox_Scale.setModel(comBoxModel_Scale);
		comBox_Scale.setBounds(901, 190, 84, 21);
		comBox_Scale.setActionCommand("Scale");
		comBox_Scale.addActionListener(editorViewListener);
		
		rbtn_Lift = new JRadioButton();
		rbtn_Lift.setText("UP");
		rbtn_Lift.setBounds(901, 220, 84, 21);
		rbtn_Lift.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Lift.setActionCommand("LiftWorkHead");
		rbtn_Lift.addActionListener(editorViewListener);
		rbtn_Lift.setSelected(true);
		
		rbtn_Down = new JRadioButton();
		rbtn_Down.setText("DOWN");
		rbtn_Down.setBounds(901, 240, 84, 21);
		rbtn_Down.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_Down.setActionCommand("DescendWorkHead");
		rbtn_Down.addActionListener(editorViewListener);

		
		btnGr_headPosition = new ButtonGroup();
		btnGr_headPosition.add(rbtn_Lift);
		btnGr_headPosition.add(rbtn_Down);
				
		rbtn_commandType_G00 = new JRadioButton();
		rbtn_commandType_G00.setText("G00");
		rbtn_commandType_G00.setBounds(901, 270, 45, 21);
		rbtn_commandType_G00.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_commandType_G00.setActionCommand("SetFutureCommandsType_G00");
		rbtn_commandType_G00.addActionListener(editorViewListener);
		rbtn_commandType_G00.setSelected(true);
		
		rbtn_commandType_G02 = new JRadioButton();
		rbtn_commandType_G02.setText("G02");
		rbtn_commandType_G02.setBounds(901, 290, 45, 21);
		rbtn_commandType_G02.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rbtn_commandType_G02.setActionCommand("SetFutureCommandsType_G02");
		rbtn_commandType_G02.addActionListener(editorViewListener);
		
		btnGr_commandTypes = new ButtonGroup();
		btnGr_commandTypes.add(rbtn_commandType_G00);
		btnGr_commandTypes.add(rbtn_commandType_G02);
		
		txtFld_G02Radius = new JTextField();
		txtFld_G02Radius.setText("20");
		txtFld_G02Radius.setBounds(950, 290, 30, 21);
		txtFld_G02Radius.setMargin(new java.awt.Insets(0, 0, 0, 0));
		txtFld_G02Radius.addKeyListener(editorViewListener);
		
		chkBx_liftForEachStroke = new JCheckBox();
		chkBx_liftForEachStroke.setText("lift down");
		chkBx_liftForEachStroke.setBounds(901, 320, 84, 21);
		chkBx_liftForEachStroke.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkBx_liftForEachStroke.addChangeListener(editorViewListener);
		
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("GCodes editor");
		
		add(scrollPane_GraphicOutput);
		add(scrollPane_GCodesEditor);
		add(btn_Clear);			
		add(btn_ConvertImageToGCodes);			
		add(btn_AddGCodesFromFile);	
		add(rbtn_Tool_SimpleDraw);		
		add(rbtn_Tool_ContinuousDraw);		
		add(rbtn_Tool_SelectVertex);		
		add(comBox_Scale);
		add(rbtn_Down);
		add(rbtn_Lift);
		add(rbtn_commandType_G00);
		add(rbtn_commandType_G02);
		add(txtFld_G02Radius);
		add(chkBx_liftForEachStroke);
		
		pack();
		setSize(1000, 500);	
		setLocationRelativeTo(null);
	}

	public static File openFileChooser(String dir, final String desirableExt){
		
		final JFileChooser fc = new JFileChooser(new File(dir));
		fc.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return null;
			}
			
			@Override
			public boolean accept(File f) {
				String ext = f.getName().substring(f.getName().indexOf(".") + 1);
				if (f.isDirectory() || (f.isFile() && ext.equals(desirableExt))) {
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