package cnc.editor;

public class Line {
	private Vertex a;
	private Vertex b;
	public Vertex getA() {
		return a;
	}
	public void setA(Vertex a) {
		this.a = a;
	}
	public Vertex getB() {
		return b;
	}
	public void setB(Vertex b) {
		this.b = b;
	}
	public Line(Vertex a, Vertex b) {
		super();
		this.a = a;
		this.b = b;
	}
}
