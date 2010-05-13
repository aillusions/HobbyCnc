package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import cnc.GCodeAcceptor;
import cnc.operator.storage.BitMapArrayDataStorage;
import cnc.operator.storage.IDataStorage;
import cnc.operator.storage.TrickyDataStorage;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;

public class Main extends javax.swing.JFrame implements GCodeAcceptor {

	private static final long serialVersionUID = 13423452354765L;
	private JPanel panelGraphicOutput;
	private JButton btnAddGCodesFromFile;
	private JButton btnConvertImageIntoGCodes;
	private JButton btnClear;
	private JScrollPane scrollPanelGCodesEditor;
	private JTextArea txtAreaGCodes;
	private JScrollPane scrollPaneGraphicOutput;
	private JPanel currentPoint = null;

	private float scale = 5;

	public void putGCode(String gcode) {
		txtAreaGCodes.append("\r\n" + gcode);
		panelGraphicOutput.repaint();
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
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				scrollPaneGraphicOutput = new JScrollPane();
				getContentPane().add(scrollPaneGraphicOutput);
				scrollPaneGraphicOutput.setBounds(43, 10, 556, 300);
				{

					panelGraphicOutput = new CNCViewPanel();
					scrollPaneGraphicOutput.setViewportView(panelGraphicOutput);
					panelGraphicOutput.setLayout(null);
					panelGraphicOutput.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent evt) {
							panelGraphicOutputMousePressed(evt);
						}
					});
					panelGraphicOutput
							.addMouseMotionListener(new MouseMotionAdapter() {
								public void mouseMoved(MouseEvent evt) {
									panelGraphicOutputMouseMoved(evt);
								}
							});
				}
			}
			{
				txtAreaGCodes = new JTextArea();
				scrollPanelGCodesEditor = new JScrollPane(txtAreaGCodes);
				getContentPane().add(scrollPanelGCodesEditor);
				scrollPanelGCodesEditor.setBounds(605, 12, 174, 298);	
				scrollPanelGCodesEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);							
				{
					txtAreaGCodes.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent evt) {
							jTextArea1KeyReleased(evt);
						}
					});
				}
			}
			{
				btnClear = new JButton();
				getContentPane().add(btnClear);
				btnClear.setText("clear");
				btnClear.setBounds(783, 17, 55, 21);
				btnClear.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent evt) {
						btnClearMouseReleased(evt);
					}
				});
			}
			{
				btnConvertImageIntoGCodes = new JButton();
				getContentPane().add(btnConvertImageIntoGCodes);
				btnConvertImageIntoGCodes.setText("open image");
				btnConvertImageIntoGCodes.setBounds(783, 50, 55, 21);
				btnConvertImageIntoGCodes.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent evt) {
						btnConvertImageIntoGCodesMouseReleased(evt);
					}
				});
			}
			{
				btnAddGCodesFromFile = new JButton();
				getContentPane().add(btnAddGCodesFromFile);
				btnAddGCodesFromFile.setText("import from file");
				btnAddGCodesFromFile.setBounds(783, 77, 55, 21);
				btnAddGCodesFromFile.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent evt) {
						btnAddGCodesFromFileMouseReleased(evt);
					}
				});
			}
			pack();
			this.setSize(846, 356);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jTextArea1KeyReleased(KeyEvent evt) {
		panelGraphicOutput.repaint();
	}

	private void panelGraphicOutputMouseMoved(MouseEvent evt) {
		if (currentPoint != null) {
			currentPoint.setBounds(evt.getX(), evt.getY(), 4, 4);
		}
	}

	private void panelGraphicOutputMousePressed(MouseEvent evt) {
		double x = Math.round(evt.getPoint().getX() / scale);
		double y = Math.round(evt.getPoint().getY() / scale);
		txtAreaGCodes.setText(txtAreaGCodes.getText() + "\n G00 X" + x + " Y"
				+ y);
		panelGraphicOutput.repaint();
	}

	protected void btnClearMouseReleased(MouseEvent evt) {
		txtAreaGCodes.setText("");
		panelGraphicOutput.repaint();
	}

	private void btnConvertImageIntoGCodesMouseReleased(MouseEvent evt) {

		final JFileChooser fc = new JFileChooser(new File("./parser"));
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public boolean accept(File f) {
				String ext = f.getName()
						.substring(f.getName().indexOf(".") + 1);
				if (f.isDirectory() || (f.isFile() && ext.equals("bmp"))) {
					return true;
				}
				return false;
			}
		});

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == 0) {
			
			//clear view
			txtAreaGCodes.setText("");
			panelGraphicOutput.repaint();		
			
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

	private void btnAddGCodesFromFileMouseReleased(MouseEvent evt) {

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

			txtAreaGCodes.setText("");
			panelGraphicOutput.repaint();

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
			String cmd = txtAreaGCodes.getText();
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
					
					//double panelWidth = panelGraphicOutput.getSize().getWidth();
					//double panelHeight = panelGraphicOutput.getSize().getHeight();					
					//panelGraphicOutput.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth), (int)Math.max(newY, panelHeight)));
					panelGraphicOutput.setPreferredSize(new Dimension(newX,newY));						 			
					panelGraphicOutput.revalidate();

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
