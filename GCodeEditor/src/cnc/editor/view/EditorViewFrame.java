package cnc.editor.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.text.Document;

public class EditorViewFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 13423452354765L;
	private static final String[] scaleValues =  
						new String[]{"1", "1.5", "2", "3", "5", "10", "20", "50", "0.1", "0.5"};
	
	private VisualisationPanel pnl_GraphicOutput;
	private JButton btn_AddGCodesFromFile;
	private JButton btn_ConvertImageToGCodes;
	private JButton btn_Clear;
	private JComboBox comBox_Scale;
	private JScrollPane scrollPane_GCodesEditor;
	private JTextArea txtArea_GCodes;
	private JScrollPane scrollPane_GraphicOutput;

	//Constructor
	public EditorViewFrame(ActionListener actionListener, Document doc) {
		
		txtArea_GCodes = new JTextArea(doc);
		
		pnl_GraphicOutput = new VisualisationPanel(txtArea_GCodes);			
		pnl_GraphicOutput.setLayout(null);
		pnl_GraphicOutput.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				pnl_GraphicOutput_MousePressed(evt);
			}
		});			
		pnl_GraphicOutput.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt) {
				pnl_GraphicOutput_MouseMoved(evt);
			}
		});
					
		scrollPane_GraphicOutput = new JScrollPane();			
		scrollPane_GraphicOutput.setBounds(6, 5, 704, 464);	
		scrollPane_GraphicOutput.setViewportView(pnl_GraphicOutput);
		
		scrollPane_GCodesEditor = new JScrollPane(txtArea_GCodes);
		scrollPane_GCodesEditor.setBounds(716, 5, 174, 464);	
		scrollPane_GCodesEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
		btn_Clear = new JButton();
		btn_Clear.setText("clear");
		btn_Clear.setBounds(901, 8, 84, 21);
		btn_Clear.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Clear.setActionCommand("Clear");
		btn_Clear.addActionListener(actionListener);
		
		btn_ConvertImageToGCodes = new JButton();
		btn_ConvertImageToGCodes.setText("open image");
		btn_ConvertImageToGCodes.setBounds(901, 35, 84, 21);
		btn_ConvertImageToGCodes.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_ConvertImageToGCodes.setActionCommand("ConvertImageToGCodes");
		btn_ConvertImageToGCodes.addActionListener(actionListener);
				
		btn_AddGCodesFromFile = new JButton();			
		btn_AddGCodesFromFile.setText("import file");
		btn_AddGCodesFromFile.setBounds(901, 62, 84, 21);
		btn_AddGCodesFromFile.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_AddGCodesFromFile.setActionCommand("AddGCodesFromFile");
		btn_AddGCodesFromFile.addActionListener(actionListener);
		
		ComboBoxModel comBoxModel_Scale = new DefaultComboBoxModel(scaleValues);						
		comBox_Scale = new JComboBox();			
		comBox_Scale.setModel(comBoxModel_Scale);
		comBox_Scale.setBounds(901, 89, 84, 21);
		comBox_Scale.setActionCommand("Scale");
		comBox_Scale.addActionListener(actionListener);
		comBox_Scale.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				scaleVisualisationPanel(e);				
			}});
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("GCodes editor");
		
		getContentPane().add(scrollPane_GraphicOutput);
		getContentPane().add(scrollPane_GCodesEditor);
		getContentPane().add(btn_Clear);			
		getContentPane().add(btn_ConvertImageToGCodes);			
		getContentPane().add(btn_AddGCodesFromFile);			
		getContentPane().add(comBox_Scale);
		pack();
		setSize(1000, 500);	
	}

	private void pnl_GraphicOutput_MouseMoved(MouseEvent evt) {

	}

	private void pnl_GraphicOutput_MousePressed(MouseEvent evt) {
		double x = Math.round(evt.getPoint().getX() / pnl_GraphicOutput.getScale());
		double y = Math.round(evt.getPoint().getY() / pnl_GraphicOutput.getScale());
		txtArea_GCodes.setText(txtArea_GCodes.getText() + "\n G00 X" + x + " Y"	+ y);
	}
	
	public void repaintVisualPanel(){
		pnl_GraphicOutput.repaint();
	}
	
	private void scaleVisualisationPanel(ActionEvent e) {
		float scale = Float.parseFloat(((JComboBox)e.getSource()).getSelectedItem().toString());
		pnl_GraphicOutput.setScale(scale);
		repaintVisualPanel();
	}	
}
