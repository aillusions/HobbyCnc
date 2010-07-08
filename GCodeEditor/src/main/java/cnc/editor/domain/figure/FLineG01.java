package cnc.editor.domain.figure;

import cnc.editor.Editor.GcommandTypes;

public class FLineG01 extends FLineG00{
	
	public FLineG01(FPoint from, FPoint to) {		
		super(from, to);
	}
	
	@Override
	public GcommandTypes getLineType() {
		return GcommandTypes.G01;
	}

}
