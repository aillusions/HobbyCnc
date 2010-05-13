package cnc.editor;


public class Vertex {
	private float x;
	private float y;
	private float z;

	public Vertex() {
		super();
	}

	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return String.format(java.util.Locale.US, "%1$f;%2$f;%3$f;",x,y,z);
	}

	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if(arg0 != null && arg0 instanceof Vertex)
		{
			Vertex inVertex = (Vertex) arg0;
			if(x == inVertex.getX() &&	y == inVertex.getY() && z == inVertex.getZ()){
				res = true;
			}
		}
		
		return res;
	}
	
	
	

}
