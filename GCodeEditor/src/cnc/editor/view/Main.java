package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import cnc.GCodeAcceptor;
import cnc.editor.GCodeParser;
import cnc.editor.domain.BigDecimalPoint3D;
import cnc.editor.domain.GCommand;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.IDataStorage;
import cnc.storage.light.BitMapArrayDataStorage;

public class Main extends javax.swing.JFrame implements GCodeAcceptor {

	private static final long serialVersionUID = 13423452354765L;
	private JPanel pnl_GraphicOutput;
	private JButton btn_AddGCodesFromFile;
	private JButton btn_ConvertImageToGCodes;
	private JButton btn_Clear;
	private JComboBox comBox_Scale;
	private JScrollPane scrollPane_GCodesEditor;
	private JTextArea txtArea_GCodes;
	private JScrollPane scrollPane_GraphicOutput;
	private JPanel currentPoint = null;

	private float scale = 1;

	public void putGCode(String gcode) {
		txtArea_GCodes.append("\r\n" + gcode);
		pnl_GraphicOutput.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main inst = new Main();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public Main() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {			
			pnl_GraphicOutput = new CNCViewPanel();			
			pnl_GraphicOutput.setLayout(null);
			pnl_GraphicOutput.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					pnl_GraphicOutput_MousePressed(evt);
				}
			});			
			pnl_GraphicOutput
					.addMouseMotionListener(new MouseMotionAdapter() {
						public void mouseMoved(MouseEvent evt) {
							pnl_GraphicOutput_MouseMoved(evt);
						}
					});
			
			txtArea_GCodes = new JTextArea();
			txtArea_GCodes.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					txtArea_GCodes_KeyReleased(evt);
				}
			});
						
			scrollPane_GraphicOutput = new JScrollPane();			
			scrollPane_GraphicOutput.setBounds(6, 5, 556, 300);	
			scrollPane_GraphicOutput.setViewportView(pnl_GraphicOutput);
			
			scrollPane_GCodesEditor = new JScrollPane(txtArea_GCodes);
			scrollPane_GCodesEditor.setBounds(568, 5, 174, 298);	
			scrollPane_GCodesEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
			btn_Clear = new JButton();
			btn_Clear.setText("clear");
			btn_Clear.setBounds(751, 17, 84, 21);
			btn_Clear.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					btn_Clear_MouseReleased(evt);
				}
			});		
		
			btn_ConvertImageToGCodes = new JButton();
			btn_ConvertImageToGCodes.setText("open image");
			btn_ConvertImageToGCodes.setBounds(751, 44, 84, 21);
			btn_ConvertImageToGCodes.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					btn_ConvertImageToGCodes_MouseReleased(evt);
				}
			});
			
			
			btn_AddGCodesFromFile = new JButton();			
			btn_AddGCodesFromFile.setText("import file");
			btn_AddGCodesFromFile.setBounds(751, 71, 84, 21);
			btn_AddGCodesFromFile.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					btn_AddGCodesFromFile_MouseReleased(evt);
				}
			});
			
			ComboBoxModel comBoxModel_Scale = 
					new DefaultComboBoxModel(
							new String[] {"1", "5", "10", "15", "20", "30", "50", "100" , "0.1", "0.5"});
							
			comBox_Scale = new JComboBox();			
			comBox_Scale.setModel(comBoxModel_Scale);
			comBox_Scale.setBounds(751, 104, 84, 21);
			comBox_Scale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					comBox_Scale_ActionPerformed(evt);
				}
			});
			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);			
			this.setTitle("GCodes editor");
			getContentPane().add(scrollPane_GraphicOutput);
			getContentPane().add(scrollPane_GCodesEditor);
			getContentPane().add(btn_Clear);
			btn_Clear.setMargin(new java.awt.Insets(0, 0, 0, 0));
			getContentPane().add(btn_ConvertImageToGCodes);
			btn_ConvertImageToGCodes.setMargin(new java.awt.Insets(0, 0, 0, 0));
			getContentPane().add(btn_AddGCodesFromFile);
			btn_AddGCodesFromFile.setMargin(new java.awt.Insets(0, 0, 0, 0));
			getContentPane().add(comBox_Scale);

			pack();
			this.setSize(846, 356);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void txtArea_GCodes_KeyReleased(KeyEvent evt) {
		pnl_GraphicOutput.repaint();
	}

	private void pnl_GraphicOutput_MouseMoved(MouseEvent evt) {
		if (currentPoint != null) {
			currentPoint.setBounds(evt.getX(), evt.getY(), 4, 4);
		}
	}

	private void pnl_GraphicOutput_MousePressed(MouseEvent evt) {
		double x = Math.round(evt.getPoint().getX() / scale);
		double y = Math.round(evt.getPoint().getY() / scale);
		txtArea_GCodes.setText(txtArea_GCodes.getText() + "\n G00 X" + x + " Y"
				+ y);
		pnl_GraphicOutput.repaint();
	}

	protected void btn_Clear_MouseReleased(MouseEvent evt) {
		txtArea_GCodes.setText("");
		pnl_GraphicOutput.repaint();		
		pnl_GraphicOutput.setPreferredSize(new Dimension(1, 1));
		pnl_GraphicOutput.revalidate();
	}

	private void btn_ConvertImageToGCodes_MouseReleased(MouseEvent evt) {

		final JFileChooser fc = new JFileChooser(new File("./parser"));
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public boolean accept(File f) {
				String ext = f.getName().substring(f.getName().indexOf(".") + 1);
				if (f.isDirectory() || (f.isFile() && ext.equals("bmp"))) {
					return true;
				}
				return false;
			}
		});

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == 0) {
			
			//clear view
			txtArea_GCodes.setText("");
			pnl_GraphicOutput.repaint();		
			
			//IDataStorage store = new TrickyDataStorage();
			IDataStorage store = new BitMapArrayDataStorage();
			store.clearStorage();
			
			BmpParser parser = new BmpParser();
			parser.setStorage(store);
			long qty = parser.loadbitmap(fc.getSelectedFile().getPath());
			
			BmpFilePrinter bmpPrinter = new BmpFilePrinter(this);			
			bmpPrinter.setStore(store);
			bmpPrinter.StartBuild();
		}
	}

	private void btn_AddGCodesFromFile_MouseReleased(MouseEvent evt) {

		final JFileChooser fc = new JFileChooser(new File("./gcodes"));
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public boolean accept(File f) {
				String ext = f.getName()
						.substring(f.getName().indexOf(".") + 1);
				if (f.isDirectory() || (f.isFile() && ext.equals("cnc"))) {
					return true;
				}
				return false;
			}
		});

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == 0) {

			txtArea_GCodes.setText("");
			pnl_GraphicOutput.repaint();

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fc.getSelectedFile())));
				String line = null;
				while ((line = br.readLine()) != null) {
					putGCode(line);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
	private void comBox_Scale_ActionPerformed(ActionEvent evt) {
		scale = Float.parseFloat(comBox_Scale.getSelectedItem().toString());
		pnl_GraphicOutput.repaint();
	}

	public class CNCViewPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int theGap = 0;
		private int coordLenght = 200;


		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.black);
			drawCoordinates(g);			
			DrawPicture(g);
		}

		private void DrawPicture(Graphics g) {
			String cmd = txtArea_GCodes.getText();
			String[] cmdArray = cmd.replace("\r", "").split("\n");
			BigDecimalPoint3D prevPos = new BigDecimalPoint3D();
			for (int i = 0; i < cmdArray.length; i++) {
				GCommand gc = GCodeParser.parseCommand(cmdArray[i], prevPos);
				
				if (gc != null) {
				
					BigDecimalPoint3D newPos = gc.getCoord();
					
					if(Math.round(prevPos.getZ().floatValue()) <= 0
					  && Math.round(newPos.getZ().floatValue()) <= 0){
					 	g.setColor(Color.red);
					}else{
						g.setColor(Color.blue);
					}
					 
					 
					int prevX = Math.round(prevPos.getX().floatValue() * scale);
					int prevY = Math.round(prevPos.getY().floatValue() * scale);
					int newX = Math.round(newPos.getX().floatValue() * scale);
					int newY = Math.round(newPos.getY().floatValue() * scale);
					
					double panelWidth = pnl_GraphicOutput.getSize().getWidth();
					double panelHeight = pnl_GraphicOutput.getSize().getHeight();	
					if(panelWidth < newX || panelHeight < newY){	
						pnl_GraphicOutput.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 50, (int)Math.max(newY, panelHeight)+50));
						pnl_GraphicOutput.revalidate();
					}
					g.drawLine(prevX, prevY, newX, newY);
					prevPos = newPos;
				}
			}
		}

		private void drawCoordinates(Graphics g) {
			g.drawLine(theGap, theGap, coordLenght, theGap);
			g.drawLine(coordLenght - 3, theGap - 3, coordLenght, theGap);
			g.drawLine(coordLenght - 3, theGap + 3, coordLenght, theGap);

			g.drawLine(theGap, theGap, theGap, coordLenght);
			g.drawLine(theGap - 3, coordLenght - 3, theGap, coordLenght);
			g.drawLine(theGap + 3, coordLenght - 3, theGap, coordLenght);

			g.drawString("X", coordLenght + 5, theGap + 5);
			g.drawString("Y", theGap + 5, coordLenght + 5);

		}
	}

}
