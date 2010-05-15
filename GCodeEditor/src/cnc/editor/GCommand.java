package cnc.editor;


public class GCommand {
	
	public enum GcommandTypes{G00, G01, G02}	
	
	private GcommandTypes GcommandType;
	private EditorVertex vertex;
	private long editorLineIndex;
	
	public GCommand(GcommandTypes gcommandType, EditorVertex vertex) {
		super();
		GcommandType = gcommandType;
		this.vertex = vertex;
	}
	
	public long getEditorLineIndex() {
		return editorLineIndex;
	}
	
	public void setEditorLineIndex(long editorLinuNumber) {
		this.editorLineIndex = editorLinuNumber;
	}

	public GcommandTypes getGcommandType() {
		return GcommandType;
	}
	
	public void setGcommandType(GcommandTypes gcommandType) {
		GcommandType = gcommandType;
	}
	
	public EditorVertex getVertex() {
		return vertex;
	}
	
	public void setVertex(EditorVertex coord) {
		this.vertex = coord;
	}

	@Override
	public String toString() {
		return GcommandType + " X" + vertex.getX() + " Y" +  vertex.getY();
	}
	
	
}
