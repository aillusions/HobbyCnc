package cnc.editor;


public class GCommand {
	
	public enum GcommandTypes{G00, G01, G02}
	
	private GcommandTypes GcommandType;
	private EditorVertex coord;
	
	public GCommand(GcommandTypes gcommandType, EditorVertex coord) {
		super();
		GcommandType = gcommandType;
		this.coord = coord;
	}
	public GcommandTypes getGcommandType() {
		return GcommandType;
	}
	public void setGcommandType(GcommandTypes gcommandType) {
		GcommandType = gcommandType;
	}
	public EditorVertex getCoord() {
		return coord;
	}
	public void setCoord(EditorVertex coord) {
		this.coord = coord;
	}
	

}
