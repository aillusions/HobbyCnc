package cnc.editor;

import cnc.editor.Editor.GcommandTypes;

public class GCommandG03 extends GCommandG02 {

	public GCommandG03(Float x, Float y, Float z, Float radius) {
		super(x, y, z, radius);
		this.signum = Math.signum(-radius);
	}
	
	public GCommandG03(Float x, Float y, Float z, Float i, Float j) {
		super(x, y, z, i, j);
	}
	
	@Override
	public GcommandTypes getCommandType() {
		return GcommandTypes.G03;
	}	

}
