package cnc.editor.domain.figure;

import cnc.editor.Editor.GcommandTypes;

public class FLineG03 extends FLineG02  {

	public FLineG03(FPoint from, FPoint to, Float radius) {
		super(from, to, radius);	
		this.clockWise = false;
	}
	
	public FLineG03(FPoint from, FPoint to, Float i, Float j) {
		super(from, to, i, j);
		this.clockWise = false;
	}

	@Override
	public GcommandTypes getLineType() {
		return GcommandTypes.G03;
	}
}
