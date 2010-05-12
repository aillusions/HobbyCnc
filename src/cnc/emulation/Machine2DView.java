package cnc.emulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import cnc.emulation.model.Brick;
import cnc.operator.ICncByteSignalGenerator;
import cnc.operator.model.DoublePoint3D;


public class Machine2DView extends JFrame implements IMachineListener {
	
	private static final long serialVersionUID = 1L;
	private  int zoom;
	private int theGap = 5;
	private int coordLenght = 200;
	private double resolution;
	
	private JScrollPane jScrollPaneYZ;
	private JButton jButtonPause;
	private JPanel jPanelYZ;
	private JPanel jPanelXY;
	private JScrollPane jScrollPaneXY;
	private List<Brick> bricks = new ArrayList<Brick>();
	private ICncByteSignalGenerator bGenerator;
	DoublePoint3D currHeadPosition = new DoublePoint3D(0d,0d,0d);
	private int wasTact = 0;
	
	private long repaintEveryBytes = 0;
	
	public Machine2DView(List<Brick> buildingBricks, ICncByteSignalGenerator bytesGenerator, 
			long repaintEveryBytes, int zoom, double cncResolution){	
		resolution = cncResolution;
		this.repaintEveryBytes = repaintEveryBytes;
		this.zoom = zoom;
		bGenerator = bytesGenerator;	
		bricks = buildingBricks;
		setLayout(null);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Hobby cnc");
		setAlwaysOnTop(true);
		setLocationByPlatform(true);		  
		getAccessibleContext().setAccessibleName("Hobbycnc");
		
		this.setBounds(0, 0, 769, 333);
		{
			jScrollPaneXY = new JScrollPane();
			getContentPane().add(jScrollPaneXY);
			jScrollPaneXY.setBounds(7, 11, 381, 253);
			{
				jPanelXY = new JPanel(){					
					@Override
					public void paint(Graphics g) {
						super.paint(g);
						g.setColor(Color.black);
						drawXYCoordinates(g);
						g.setColor(Color.red);
						drawHeadXY(g);
						g.setColor(Color.blue);
						DrawPointsXY(g);						
					}
				};
				jScrollPaneXY.setViewportView(jPanelXY);
				jPanelXY.setBounds(334, 98, 349, 330);
				jPanelXY.setPreferredSize(new java.awt.Dimension(600, 600));
			}
		}

		{
			jScrollPaneYZ = new JScrollPane();
			getContentPane().add(jScrollPaneYZ);
			jScrollPaneYZ.setBounds(396, 11, 355, 253);
			{
				jPanelYZ = new JPanel(){
					@Override
					public void paint(Graphics g) {    	
						super.paint(g);
						g.setColor(Color.black);
						drawYZCoordinates(g);
						g.setColor(Color.red);
						drawHeadYZ(g);
						g.setColor(Color.blue);
						DrawPointsYZ(g);						
					}
				};
				jScrollPaneYZ.setViewportView(jPanelYZ);
				jPanelYZ.setBounds(73, 248, 413, 370);
				jPanelYZ.setPreferredSize(new java.awt.Dimension(600, 600));
			}
		}
		{
			jButtonPause = new JButton();
			getContentPane().add(jButtonPause);
			jButtonPause.setText("Pause");
			jButtonPause.setBounds(12, 273, 74, 21);
			jButtonPause.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					jButtonPauseMouseReleased(evt);
				}
			});
		}
	}
		
	private void drawXYCoordinates(Graphics g){		
		g.drawLine(theGap, theGap, coordLenght, theGap);			
		g.drawLine(coordLenght-3, theGap-3, coordLenght, theGap);	
		g.drawLine(coordLenght-3, theGap+3, coordLenght, theGap);
		
		g.drawLine(theGap, theGap, theGap, coordLenght);
		g.drawLine(theGap-3, coordLenght-3, theGap, coordLenght);
		g.drawLine(theGap+3, coordLenght-3, theGap, coordLenght);
		
		g.drawString("X", coordLenght+5, theGap+5);
		g.drawString("Y", theGap+5, coordLenght+5);
	}
	
	private void drawYZCoordinates(Graphics g){	
		g.drawLine(theGap, theGap, coordLenght, theGap);			
		g.drawLine(coordLenght-3, theGap-3, coordLenght, theGap);	
		g.drawLine(coordLenght-3, theGap+3, coordLenght, theGap);
		
		g.drawLine(theGap, theGap, theGap, coordLenght);
		g.drawLine(theGap-3, coordLenght-3, theGap, coordLenght);
		g.drawLine(theGap+3, coordLenght-3, theGap, coordLenght);
		
		g.drawString("Y", coordLenght+5, theGap+5);
		g.drawString("Z", theGap+5, coordLenght+5);
	}
	
	private void DrawPointsXY(Graphics g)	{	
		if(bricks != null){
			synchronized(bricks) {
				for(Brick b : bricks) {
					int x = (int) (b.getX()*resolution*zoom) + theGap;
					int y = (int) (b.getY()*resolution*zoom)+ theGap;
					g.drawLine(x, y, x, y);	
				}
			}
		}
	}
	
	private void DrawPointsYZ(Graphics g)	{		
		if(bricks != null){
			synchronized(bricks) {
				for(Brick b : bricks) {
					int y = (int) (b.getY()*resolution*zoom) + theGap;
					int z = (int) (b.getZ()*resolution*zoom)+ theGap;
					g.drawLine(y, z, y, z);	
				}
			}
		}
	}
	
	private void drawHeadXY(Graphics g){
		if(currHeadPosition != null)
		{
			int y = (int) (currHeadPosition.getX()*resolution*zoom) + theGap;
			int z = (int) (currHeadPosition.getY()*resolution*zoom)+ theGap;
			g.drawRect(y-1, z-1, 3, 3);
		}
	}
	
	private void drawHeadYZ(Graphics g){
		if(currHeadPosition != null)
		{
			int x = (int) (currHeadPosition.getY()*resolution*zoom) + theGap;
			int y = (int) (currHeadPosition.getZ()*resolution*zoom)+ theGap;
			g.drawRect(x-1, y-1, 3, 3);
		}
	}
	
	public void onMachineHeadMoved(DoublePoint3D newPosition) {
		currHeadPosition = newPosition;
		wasTact++;
		if(repaintEveryBytes > -1 && wasTact > repaintEveryBytes){
			repaint();
			wasTact = 0;
		}
	}
	
	private void jButtonPauseMouseReleased(MouseEvent evt) {		
		/*bGenerator.setPause(!bGenerator.isPause());
		
		if(bGenerator.isPause())
			jButtonPause.setText("Continue");
		else
			jButtonPause.setText("Pause");*/
	}

}
