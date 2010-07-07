package cnc.editor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;

import cnc.editor.EditorStates;


public class GraphicsWrapper{
	
	private Graphics g;
	private VisualisationPanel vp;
	
	public GraphicsWrapper(Graphics g, VisualisationPanel vp){
		this.g = g;
		this.vp = vp;
	}
	
	public Graphics getG() {
		return g;
	}
	
	public void drawLineWithScaleThickness(int x, int y, int endX, int endY){
		
	    Graphics2D g2 = (Graphics2D)g;
		float thickness = EditorStates.getInstance().getScale();	
		Stroke s = new BasicStroke(thickness);
		Stroke old = g2.getStroke();
		g2.setStroke(s);	
		
		g.drawLine(x, vp.getViewY(y), endX, vp.getViewY(endY));

		g2.setStroke(old);
		
		
	}
	
	public void drawLine(int x, int y, int endX, int endY){

		g.drawLine(x, vp.getViewY(y), endX, vp.getViewY(endY));
	
	}
	
	public void drawString(String s, int x, int y){
		g.drawString(s, x, vp.getViewY(y));
	}
	
	public void drawBullet(int i, int j, int pointSize) {
		g.fillOval((int)(i-pointSize/2), vp.getViewY(j+pointSize/2), pointSize, pointSize);
	}

	public void drawArc(int viewLeft, int viewTop, int viewSide, int startAngle, int arcAngle) {
		g.drawArc(viewLeft, vp.getViewY(viewTop + viewSide), viewSide, viewSide, startAngle, arcAngle);
	}
	
	public Color getColor() {
		return g.getColor();
	}

	public void setColor(Color c) {
		g.setColor(c);
		
	}

	public void drawImage(Image image, int i, int j) {
		int height;
		height = image.getHeight(vp);		
		g.drawImage(image, i, vp.getViewY(j + height), vp);
	}
		
}
