package cnc.parser.bmp;

public class Point2D {
	
	private int x;
	private int y;

	public Point2D() {
		super();
	}

	public Point2D(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format(java.util.Locale.US, "%1$f;%2$f;",x,y);
	}

	@Override
	public boolean equals(Object arg0) {
		
		if (arg0 != null && arg0 instanceof Point2D) {
			
			Point2D inVertex = (Point2D) arg0;
			
			if (x == inVertex.getX() && y == inVertex.getY()){
				
				return true;
			}
		}

		return false;
	}	

}
