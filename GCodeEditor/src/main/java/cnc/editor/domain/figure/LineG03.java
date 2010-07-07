package cnc.editor.domain.figure;

import cnc.editor.Editor.GcommandTypes;

public class LineG03 extends LineG02  {

	public LineG03(FPoint from, FPoint to, Float radius) {
		super(from, to, radius);	
		this.clockWise = false;
	}
	
	public LineG03(FPoint from, FPoint to, Float i, Float j) {
		super(from, to, i, j);
		this.clockWise = false;
	}

	@Override
	public GcommandTypes getLineType() {
		return GcommandTypes.G03;
	}
}
