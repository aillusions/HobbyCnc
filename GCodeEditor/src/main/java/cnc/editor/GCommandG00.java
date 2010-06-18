package cnc.editor;

import java.awt.Color;

import cnc.editor.Editor.GcommandTypes;
import cnc.editor.view.GraphicsWrapper;

public class GCommandG00 extends GCommand{

	public GCommandG00(Float x, Float y, Float z) {
		
		super(x, y, z);
	}

	@Override
	public GcommandTypes getCommandType() {
		
		return GcommandTypes.G00;
	}

	@Override
	public void drawLine(GraphicsWrapper g) {
		
		int prevX = (int)EditorStates.convertPositionCnc_View(previousCmd.getX());
		int prevY = (int)EditorStates.convertPositionCnc_View(previousCmd.getY());
		
		int newX = (int)EditorStates.convertPositionCnc_View(getX()); 
		int newY = (int)EditorStates.convertPositionCnc_View(getY()); 	

		g.drawLine(prevX, prevY, newX, newY);
		
		if(EditorStates.getInstance().getScale() >= 5){
			drawArrowEnd(g);						
		}	
	}
	
	private void drawArrowEnd(GraphicsWrapper g){
		
		Color color = g.getColor();
		g.setColor(Color.green);

		double Ya,Yb,Xa,Xb;
		double k,k1;
		double tgB;
		double CB = 0.08;
		
		Xa = previousCmd.getX();
		Xb = getX();
		
		Ya = previousCmd.getY();
		Yb = getY();
		
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
				startX1 = (int)EditorStates.convertPositionCnc_View((float)Xc1);
			}else{
				startX1 = (int)EditorStates.convertPositionCnc_View((float)Xc2);						
			}
			
			if(Ydiff1 < Ydiff2){
				startY1 = (int)EditorStates.convertPositionCnc_View((float)Yc1);
			}else{
				startY1 = (int)EditorStates.convertPositionCnc_View((float)Yc2);
			}
	
			XbInt = (int)EditorStates.convertPositionCnc_View((float)Xb);
			YbInt = (int)EditorStates.convertPositionCnc_View((float)Yb);
					
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
				startX1 = (int)EditorStates.convertPositionCnc_View((float)Xc1);
			}else{
				startX1 = (int)EditorStates.convertPositionCnc_View((float)Xc2);						
			}
			
			if(Ydiff1 < Ydiff2){
				startY1 = (int)EditorStates.convertPositionCnc_View((float)Yc1);
			}else{
				startY1 = (int)EditorStates.convertPositionCnc_View((float)Yc2);
			}
	
			endX = (int)EditorStates.convertPositionCnc_View((float)Xb);
			endY = (int)EditorStates.convertPositionCnc_View((float)Yb);
					
			g.drawLine(startX1, startY1, endX, endY);
		}
		g.setColor(color);
	}

}
