package cnc.editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JPanel;

import cnc.editor.EditorStates;
import cnc.editor.EditorVertex;
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
		
		drawStrictBorders(g);
		drawGrid(g);		
		drawCoordinates(g);		
		drawSelection(g);
		drawPicture(g);		
	}
	
	public void drawStrictBorders(Graphics g){
		Color color = g.getColor();
	    g.setColor(Color.ORANGE);
	    
	    int maxCncX = (int)EditorStates.convertCnc_View(es.getMaxCncX());
	    int maxCncY = (int)EditorStates.convertCnc_View(es.getMaxCncY());
	    
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
		
		double height = EditorStates.convertView_Cnc(viewHeight);
		double width = EditorStates.convertView_Cnc(viewWidth);
		
		Color color = g.getColor();	   
	    
		//vertical
	    float progress = 0;				
		while(progress < width){
			
			int x1 = (int)EditorStates.convertCnc_View(progress);
			int y1 = 0 + theGap;

			g.setColor(Color.white);
			g.drawLine(x1, y1, x1, (int)viewWidth);
			
			if(progress > 0){
				DecimalFormat format = new DecimalFormat("###.#");
				 g.setColor(Color.ORANGE);
				g.drawString(format.format(progress), x1 - 6, theGap - 4);	
			}
			progress += gridSteps;
		}
		
		//horizontal
		progress = 0;			
		while(progress < height){
			
			int x1 = 0 + theGap;
			int y1 = (int)EditorStates.convertCnc_View(progress);
			
			g.setColor(Color.white);
			g.drawLine(x1, y1, (int)viewWidth, y1);
			
			if(progress > 0){
				DecimalFormat format = new DecimalFormat("###.#");
				g.setColor(Color.orange);
				g.drawString(format.format(progress), theGap - 17, y1 + 5);	
			}
			progress += gridSteps;
			
		}
		g.setColor(color);
	}
	
	private void drawSelection(Graphics g) {
		
		GCommand selected = es.getSelectedVertex();		
		int size = (int)EditorStates.SELECTIO_CIRCLE_SIZE;
		
		if(selected != null){			
			
			float X = EditorStates.convertCnc_View(selected.getVertex().getX());
			float Y = EditorStates.convertCnc_View(selected.getVertex().getY());			
			
			Color color = g.getColor();
		    g.setColor(Color.pink);
		    g.fillOval((int)(X-size/2), (int)(Y-size/2), size, size);
		    g.setColor(color);
		}
		
		List<GCommand> nearSelection = es.getNearSelectedVertex();
		
		if(nearSelection != null){
			
			float X;
			float Y;
			Color color = g.getColor();
		    g.setColor(Color.gray);
		    
			for(GCommand gc : nearSelection){
				
				X = EditorStates.convertCnc_View(gc.getVertex().getX());
				Y = EditorStates.convertCnc_View(gc.getVertex().getY());
				g.fillOval((int)(X-size/2), (int)(Y-size/2), size, size);
			}			
		    g.setColor(color);
		}
	}

	private void drawPicture(Graphics g) {
		
		EditorVertex prevPos = null;
		GCommandsContainer gcc = GCommandsContainer.getInstance();
		
		if(gcc.getGCommandList().size() == 1){
			setPreferredSize(new Dimension(1, 1));
			revalidate();
		}else{
			for(GCommand v : gcc.getGCommandList()){
				
				if(prevPos != null){
					
					if(prevPos.getZ() <= 0 && v.getVertex().getZ() <= 0){
					 	g.setColor(Color.red);
					}else{
						g.setColor(Color.blue);
					}
					
					int prevX = (int)EditorStates.convertCnc_View(prevPos.getX());
					int prevY = (int)EditorStates.convertCnc_View(prevPos.getY());
					
					int newX = (int)EditorStates.convertCnc_View(v.getVertex().getX()); 
					int newY = (int)EditorStates.convertCnc_View(v.getVertex().getY()); 				
		
					double panelWidth = this.getSize().getWidth();
					double panelHeight = this.getSize().getHeight();	
					
					if(panelWidth < newX + 50 || panelHeight < newY + 50){	
						this.setPreferredSize(new Dimension((int)Math.max(newX, panelWidth) + 300, (int)Math.max(newY, panelHeight) + 300));
						this.revalidate();
					}
					
					g.drawLine(prevX, prevY, newX, newY);
					
					if(EditorStates.getInstance().getScale() >= 5){
						Color color = g.getColor();
						g.setColor(Color.green);
						drawArrowEnd(g, prevPos, v.getVertex());
						g.setColor(color);
					}
					
					prevPos = v.getVertex();
					
				}else{
					prevPos = v.getVertex();
				}
			}
		}
	}
	
	private void drawArrowEnd(Graphics g, EditorVertex ev1, EditorVertex ev2){
		double Ya,Yb,Xa,Xb;
		double k,k1;
		double tgB;
		double CB = 0.08;
		
		Xa = ev1.getX();
		Xb = ev2.getX();
		
		Ya = ev1.getY();
		Yb = ev2.getY();
		
		if(Xa == Xb || Ya == Xb){
			return;
		}
		tgB = Math.tan((30 * Math.PI) / 180);
		k = (Ya-Yb)/(Xa - Xb);		
		
		{
			k1 = (tgB + k) / (1 - tgB * k);
			double a, b, c;
			
			a = 1;
			b = - 2 * Xb;
			c = Xb*Xb-(CB*CB/(k1*k1+1));
			
			double Xc1, Xc2;
			
			if(b*b-4*a*c < 0){
				//return;
			}
			
			Xc1 = (-b+Math.sqrt(b*b-4*a*c))/2*a;
			Xc2 = (-b-Math.sqrt(b*b-4*a*c))/2*a;
			
			double Yc1, Yc2;
			Yc1 = k1*(Xc1-Xb) + Yb;
			Yc2 = k1*(Xc2-Xb) + Yb;
			
			double Xdiff1 = Math.abs(Xa - Xc1);
			double Xdiff2 = Math.abs(Xa - Xc2);
			double Ydiff1 = Math.abs(Ya - Yc1);
			double Ydiff2 = Math.abs(Ya - Yc2);
			
			int startX1,startY1, XbInt,YbInt;
			
			if(Xdiff1 < Xdiff2){
				startX1 = (int)EditorStates.convertCnc_View((float)Xc1);
			}else{
				startX1 = (int)EditorStates.convertCnc_View((float)Xc2);						
			}
			
			if(Ydiff1 < Ydiff2){
				startY1 = (int)EditorStates.convertCnc_View((float)Yc1);
			}else{
				startY1 = (int)EditorStates.convertCnc_View((float)Yc2);
			}
	
			XbInt = (int)EditorStates.convertCnc_View((float)Xb);
			YbInt = (int)EditorStates.convertCnc_View((float)Yb);
					
			g.drawLine(startX1, startY1, XbInt, YbInt);	
		}
		{
			k1 = (tgB - k) / ( tgB * k - 1);
			double a, b, c;
			
			a = 1;
			b = - 2 * Xb;
			c = Xb*Xb-(CB*CB/(k1*k1+1));
			
			double Xc1, Xc2;
			Xc1 = (-b+Math.sqrt(b*b-4*a*c))/2*a;
			Xc2 = (-b-Math.sqrt(b*b-4*a*c))/2*a;
			
			double Yc1, Yc2;
			Yc1 = k1*(Xc1-Xb) + Yb;
			Yc2 = k1*(Xc2-Xb) + Yb;
			
			double Xdiff1 = Math.abs(Xa - Xc1);
			double Xdiff2 = Math.abs(Xa - Xc2);
			double Ydiff1 = Math.abs(Ya - Yc1);
			double Ydiff2 = Math.abs(Ya - Yc2);
			
			int startX1,startY1, endX,endY;
			
			if(Xdiff1 < Xdiff2){
				startX1 = (int)EditorStates.convertCnc_View((float)Xc1);
			}else{
				startX1 = (int)EditorStates.convertCnc_View((float)Xc2);						
			}
			
			if(Ydiff1 < Ydiff2){
				startY1 = (int)EditorStates.convertCnc_View((float)Yc1);
			}else{
				startY1 = (int)EditorStates.convertCnc_View((float)Yc2);
			}
	
			endX = (int)EditorStates.convertCnc_View((float)Xb);
			endY = (int)EditorStates.convertCnc_View((float)Yb);
					
			g.drawLine(startX1, startY1, endX, endY);
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