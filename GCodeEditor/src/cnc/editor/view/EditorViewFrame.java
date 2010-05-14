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
import javax.swing.text.Document;

public class EditorViewFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 13423452354765L;
	private static final String[] scaleValues =  
						new String[]{"1", "1.5", "2", "3", "5", "10", "20", "50", "0.1", "0.5"};
	
	
	private VisualisationPanel pnl_GraphicOutput;
	
	private JButton btn_AddGCodesFromFile;
	private JButton btn_ConvertImageToGCodes;
	private JButton btn_Clear;
	
	private ButtonGroup toolBarGroup;
	private JRadioButton btn_Tool_SimpleDraw;
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
	public EditorViewFrame(ActionListener editorViewListener, Document doc, VisualisationPanel visualisationPanel) {
		
		txtArea_GCodes = new JTextArea(doc);
		
		pnl_GraphicOutput = visualisationPanel;	
		
		scrollPane_GraphicOutput = new JScrollPane();			
		scrollPane_GraphicOutput.setBounds(6, 5, 704, 464);	
		scrollPane_GraphicOutput.setViewportView(pnl_GraphicOutput);
		
		scrollPane_GCodesEditor = new JScrollPane(txtArea_GCodes);
		scrollPane_GCodesEditor.setBounds(716, 5, 174, 464);	
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
		btn_Tool_SimpleDraw = new JRadioButton ();
		btn_Tool_SimpleDraw.setText("draw");
		btn_Tool_SimpleDraw.setBounds(901, 100, 84, 21);
		btn_Tool_SimpleDraw.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Tool_SimpleDraw.addActionListener(editorViewListener);
		btn_Tool_SimpleDraw.setActionCommand("switchToolsTo_SimpleDraw");
		
		btn_Tool_SelectVertex = new JRadioButton ();
		btn_Tool_SelectVertex.setText("select");
		btn_Tool_SelectVertex.setBounds(901, 130, 84, 21);
		btn_Tool_SelectVertex.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Tool_SelectVertex.addActionListener(editorViewListener);
		btn_Tool_SelectVertex.setActionCommand("switchToolsTo_SimpleDraw");
		
		toolBarGroup = new ButtonGroup();
		toolBarGroup.add(btn_Tool_SimpleDraw);
		toolBarGroup.add(btn_Tool_SelectVertex);
		
		ComboBoxModel comBoxModel_Scale = new DefaultComboBoxModel(scaleValues);						
		comBox_Scale = new JComboBox();			
		comBox_Scale.setModel(comBoxModel_Scale);
		comBox_Scale.setBounds(901, 160, 84, 21);
		comBox_Scale.setActionCommand("Scale");
		comBox_Scale.addActionListener(editorViewListener);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("GCodes editor");
		
		add(scrollPane_GraphicOutput);
		add(scrollPane_GCodesEditor);
		add(btn_Clear);			
		add(btn_ConvertImageToGCodes);			
		add(btn_AddGCodesFromFile);	
		add(btn_Tool_SimpleDraw);		
		add(btn_Tool_SelectVertex);		
		add(comBox_Scale);
		
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
