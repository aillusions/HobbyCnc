package cnc.parser;

public class Line {
	private int id;
	private ParserVertex point_a;
	private ParserVertex point_b;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Line(int id, ParserVertex point_a, ParserVertex point_b) {
		super();
		this.id = id;
		this.point_a = point_a;
		this.point_b = point_b;
	}
	public ParserVertex getPoint_a() {
		return point_a;
	}
	public void setPoint_a(ParserVertex point_a) {
		this.point_a = point_a;
	}
	public ParserVertex getPoint_b() {
		return point_b;
	}
	public void setPoint_b(ParserVertex point_b) {
		this.point_b = point_b;
	}
	public Line() {
		super();
	}
	
	@Override
	public String toString() {
		return String.format("%d (%d,%d)", id, point_a.getId(), point_b.getId());
	}

	
}
