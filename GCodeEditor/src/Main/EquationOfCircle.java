package Main;

public class EquationOfCircle {

	double top;
	double left;		
	double size;	
	double angelGamma;	
	double angelPhy;	
	
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
		
		double X0 = c - P;		
		double Y0 = d - N;	
		
		top = Y0 - radius;
		left = X0 - radius;		
		
		size = 2 * radius;
		
		angelGamma = 0; //(Math.asin((d - Y0) / radius) * 180) / Math.PI;
		
		angelPhy = 360;//(Math.acos((a - X0) / radius) * 180) / Math.PI;		
	}


	public double getTop() {
		return top;
	}
	public double getLeft() {
		return left;
	}
	public double getSize() {
		return size;
	}
	public double getAngelGamma() {
		return angelGamma;
	}
	public double getAngelPhy() {
		return angelPhy;
	}		
}
