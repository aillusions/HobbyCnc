package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JPanel;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
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
		//drawSelection(g);
		drawPicture(g);		
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
	    
	    g.drawLine(x1, y1, x2, y2);
	    
	    // horizontal
	    x1 = es.getGap();
	    y1 = maxCncY;
	    x2 = maxCncX;
	    y2 = y1;
	    
	    g.drawLine(x1, y1, x2, y2);
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
			g.drawLine(x1, y1, x1, (int)viewWidth);
			
			if(progress > 0){
				DecimalFormat format = new DecimalFormat("###.#");
				 g.setColor(Color.gray);
				g.drawString(format.format(progress), x1 - 6, theGap - 4);	
			}
			progress += gridSteps;
		}
		
		//horizontal
		progress = 0;			
		while(progress < height){
			
			int x1 = 0 + theGap;
			int y1 = (int)EditorStates.convertPositionCnc_View(progress);
			
			g.setColor(Color.white);
			g.drawLine(x1, y1, (int)viewWidth, y1);
			
			if(progress > 0){
				DecimalFormat format = new DecimalFormat("###.#");
				g.setColor(Color.gray);
				g.drawString(format.format(progress), theGap - 17, y1 + 5);	
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
					this.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 300, (int)Math.max(newY, panelHeight) + 300));
					this.revalidate();
				}
			
				gc.draw(g);			
			}
		}
	}
	
	private void drawCoordinates(Graphics g) {
		
		Color color = g.getColor();
	    g.setColor(Color.gray);
	  
		g.drawLine(es.getGap(), es.getGap(), es.getViewCoordLenghtX(), es.getGap());
		g.drawLine(es.getViewCoordLenghtX() - 3, es.getGap() - 3, es.getViewCoordLenghtX(), es.getGap());
		g.drawLine(es.getViewCoordLenghtX() - 3, es.getGap() + 3, es.getViewCoordLenghtX(), es.getGap());

		g.drawLine(es.getGap(), es.getGap(), es.getGap(), es.getViewCoordLenghtY());
		g.drawLine(es.getGap() - 3, es.getViewCoordLenghtY() - 3, es.getGap(), es.getViewCoordLenghtY());
		g.drawLine(es.getGap() + 3, es.getViewCoordLenghtY() - 3, es.getGap(), es.getViewCoordLenghtY());

		g.drawString("X", es.getViewCoordLenghtX() + 5, es.getGap() + 5);
		g.drawString("Y", es.getGap() -3, es.getViewCoordLenghtY() + 15);
		
		g.setColor(color);
	}
}