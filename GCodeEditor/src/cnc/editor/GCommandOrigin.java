package cnc.editor;

import java.awt.Graphics;

import cnc.editor.Editor.GcommandTypes;


public class GCommandOrigin extends GCommand{

	public GCommandOrigin() {
		super(0f, 0f, 2f);
	}

	@Override
	public GcommandTypes getCommandType() {
		return GcommandTypes.ORIGIN;
	}

	@Override
	public void drawLine(Graphics g) {
		//System.out.println("Origin - nothing to do.");
	}
	
	//Origin can't be moved
	@Override
	public void setX(Float x){
		
	}
	@Override
	public void setY(Float y){
		
	}
	@Override
	public void setZ(Float z){
		
	}
}
