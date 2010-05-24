package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.ReplicateScaleFilter;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.GCommandsContainer;
import cnc.editor.EditorStates.SelectedRegion;
import cnc.editor.listener.VisualisationPanelListener;

public class VisualisationPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private EditorStates es = EditorStates.getInstance();
	private Image image;
	
	public VisualisationPanel(VisualisationPanelListener  ml){
		
		addMouseListener(ml);
		addMouseMotionListener(ml);
		setLayout(null);
		
		try {
			Image source = ImageIO.read(new File("d:\\cat.bmp"));
			ImageFilter replicate = new ReplicateScaleFilter(source.getWidth(this), source.getHeight(this));
			ImageProducer prod = new FilteredImageSource(source.getSource(),replicate);
			
			image = createImage(prod);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public void drawUnderlayer(GraphicsWrapper g) {	
		
	    g.drawImage(image, es.getGap(), es.getGap());

	}


	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		
		GraphicsWrapper gw = new GraphicsWrapper(g, this);
		
		drawUnderlayer(gw);
		
		drawGrid(gw);
		drawStrictBorders(gw);		
		drawCoordinates(gw);		
		drawSelectedRegion(gw);
		drawPicture(gw);		
	}
	
	private void drawSelectedRegion(GraphicsWrapper g) {
		
		Color color = g.getColor();
		g.setColor(Color.ORANGE);
		SelectedRegion sr = es.getSelRegion();		
		g.drawLine(sr.getStartX(), sr.getStartY(), sr.getStartX(), sr.getEndY());
		g.drawLine(sr.getStartX(), sr.getStartY(), sr.getEndX(), sr.getStartY());
		g.drawLine(sr.getEndX(), sr.getStartY(), sr.getEndX(), sr.getEndY());
		g.drawLine(sr.getStartX(), sr.getEndY(), sr.getEndX(), sr.getEndY());
		g.setColor(color);
	}

	public void drawStrictBorders(GraphicsWrapper g){
		
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
	
	public void drawGrid(GraphicsWrapper g){

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
				g.drawString(format.format(progress), x1 - 6, theGap - 14);	
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
				g.drawString(format.format(progress), theGap - 23, y1 - 5);	
			}
			progress += gridSteps;
			
		}
		g.setColor(color);
	}

	private void drawPicture(GraphicsWrapper g) {
		
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
			
				gc.draw(g);			
			}
		}
	}
	
	private void drawCoordinates(GraphicsWrapper g) {
		
		Color color = g.getColor();
	    g.setColor(Color.gray);
	  
	    int gap = es.getGap();
		g.drawLine(gap, gap, es.getViewCoordLenghtX(), gap);
		
		g.drawLine(es.getViewCoordLenghtX() - 3, gap - 3, es.getViewCoordLenghtX(), gap);
		g.drawLine(es.getViewCoordLenghtX() - 3, gap + 3, es.getViewCoordLenghtX(), gap);

		g.drawLine(gap, gap, gap, es.getViewCoordLenghtY());
		
		g.drawLine(gap - 3, es.getViewCoordLenghtY() - 3, gap, es.getViewCoordLenghtY());
		g.drawLine(gap + 3, es.getViewCoordLenghtY() - 3, gap, es.getViewCoordLenghtY());

		g.drawString("X", es.getViewCoordLenghtX() + 5, gap + 5);
		g.drawString("Y", gap -3, es.getViewCoordLenghtY() + 15);
		
		g.setColor(color);
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