package cnc.operator.model;

import java.text.DecimalFormat;
import java.util.Locale;

public class DoublePoint3D {

	public enum CoordNames {
		X, Y, Z
	};

	protected double x;
	protected double y;
	protected double z;

	public double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setNewPosition(double byX, double byY, double byZ) {

		x = byX;
		y = byY;
		z = byZ;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	public double get(CoordNames name) {

		switch (name) {
		case X:
			return x;
		case Y:
			return y;
		case Z:
			return z;
		}
		return 0;
	}

	public void set(CoordNames name, double value) {
		switch (name) {
		case X:
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

	public DoublePoint3D() {
		super();
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public DoublePoint3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		DecimalFormat myFormater = (DecimalFormat) DecimalFormat
				.getInstance(Locale.ENGLISH);
		return String.format("(%s,%s,%s)", myFormater.format(x), myFormater
				.format(y), myFormater.format(z));
	}

	@Override
	public boolean equals(Object arg0) {
		boolean res = false;
		if (arg0 instanceof DoublePoint3D) {
			DoublePoint3D temp = (DoublePoint3D) arg0;
			if ((this.x == temp.getX()) && (this.y == temp.getY())
					&& (this.z == temp.getZ()))
				res = true;
		}
		return res;
	}

}
