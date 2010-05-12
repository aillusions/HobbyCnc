package cnc.operator.model;


/**
 * 
 * Represent spacial point in discrete's coordinate system. 
 * Has a few analogous at whole of project:
 * <ul> 
 * <li>
 * {@link cnc.operator.model.BigDecimalPoint3D} - spacial point
 * in continued coordinate system. Used  by {@link cnc.emulation.VirtuaMachine}.
 * <li>
 * {@link cnc.parser.Vertex} - spacial point in continued 
 * coordinate system. Used to parse input graphics/geometric/GIS data. 
 * </ul>
 * @author Alex
 *
 */
public class LongPoint3D {
    private Long X;
    private Long Y;
    private Long Z;
    
    public LongPoint3D(Long x, Long y, Long z) {
        super();
        X = x;
        Y = y;
        Z = z;
    } 

    @Override
    public String toString()
    {
    	return String.format("(%d,%d,%d)", X,Y,Z);    			
    }

	public LongPoint3D() {        
        super();
        X = 0L;
        Y = 0L;
        Z = 0L;  
    }
              
    public Long getX() {
		return X;
	}
	public void setX(Long x) {
		X = x;
	}
	public Long getY() {
		return Y;
	}
	public void setY(Long y) {
		Y = y;
	}

	public Long getZ() {
		return Z;
	}
	public void setZ(Long z) {
		Z = z;
	}

	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if(arg0 != null && arg0 instanceof LongPoint3D)
		{
			LongPoint3D temp = (LongPoint3D) arg0;
			if((this.X == temp.getX()) && (this.Y == temp.getY()) && (this.Z == temp.getZ()))
				res = true;
		}
		return res;
	}
}
