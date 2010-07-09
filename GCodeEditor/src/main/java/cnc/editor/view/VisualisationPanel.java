package cnc.editor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
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
import cnc.editor.FiguresContainer;
import cnc.editor.domain.figure.FPoint;
import cnc.editor.listener.VisualisationPanelListener;

public class VisualisationPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private EditorStates es = EditorStates.getInstance();
	private Image image;
	private int theGap = es.getGap();
	private Image source;
	
	public VisualisationPanel(VisualisationPanelListener  ml){
		
		addMouseListener(ml);
		addMouseMotionListener(ml);
		setBackground(Color.white);
		setLayout(null);
		
		try {
			source = ImageIO.read(new File("Plata_ok_tr.bmp"));
			scaleImage();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public void scaleImage(){
		
		ImageFilter replicate = new ReplicateScaleFilter((int)(source.getWidth(this) *0.45 * es.getScale()) , (int)(source.getHeight(this)*0.45 * es.getScale()));
		ImageProducer prod = new FilteredImageSource(source.getSource(),replicate);
		
		image = createImage(prod);
	}
	
	public void drawUnderlayer(GraphicsWrapper gw) {			
		gw.drawImage(image, es.getGap(), es.getGap());
	}

	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		
		GraphicsWrapper gw = new GraphicsWrapper(g, this);
		
		if(es.isDrawFacilities()){			
			drawUnderlayer(gw);			
			drawGrid(gw);
			drawStrictBorders(gw);	
		}		
	
		drawCoordinates(gw);		
		
		drawPicture(gw);
		
		if(es.isDrawFacilities()){
			//drawMousePosition(gw);	
		}
		
		drawSelectedRegion(gw);
		
		for(Component c : getComponents()){
			c.repaint();
		}		
	}
	
	private void drawMousePosition(GraphicsWrapper gw) {
		
		int viewHeight = (int)getSize().getHeight();
		int viewWidth = (int)getSize().getWidth();
		
		int x1 = es.getMousePosition().x;
		int y1 = es.getMousePosition().y;

		gw.setColor(Color.LIGHT_GRAY);
		
	    Graphics2D g2 = (Graphics2D)gw.getG();
		float thickness = 1.0f;	
		Stroke s = new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[]{4f}, 3f);
		Stroke olds = g2.getStroke();
		g2.setStroke(s);	
		
		gw.drawLine(x1, theGap, x1, viewHeight);
		gw.drawLine(theGap, y1, viewWidth, y1);
		g2.setStroke(s);	
		
	}

	private void drawSelectedRegion(GraphicsWrapper gw) {
		
		Color color = gw.getColor();
		gw.setColor(Color.ORANGE);
		cnc.editor.SelectedRegion sr = es.getSelRegion();	
		
	    Graphics2D g2 = (Graphics2D)gw.getG();
		float thickness = 2.0f;	
		Stroke s = new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[]{4f}, 3f);
		Stroke olds = g2.getStroke();
		g2.setStroke(s);	
		
		gw.drawLine(sr.getStartX(), sr.getStartY(), sr.getStartX(), sr.getEndY());
		gw.drawLine(sr.getStartX(), sr.getStartY(), sr.getEndX(), sr.getStartY());
		gw.drawLine(sr.getEndX(), sr.getStartY(), sr.getEndX(), sr.getEndY());
		gw.drawLine(sr.getStartX(), sr.getEndY(), sr.getEndX(), sr.getEndY());
		
		gw.setColor(color);		
		g2.setStroke(olds);
	}

	public void drawStrictBorders(GraphicsWrapper g){
		
		Color color = g.getColor();
	    g.setColor(Color.red);
	    
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

		double gridSteps = Math.round((es.getGridStep() / es.getScale()*10))/10;
	
		if(gridSteps < 1){
			gridSteps = 1;
		}
		
/*		if(es.getScale() >= 10){
			gridSteps = 0.5;
		}
		
		if(es.getScale() >= 50){
			gridSteps = 0.1;
		}*/
		
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

			g.setColor(Color.LIGHT_GRAY);
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
			
			g.setColor(Color.LIGHT_GRAY);
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
		
		FiguresContainer gcc = FiguresContainer.getInstance();				
			
		for(FPoint gc : gcc.getAllPointsList()){			
				
			int newX = (int)EditorStates.convertPositionCnc_View(gc.getX()); 
			int newY = (int)EditorStates.convertPositionCnc_View(gc.getY()); 				

			double panelWidth = this.getSize().getWidth();
			double panelHeight = this.getSize().getHeight();	
			
			if(panelWidth < newX + 100){
				this.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 100, (int)panelHeight));
				this.revalidate();
			} 
			
			if(panelHeight < newY + 100){	
				this.setPreferredSize(new Dimension((int)panelWidth, (int)Math.max(newY, panelHeight) + 100));
				this.revalidate();
			}	
		}
		
		gcc.draw(g);	

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
		g.drawString("Y", gap + 3, es.getViewCoordLenghtY() + 4);
		
		g.setColor(color);
	}
	
	public int getViewY(double editorY){
		double panelHeight = this.getSize().getHeight();
		return 	(int)(panelHeight - editorY);		
	}
	
	public int getEditorY(double viewY){
		double panelHeight = this.getSize().getHeight();
		return 	(int)(panelHeight - viewY);		
	}

}