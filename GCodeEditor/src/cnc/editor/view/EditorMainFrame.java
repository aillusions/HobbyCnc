package cnc.editor.view;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

public class EditorMainFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 13423452354765L;
	private static final String[] scaleValues =  
						new String[]{"1", "1.5", "2", "3", "5", "10", "15", "20", "50", "0.1", "0.5"};	
	
	private VisualisationPanel pnl_GraphicOutput;
	
	private JButton btn_AddGCodesFromFile;
	private JButton btn_ConvertImageToGCodes;
	private JButton btn_Clear;
	
	private ButtonGroup headPositionGroup;
	private JRadioButton btn_Lift;
	private JRadioButton btn_Descend;
	
	private ButtonGroup toolBarGroup;
	private JRadioButton btn_Tool_SimpleDraw;
	private JRadioButton btn_Tool_ContinuousDraw;
	private JRadioButton btn_Tool_SelectVertex;
	
	private JComboBox comBox_Scale;
	
	private JTextArea txtArea_GCodes;
	
	private JScrollPane scrollPane_GCodesEditor;
	private JScrollPane scrollPane_GraphicOutput;
	
	public VisualisationPanel getVisualPanelViewOutput() {
		return pnl_GraphicOutput;
	}
	
	public void setVisualPanelViewOutput(VisualisationPanel visualisationPanel) {
		pnl_GraphicOutput = visualisationPanel;
	}

	//Constructor
	public EditorMainFrame(ActionListener editorViewListener, GCodesTextContainer textContainer, VisualisationPanel visualisationPanel) {
		
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
		btn_Tool_SimpleDraw = new JRadioButton();		
		btn_Tool_SimpleDraw.setText("draw");
		btn_Tool_SimpleDraw.setBounds(901, 100, 84, 21);
		btn_Tool_SimpleDraw.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Tool_SimpleDraw.addActionListener(editorViewListener);
		btn_Tool_SimpleDraw.setActionCommand("switchToolsTo_SimpleDraw");
		
		btn_Tool_SelectVertex = new JRadioButton();
		btn_Tool_SelectVertex.setText("select");
		btn_Tool_SelectVertex.setBounds(901, 130, 84, 21);
		btn_Tool_SelectVertex.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Tool_SelectVertex.addActionListener(editorViewListener);
		btn_Tool_SelectVertex.setActionCommand("switchToolsTo_SelectVertexes");
		
		btn_Tool_ContinuousDraw = new JRadioButton();
		btn_Tool_ContinuousDraw.setText("continue");
		btn_Tool_ContinuousDraw.setBounds(901, 160, 84, 21);
		btn_Tool_ContinuousDraw.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Tool_ContinuousDraw.addActionListener(editorViewListener);
		btn_Tool_ContinuousDraw.setActionCommand("switchToolsTo_ContinuousDraw");
		
		toolBarGroup = new ButtonGroup();
		toolBarGroup.add(btn_Tool_SimpleDraw);
		toolBarGroup.add(btn_Tool_SelectVertex);
		toolBarGroup.add(btn_Tool_ContinuousDraw);
		btn_Tool_SimpleDraw.setSelected(true);
		
		ComboBoxModel comBoxModel_Scale = new DefaultComboBoxModel(scaleValues);						
		comBox_Scale = new JComboBox();			
		comBox_Scale.setModel(comBoxModel_Scale);
		comBox_Scale.setBounds(901, 190, 84, 21);
		comBox_Scale.setActionCommand("Scale");
		comBox_Scale.addActionListener(editorViewListener);
		
		btn_Lift = new JRadioButton();
		btn_Lift.setText("lift");
		btn_Lift.setBounds(901, 220, 84, 21);
		btn_Lift.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Lift.setActionCommand("LiftWorkHead");
		btn_Lift.addActionListener(editorViewListener);
		
		btn_Descend = new JRadioButton();
		btn_Descend.setText("descend");
		btn_Descend.setBounds(901, 250, 84, 21);
		btn_Descend.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Descend.setActionCommand("DescendWorkHead");
		btn_Descend.addActionListener(editorViewListener);
		
		headPositionGroup = new ButtonGroup();
		headPositionGroup.add(btn_Lift);
		headPositionGroup.add(btn_Descend);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("GCodes editor");
		
		add(scrollPane_GraphicOutput);
		add(scrollPane_GCodesEditor);
		add(btn_Clear);			
		add(btn_ConvertImageToGCodes);			
		add(btn_AddGCodesFromFile);	
		add(btn_Tool_SimpleDraw);		
		add(btn_Tool_ContinuousDraw);		
		add(btn_Tool_SelectVertex);		
		add(comBox_Scale);
		add(btn_Descend);
		add(btn_Lift);
		
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
