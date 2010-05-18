package cnc.editor.math;

public class EquationOfCircle {

	double top;
	double left;		
	double diametr;	
	double angelGamma;	
	double angelPhy;	
	
	double angelAlpha;	
	double angelBeta;
	
	public double Rx1;
	public double Ry1;
	public double Rx2;
	public double Ry2;
	
	
	public EquationOfCircle(double a, double b, double c, double d, double radius){

		double chord = Math.sqrt(Math.pow(a-c, 2) + Math.pow(b-d, 2));
	
		double cosAlpha = chord / (2 * radius);
		double sinAlpha = Math.sqrt(1 - Math.pow(cosAlpha, 2));
		
		double sinBetta = (c - a) / chord;
		double cosBetta = Math.sqrt(1 - Math.pow(sinBetta, 2));
			
		double sinGamma = sinAlpha * cosBetta - cosAlpha * sinBetta;
		double cosGamma = Math.sqrt(1 - Math.pow(sinGamma, 2));
		
		double P = cosGamma * radius;
		double N = Math.sqrt(Math.pow(radius, 2) - Math.pow(P, 2));
		
	
		
/*		top = Y0 - radius;
		left = X0 - radius;		
		
		diametr = 2 * radius;
		
		angelGamma = (Math.asin((d - Y0) / radius) * 180) / Math.PI;
		
		angelPhy = (Math.acos((a - X0) / radius) * 180) / Math.PI;	*/	
		
		MPoint q = new MPoint(), w = new MPoint(),e = new MPoint();
		
		GetCirclesIntersect(a,b,radius, c,d, radius, q,w,e);
		
		
		//double X0 = w.x;		
		//double Y0 = w.y;
		
		//top = Y0 - radius;
		//left = X0 - radius;		
		
		//diametr = 2 * radius;
		
		//angelGamma = (Math.acos((a - X0) / radius) * 180) / Math.PI;
		
		//angelPhy = (Math.acos((c - X0) / radius) * 180) / Math.PI;	
		Rx1 = q.x;
		Ry1 = q.y;
		Rx2 = w.x;
		Rx1 = w.y;

		
	}

	
	private class MPoint
	{
	  double  x, y;
	};

	boolean GetCirclesIntersect(double X1, double Y1, double R1, double X2, double Y2, double R2, MPoint P1, MPoint P2, MPoint Near1)
	{

	  double  C1 = R1 * R1 - R2 * R2 + Y2 * Y2 - (X2 - X1) * (X2 - X1) - Y1 * Y1, 
	         C2 = X2 - X1,                    
	         C3 = Y2 - Y1,                                                 
	         a  = -4 * C2 * C2 - 4 * C3 * C3,
	         b  = 8 * C2 * C2 * Y2 + 4 * C1 * C3,
	         c  = 4 * C2 * C2 * R2 * R2 - 4 * C2 * C2 * Y2 * Y2 - C1 * C1,
	         X_1_1 = 0, X_1_2 = 0, Y_1 = 0, X_2_1 = 0, X_2_2 = 0, Y_2 = 0,
	         Leng1, Leng2;

	  double  sqrtVal = b * b - 4 * a * c;
	  if ( sqrtVal < 0  ||  a == 0 )
	  {
	    if ( X1 == X2  &&  Y1 == Y2  &&  R1 == R2 )  
	    {
	      P1.x = X1 - R1;
	      P1.y = Y1;
	      P2.x = X1 + R1;
	      P2.y = Y1;
	      return true;
	    }
	    return false;
	  }

	  Y_1 = (-b + Math.sqrt(sqrtVal)) / (2 * a);
	  Y_2 = (-b - Math.sqrt(sqrtVal)) / (2 * a);
	  X_1_1 = X1 + Math.sqrt(R1 * R1 - (Y_1 - Y1) * (Y_1 - Y1));
	  X_1_2 = X1 - Math.sqrt(R1 * R1 - (Y_1 - Y1) * (Y_1 - Y1));
	  X_2_1 = X2 + Math.sqrt(R2 * R2 - (Y_2 - Y2) * (Y_2 - Y2));
	  X_2_2 = X2 - Math.sqrt(R2 * R2 - (Y_2 - Y2) * (Y_2 - Y2));

	  P1.y = Y_1;
	  P2.y = Y_2;

	  Leng1 = Math.sqrt((Near1.x - X_1_1) * (Near1.x - X_1_1) + (Near1.y - Y_1) * (Near1.y - Y_1));
	  Leng2 = Math.sqrt((Near1.x - X_1_2) * (Near1.x - X_1_2) + (Near1.y - Y_1) * (Near1.y - Y_1));
	  if ( Leng1 < Leng2 )
	    P1.x = X_1_1;
	  else
	    P1.x = X_1_2;
	  Leng1 = Math.sqrt((Near1.x - X_2_1) * (Near1.x - X_2_1) + (Near1.y - Y_2) * (Near1.y - Y_2));
	  Leng2 = Math.sqrt((Near1.x - X_2_2) * (Near1.x - X_2_2) + (Near1.y - Y_2) * (Near1.y - Y_2));
	  if ( Leng1 < Leng2 )
	    P2.x = X_2_1;
	  else
	    P2.x = X_2_2;

	  return true;
	}
	
	

	public double getTop() {
		return top;
	}
	
	public double getLeft() {
		return left;
	}

	public double getDiametr() {
		return diametr;
	}

	public double getAngelGamma() {
		return angelGamma;
	}
	
	public double getAngelPhy() {
		return angelPhy;
	}		
}
