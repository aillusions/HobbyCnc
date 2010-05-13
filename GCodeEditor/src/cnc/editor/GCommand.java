package cnc.editor;


public class GCommand {
	
	public enum GcommandTypes{G00, G01, G02}
	
	private GcommandTypes GcommandType;
	private Vertex coord;
	
	public GCommand(GcommandTypes gcommandType, Vertex coord) {
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
	public Vertex getCoord() {
		return coord;
	}
	public void setCoord(Vertex coord) {
		this.coord = coord;
	}
	

}
