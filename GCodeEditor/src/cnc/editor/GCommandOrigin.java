package cnc.editor;

import java.awt.Graphics;

import cnc.editor.domain.GCommand.GcommandTypes;


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
}
