package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.EditorStates.SelectedRegion;
import cnc.editor.listener.VisualisationPanelListener;

public class VisualisationPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private EditorStates es = EditorStates.getInstance();
	
	public VisualisationPanel(VisualisationPanelListener  ml){
		addMouseListener(ml);
		addMouseMotionListener(ml);
		setLayout(null);
	}

	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		
		drawGrid(g);
		drawStrictBorders(g);		
		drawCoordinates(g);		
		drawSelectedRegion(g);
		drawPicture(g);		
	}
	
	private void drawSelectedRegion(Graphics g) {
		
		Color color = g.getColor();
		g.setColor(Color.ORANGE);
		SelectedRegion sr = es.getSelRegion();		
		drawLine(g, sr.getStartX(), sr.getStartY(), sr.getStartX(), sr.getEndY());
		drawLine(g, sr.getStartX(), sr.getStartY(), sr.getEndX(), sr.getStartY());
		drawLine(g, sr.getEndX(), sr.getStartY(), sr.getEndX(), sr.getEndY());
		drawLine(g, sr.getStartX(), sr.getEndY(), sr.getEndX(), sr.getEndY());
		g.setColor(color);
	}

	public void drawStrictBorders(Graphics g){
		
		Color color = g.getColor();
	    g.setColor(Color.gray);
	    
	    int maxCncX = (int)EditorStates.convertPositionCnc_View(es.getMaxCncX());
	    int maxCncY = (int)EditorStates.convertPositionCnc_View(es.getMaxCncY());
	    
	    // vertical
	    int x1 = maxCncX;
	    int y1 = es.getGap();
	    int x2 = x1;
	    int y2 = maxCncY;
	    
	    drawLine(g, x1, y1, x2, y2);
	    
	    // horizontal
	    x1 = es.getGap();
	    y1 = maxCncY;
	    x2 = maxCncX;
	    y2 = y1;
	    
	    drawLine(g, x1, y1, x2, y2);
		g.setColor(color);
	}
	
	public void drawGrid(Graphics g){

		int theGap = es.getGap();
		double gridSteps = Math.round((es.getGridStep() / es.getScale()*10))/10;
	
		if(gridSteps < 1){
			gridSteps = 1;
		}
		
		if(es.getScale() >= 10){
			gridSteps = 0.5;
		}
		
		if(es.getScale() >= 50){
			gridSteps = 0.1;
		}
		
		long viewHeight = (long)getSize().getHeight();
		long viewWidth = (long)getSize().getWidth();
		
		double height = EditorStates.convertPositionView_Cnc(viewHeight);
		double width = EditorStates.convertPositionView_Cnc(viewWidth);
		
		Color color = g.getColor();	   
	    
		//vertical
	    float progress = 0;		
	    
		while(progress < width){
			
			int x1 = (int)EditorStates.convertPositionCnc_View(progress);
			int y1 = 0 + theGap;

			g.setColor(Color.white);
			drawLine(g, x1, y1, x1, (int)viewWidth);
			
			if(progress > 0){
				DecimalFormat format = new DecimalFormat("###.#");
				g.setColor(Color.gray);
				drawString(g, format.format(progress), x1 - 6, theGap - 14);	
			}
			progress += gridSteps;
		}
		
		//horizontal
		progress = 0;			
		while(progress < height){
			
			int x1 = 0 + theGap;
			int y1 = (int)EditorStates.convertPositionCnc_View(progress);
			
			g.setColor(Color.white);
			drawLine(g, x1, y1, (int)viewWidth, y1);
			
			if(progress > 0){
				DecimalFormat format = new DecimalFormat("###.#");
				g.setColor(Color.gray);
				drawString(g, format.format(progress), theGap - 23, y1 - 5);	
			}
			progress += gridSteps;
			
		}
		g.setColor(color);
	}

	private void drawPicture(Graphics g) {
		
		GCommandsContainer gcc = GCommandsContainer.getInstance();
		
		if(gcc.getGCommandList().size() == 1){
			
			setPreferredSize(new Dimension(1, 1));
			revalidate();
			
		}else{
			
			for(GCommand gc : gcc.getGCommandList()){			
					
				int newX = (int)EditorStates.convertPositionCnc_View(gc.getX()); 
				int newY = (int)EditorStates.convertPositionCnc_View(gc.getY()); 				
	
				double panelWidth = this.getSize().getWidth();
				double panelHeight = this.getSize().getHeight();	
				
				if(panelWidth < newX + 50 || panelHeight < newY + 50){	
					this.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 50, (int)Math.max(newY, panelHeight) + 50));
					this.revalidate();
				}
			
				gc.draw(g, this);			
			}
		}
	}
	
	private void drawCoordinates(Graphics g) {
		
		Color color = g.getColor();
	    g.setColor(Color.gray);
	  
	    int gap = es.getGap();
		drawLine(g, gap, gap, es.getViewCoordLenghtX(), gap);
		
		drawLine(g, es.getViewCoordLenghtX() - 3, gap - 3, es.getViewCoordLenghtX(), gap);
		drawLine(g, es.getViewCoordLenghtX() - 3, gap + 3, es.getViewCoordLenghtX(), gap);

		drawLine(g, gap, gap, gap, es.getViewCoordLenghtY());
		
		drawLine(g, gap - 3, es.getViewCoordLenghtY() - 3, gap, es.getViewCoordLenghtY());
		drawLine(g, gap + 3, es.getViewCoordLenghtY() - 3, gap, es.getViewCoordLenghtY());

		drawString(g, "X", es.getViewCoordLenghtX() + 5, gap + 5);
		drawString(g, "Y", gap -3, es.getViewCoordLenghtY() + 15);
		
		g.setColor(color);
	}
	
	public void drawLine(Graphics g, int x, int y, int endX, int endY){
		g.drawLine(x, getViewY(y), endX, getViewY(endY));
	}
	
	public void drawString(Graphics g, String s, int x, int y){
		g.drawString(s, x, getViewY(y));
	}
	
	public void drawBullet(Graphics g, int i, int j, int pointSize) {
		g.fillOval((int)(i-pointSize/2), getViewY(j+pointSize/2), pointSize, pointSize);
	}

	public void drawArc(Graphics g, int viewLeft, int viewTop, int viewSide, int startAngle, int arcAngle) {
		g.drawArc(viewLeft, getViewY(viewTop + viewSide), viewSide, viewSide, -startAngle, -arcAngle);
	}
	
	public int getViewY(double realY){
		double panelHeight = this.getSize().getHeight();
		return 	(int)(panelHeight - realY);		
	}
	
	public int getRealY(double viewY){
		double panelHeight = this.getSize().getHeight();
		return 	(int)(panelHeight - viewY);		
	}

}