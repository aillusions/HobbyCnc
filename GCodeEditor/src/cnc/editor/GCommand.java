package cnc.editor;



public class GCommand {
	
	public enum GcommandTypes{G00, G01, G02}	
	
	private final GcommandTypes gCommandType;
	private final EditorVertex vertex;
	
	public GCommand(GcommandTypes gCommandType, EditorVertex vertex) {
		this.gCommandType = gCommandType;
		this.vertex = vertex;
		this.vertex.setgCommand(this);
	}
	
	public GcommandTypes getGcommandType() {
		return gCommandType;
	}
	
	public EditorVertex getVertex() {
		return vertex;
	}	

	@Override
	public String toString() {
		return gCommandType + " X" + vertex.getX() + " Y" +  vertex.getY()/*+ " Z" +  vertex.getZ()*/;
	}

}
