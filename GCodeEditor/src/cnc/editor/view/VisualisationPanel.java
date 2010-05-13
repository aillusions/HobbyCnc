package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import cnc.editor.GCodeParser;
import cnc.editor.domain.BigDecimalPoint3D;
import cnc.editor.domain.GCommand;

public class VisualisationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int theGap = 0;
	private int coordLenght = 200;
	private float scale = 1;
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	private JTextArea txtArea_GCodes;
	
	public VisualisationPanel(JTextArea txtArea_GCodes){
		this.txtArea_GCodes = txtArea_GCodes;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		drawCoordinates(g);			
		DrawPicture(g);
	}

	private void DrawPicture(Graphics g) {
		String commands = txtArea_GCodes.getText();
		String[] cmdArray = commands.replace("\r", "").split("\n");
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
				
				double panelWidth = this.getSize().getWidth();
				double panelHeight = this.getSize().getHeight();	
				if(panelWidth < newX || panelHeight < newY){	
					this.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 50, (int)Math.max(newY, panelHeight)+50));
					this.revalidate();
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