package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import cnc.editor.EditorVertex;
import cnc.editor.VertexesContainer;
import cnc.editor.listener.VisualPanelListener;

public class VisualisationPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public VisualisationPanel(VisualPanelListener  ml){
		addMouseListener(ml);
		addMouseMotionListener(ml);
		setLayout(null);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		drawCoordinates(g);			
		DrawPicture(g);
	}

	private void DrawPicture(Graphics g) {
		
		EditorVertex prevPos = null;
		
		if(VertexesContainer.getInstance().getVertexList().size() == 1){
			setPreferredSize(new Dimension(1, 1));
			revalidate();
		}else{
			for(EditorVertex v : VertexesContainer.getInstance().getVertexList()){
				
				if(prevPos != null){
					
					if(prevPos.getZ() <= 0 && v.getZ() <= 0){
					 	g.setColor(Color.red);
					}else{
						g.setColor(Color.blue);
					}
					
					EditorStates es = EditorStates.getInstance();
					
					int prevX = Math.round(prevPos.getX() * 5 * es.getScale());
					int prevY = Math.round(prevPos.getY() * 5 * es.getScale());
					
					int newX = Math.round(v.getX() * 5 * es.getScale());
					int newY = Math.round(v.getY() * 5 * es.getScale());				
		
					double panelWidth = this.getSize().getWidth();
					double panelHeight = this.getSize().getHeight();	
					
					if(panelWidth < newX + 50 || panelHeight < newY + 50){	
						this.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 300, (int)Math.max(newY, panelHeight) + 300));
						this.revalidate();
					}
					
					g.drawLine(prevX, prevY, newX, newY);
					prevPos = v;
					
				}else{
					prevPos = v;
				}
			}
		}
	}

	private void drawCoordinates(Graphics g) {
		EditorStates es = EditorStates.getInstance();
		g.drawLine(es.getGap(), es.getGap(), es.getCoordLength(), es.getGap());
		g.drawLine(es.getCoordLength() - 3, es.getGap() - 3, es.getCoordLength(), es.getGap());
		g.drawLine(es.getCoordLength() - 3, es.getGap() + 3, es.getCoordLength(), es.getGap());

		g.drawLine(es.getGap(), es.getGap(), es.getGap(), es.getCoordLength());
		g.drawLine(es.getGap() - 3, es.getCoordLength() - 3, es.getGap(), es.getCoordLength());
		g.drawLine(es.getGap() + 3, es.getCoordLength() - 3, es.getGap(), es.getCoordLength());

		g.drawString("X", es.getCoordLength() + 5, es.getGap() + 5);
		g.drawString("Y", es.getGap() + 5, es.getCoordLength() + 5);
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println();
	}

}