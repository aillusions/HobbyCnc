package Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Main extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JButton clear;
	private JPanel jPanel2;
	private JScrollPane jScrollPane2;
	private JTextArea jTextArea1;
	private JScrollPane jScrollPane1;
	
	private JPanel currentPoint = null;

	/**
	* Auto-generated main method to display this JFrame
	*/
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
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(43, 10, 556, 300);
				{
				
					jPanel1 = new JPanel(){					
						@Override
						public void paint(Graphics g) {
							super.paint(g);
							
							g.setColor(Color.black);
							drawCoordinates(g);
							g.setColor(Color.red);
							DrawPicture(g);	
										
						}
						private void DrawPicture(Graphics g) {	
							String cmd = jTextArea1.getText();
							String[] cmdArray = cmd.replace("\r", "").split("\n");
							BigDecimalPoint3D prevPos = new BigDecimalPoint3D();
							for(int i = 0; i < cmdArray.length; i ++){
								GCommand gc = GCodeParser.parseCommand(cmdArray[i], prevPos);
								if(gc != null){
									BigDecimalPoint3D newPos = gc.getCoord();
									g.drawLine(
											Integer.parseInt(prevPos.getX().toBigInteger().toString()) * scale, 
											Integer.parseInt(prevPos.getY().toBigInteger().toString()) * scale , 
											Integer.parseInt(newPos.getX().toBigInteger().toString()) * scale, 
											Integer.parseInt(newPos.getY().toBigInteger().toString()) * scale);
									
																	
									prevPos = newPos;
									
								}
							}
							
							EquationOfCircle circle = new EquationOfCircle(0, 40, 40, 0, 40);
							g.drawArc((int)circle.getTop(), (int)circle.getLeft(), (int)circle.getSize(),
									(int)circle.getSize(), (int)circle.getAngelGamma(), (int)circle.getAngelPhy());
							
						}

						private void drawCoordinates(Graphics g) {
							g.drawLine(theGap, theGap, coordLenght, theGap);			
							g.drawLine(coordLenght-3, theGap-3, coordLenght, theGap);	
							g.drawLine(coordLenght-3, theGap+3, coordLenght, theGap);
							
							g.drawLine(theGap, theGap, theGap, coordLenght);
							g.drawLine(theGap-3, coordLenght-3, theGap, coordLenght);
							g.drawLine(theGap+3, coordLenght-3, theGap, coordLenght);
							
							g.drawString("X", coordLenght+5, theGap+5);
							g.drawString("Y", theGap+5, coordLenght+5);
							
						}
					};
					
					jScrollPane1.setViewportView(jPanel1);
					jPanel1.setBounds(293, 76, 171, 158);
					
					jPanel1.setLayout(null);
					jPanel1.setPreferredSize(new java.awt.Dimension(817, 498));
					jPanel1.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent evt) {
							jPanel1MousePressed(evt);
						}
					});
					jPanel1.addMouseMotionListener(new MouseMotionAdapter() {
						public void mouseMoved(MouseEvent evt) {
							jPanel1MouseMoved(evt);
						}
					});
					/*{
						jPanel2 = new JPanel();
						jPanel1.add(jPanel2);
						jPanel2.setBounds(291, 34, 4, 4);
						jPanel2.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
						jPanel2.addMouseListener(new MouseAdapter() {
							public void mouseReleased(MouseEvent evt) {
								//currentPoint = null;
							}
							public void mousePressed(MouseEvent evt) {
								if(currentPoint == null){
								currentPoint = jPanel2;
								}else{
									currentPoint = null;
								}
							}
						});
					}*/
				}
			}
			{
				jScrollPane2 = new JScrollPane();
				getContentPane().add(jScrollPane2);
				jScrollPane2.setBounds(605, 12, 174, 298);
				{
					jTextArea1 = new JTextArea();
					jScrollPane2.setViewportView(jTextArea1);
					
					jTextArea1.setText("G00 X10 Y10 \n"+
										"G00 X40 Y10 \n"+
										"G00 X40 Y40 \n"+
										"G00 X10 Y40 \n"+
										"G00 X10 Y10 \n");
					
					jTextArea1.setBounds(183, 42, 198, 299);
					jTextArea1.setPreferredSize(new java.awt.Dimension(210, 313));
					jTextArea1.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent evt) {
							jTextArea1KeyReleased(evt);
						}
					});
						}
			}
			{
				clear = new JButton();
				getContentPane().add(clear);
				clear.setText("X");
				clear.setBounds(783, 17, 30, 21);
				clear.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent evt) {
						jTextArea1.setText("");
						jPanel1.repaint();
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
		jPanel1.repaint();
	}
	

	private void jPanel1MouseMoved(MouseEvent evt) {
		if(currentPoint != null){
			currentPoint.setBounds(evt.getX(),evt.getY(), 4, 4);
		}
	}
	
	private void jPanel1MousePressed(MouseEvent evt) {
		double x = Math.round(evt.getPoint().getX()/scale);
		double y = Math.round(evt.getPoint().getY()/scale);
		jTextArea1.setText(jTextArea1.getText() + "\n G00 X" + x + " Y" + y);
		jPanel1.repaint();
	}

	private int theGap = 0;
	private int coordLenght = 400;
	private int scale = 5;
}
