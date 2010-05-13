package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import cnc.editor.Vertex;
import cnc.editor.VertexesContainer;

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

	private VertexesContainer container;
	
	public VisualisationPanel(VertexesContainer container){
		this.container = container;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		drawCoordinates(g);			
		DrawPicture(g);
	}

	private void DrawPicture(Graphics g) {
		
		Vertex prevPos = null;
		
		for(Vertex v : container.getVertexList()){
			
			if(prevPos != null){
				
				if(prevPos.getZ() <= 0 && v.getZ() <= 0){
				 	g.setColor(Color.red);
				}else{
					g.setColor(Color.blue);
				}
				
				int prevX = Math.round(prevPos.getX() * 5 * scale);
				int prevY = Math.round(prevPos.getY() * 5 * scale);
				int newX = Math.round(v.getX() * 5* scale);
				int newY = Math.round(v.getY() * 5* scale);				
	
				double panelWidth = this.getSize().getWidth();
				double panelHeight = this.getSize().getHeight();	
				
				if(panelWidth < newX || panelHeight < newY){	
					this.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 50, (int)Math.max(newY, panelHeight)+50));
					this.revalidate();
				}
				
				g.drawLine(prevX, prevY, newX, newY);
				prevPos = v;
				
			}else{
				prevPos = v;
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