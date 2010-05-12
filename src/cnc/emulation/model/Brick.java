package cnc.emulation.model;

import cnc.operator.model.DoublePoint3D;


/**
 * 
 * Class is minimal element of the geometric 
 * form (Building). 
 * 
 * */
public class Brick extends DoublePoint3D {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Types of a brick
	public enum BrickType{WHITE, BLUE};
	
	private boolean visible = true;
	
	private BrickType type = BrickType.WHITE;
	
	public BrickType getType() {
		return type;
	}
	public void setType(BrickType type) {
		this.type = type;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Brick() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Brick(Double x, Double y, Double z) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
	}
	public Brick(DoublePoint3D coords, boolean visible, BrickType type) {
		super(coords.getX(), coords.getY(), coords.getZ());
		this.visible = visible;
		this.type = type;
	}
	
	public String toString()
	{
		return String.format(super.toString() + ": " + type);
	}
	
}
