package Main;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

public class BigDecimalPoint3D  {
	
	public BigDecimalPoint3D clone(){
		return new BigDecimalPoint3D(x, y, z);
	}	
	
	public BigDecimalPoint3D() {
		this.x = new BigDecimal(0);
		this.y = new BigDecimal(0);
		this.z = new BigDecimal(0);
	}

	public BigDecimalPoint3D(BigDecimal x, BigDecimal y, BigDecimal z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BigDecimalPoint3D(double x, double y, double z){
		this(new BigDecimal(x), new BigDecimal(y), new BigDecimal(z));
	}
	
	public enum CoordNames {X, Y, Z};

	protected BigDecimal x;
	protected BigDecimal y;
	protected BigDecimal z;
	
	public BigDecimal getX() {
		return x;
	}

	public BigDecimal getY() {
		return y;
	}

	public BigDecimal getZ() {
		return z;
	}
	
	public void setNewPosition(BigDecimal byX, BigDecimal byY, BigDecimal byZ){
		
		if(byX != null)
			x = byX;
		
		if(byY != null)
			y = byY;
		
		if(byZ != null)
			z = byZ;
		
	}
	
	public BigDecimal get(CoordNames name){
		
		switch(name){
		case X:
			return x;
		case Y:
			return y;
		case Z:
			return z;
		}
		return null;
	}

	public void set(CoordNames name, BigDecimal value){

		switch(name){
		case X :
			 x = value;
			 break;
		case Y:
			 y = value;
			 break;
		case Z:
			 z = value;
			 break;
		}

	}



	@Override
	public String toString() {		
		DecimalFormat myFormater = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);//new java.text.DecimalFormat("#.########");
		return String.format("(%s,%s,%s)", myFormater.format(x),myFormater.format(y), myFormater.format(z));
	}

	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if(arg0 != null && arg0 instanceof BigDecimalPoint3D)
		{
			BigDecimalPoint3D temp = (BigDecimalPoint3D) arg0;
			if( ( ( temp.getX() == null && this.x == null ) || ( ( temp.getX() != null && this.x != null ) && ( this.x.compareTo(temp.getX()) == 0 ) ) ) &&
				( ( temp.getY() == null && this.y == null ) || ( ( temp.getY() != null && this.y != null ) && ( this.y.compareTo(temp.getY()) == 0 ) ) ) && 
				( ( temp.getZ() == null && this.z == null ) || ( ( temp.getZ() != null && this.z != null ) && ( this.z.compareTo(temp.getZ()) == 0 ) ) ) )
				res = true;
		}
		return res;
	}
	
}
