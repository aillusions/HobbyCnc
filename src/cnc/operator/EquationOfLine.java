package cnc.operator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * ordinate = a + b * abscissa
 */
public class EquationOfLine {

	private BigDecimal ordinate1; // Y1
	private BigDecimal ordinate2; // Y2
	private BigDecimal abscissa1; // X1
	private BigDecimal abscissa2; // X2
	
	BigDecimal b;	
	BigDecimal a;
	
	/**
	 * 
	 * @param abscissa1 X1
	 * @param ordinate1 Y1
	 * @param abscissa2 X2
	 * @param ordinate2 Y2
	 */
	public EquationOfLine( BigDecimal abscissa1, BigDecimal ordinate1, BigDecimal  abscissa2, BigDecimal ordinate2 ){
		
		this.ordinate1 = ordinate1; // Y1 
		this.ordinate2 = ordinate2; // Y2
		this.abscissa1 = abscissa1; // X1
		this.abscissa2 = abscissa2; // X2
		
		this.b = ordinate1.subtract(ordinate2).divide(abscissa1.subtract(abscissa2), 10, RoundingMode.DOWN);		
		this.a = ordinate1.subtract(b.multiply(abscissa1));		
	}
	
	
	public BigDecimal getFunction(BigDecimal var){
		return a.add(b.multiply(var)).round(new MathContext(10));
	}
}
