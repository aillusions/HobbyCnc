package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class SmoothDragView extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private int X = 30, Y = 30;

	public SmoothDragView() {
		addMouseMotionListener(this);
	}

	public void mouseMoved(MouseEvent event) {
		
	}

	public void mouseDragged(MouseEvent event) {
		
		double x = event.getPoint().getX();
		double y = event.getPoint().getY();
		if(Math.abs(x-X) < 15 && Math.abs(y-Y) < 15){
			X = (int) event.getPoint().getX();
			Y = (int) event.getPoint().getY();
			repaint();
		}
	}

	public void paint(Graphics g) {
		
		Dimension dim = getSize();
		g.setColor(getBackground());
		g.fillRect(0, 0, dim.width, dim.height);
		int size = 20;
		g.setColor(Color.pink);
		g.fillOval(X - size / 2, Y - size / 2, size, size);
	}
}