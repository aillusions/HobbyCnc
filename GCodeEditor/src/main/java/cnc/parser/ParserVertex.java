package cnc.parser;


public class ParserVertex {
	private int id;
	private double x;
	private double y;
	private double z;
	private boolean used = false;
	

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public ParserVertex() {
		super();
	}

	public ParserVertex(int id, double x, double y, double z) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ParserVertex(int id, double everyCoordinates, boolean used ) {
		super();
		this.id = id;
		this.x = everyCoordinates;
		this.y = everyCoordinates;
		this.z = everyCoordinates;
		this.used = used;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return String.format(java.util.Locale.US, "%1$f;%2$f;%3$f;",x,y,z);
	}

	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if(arg0 != null && arg0 instanceof ParserVertex)
		{
			ParserVertex inVertex = (ParserVertex) arg0;
			if(id == inVertex.getId() &&  x == inVertex.getX() &&
					y == inVertex.getY() && z == inVertex.getZ() && used == inVertex.isUsed())
				res = true;
		}
		
		return res;
	}
	
	
	

}
