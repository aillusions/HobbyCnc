package cnc.editor;

import cnc.editor.Editor.GcommandTypes;

public class GCommandG01 extends GCommandG00 {
	
	@Override
	public GcommandTypes getCommandType() {
		return GcommandTypes.G01;
	}
	
	public GCommandG01(Float x, Float y, Float z) {
		super(x, y, z);
	}

}
