package cnc.editor.domain;


public class GCommand {
	
	public enum GcommandTypes{G00, G01, G02}
	
	private GcommandTypes GcommandType;
	private BigDecimalPoint3D coord;
	
	public GCommand(GcommandTypes gcommandType, BigDecimalPoint3D coord) {
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
	public BigDecimalPoint3D getCoord() {
		return coord;
	}
	public void setCoord(BigDecimalPoint3D coord) {
		this.coord = coord;
	}
	

}
