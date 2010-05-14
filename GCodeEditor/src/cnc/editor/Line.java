package cnc.editor;

public class Line {
	private EditorVertex a;
	private EditorVertex b;
	public EditorVertex getA() {
		return a;
	}
	public void setA(EditorVertex a) {
		this.a = a;
	}
	public EditorVertex getB() {
		return b;
	}
	public void setB(EditorVertex b) {
		this.b = b;
	}
	public Line(EditorVertex a, EditorVertex b) {
		super();
		this.a = a;
		this.b = b;
	}
}
