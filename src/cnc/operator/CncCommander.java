package cnc.operator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import cnc.operator.model.BigDecimalPoint3D;
import cnc.operator.model.BigDecimalPoint3D.CoordNames;
import cnc.operator.model.LongPoint3D;
import cnc.operator.CncByteSignalGenerator.ShiftValues;

public class CncCommander {

	BigDecimal bigDecimalZero = new BigDecimal(0);
	
	private BigDecimal scale; // mms in one step
	private CncByteSignalGenerator cncByteSignalGenerator;
	
	private BigDecimalPoint3D currPosition;
	private BigDecimalPoint3D motionFromPositoin;
	private BigDecimalPoint3D motionToPositoin;
	
	private LongPoint3D currDiscretePosition;
	
	private boolean lastXDirection = true; //forvard;
	private boolean lastYDirection = true; //forvard;
	private boolean lastZDirection = true; //forvard;

	public CncCommander(String pScale, CncByteSignalGenerator pCncByteSignalGenerator) {
		scale = new BigDecimal(pScale);
		currPosition = new BigDecimalPoint3D();
		motionFromPositoin = new BigDecimalPoint3D();
		motionToPositoin = new BigDecimalPoint3D();
		currDiscretePosition = new LongPoint3D();
		cncByteSignalGenerator = pCncByteSignalGenerator;
	}

	public void goTo(BigDecimalPoint3D newPosition) {
		motionToPositoin = newPosition;
		
		BigDecimal shiftX = bigDecimalZero;
		if(motionToPositoin.getX() != null)
			shiftX = (motionToPositoin.getX().subtract(currPosition.getX())).abs();
		
		BigDecimal shiftY = bigDecimalZero;
		if(motionToPositoin.getY() != null)
			shiftY = (motionToPositoin.getY().subtract(currPosition.getY())).abs();
		
		BigDecimal shiftZ = bigDecimalZero;
		if(motionToPositoin.getZ() != null)
			shiftZ = (motionToPositoin.getZ().subtract(currPosition.getZ())).abs();
		
		BigDecimal maxShift = shiftX.max(shiftY.max(shiftZ));
		
		if(!maxShift.equals(new BigDecimal(0))){		
			
			if(shiftX == maxShift){				
				treatShift(CoordNames.X, CoordNames.Y, CoordNames.Z, shiftY, shiftZ);			
			}else if(shiftY == maxShift){
				treatShift(CoordNames.Y, CoordNames.X, CoordNames.Z, shiftX, shiftZ);
			}else if(shiftZ == maxShift){
				treatShift(CoordNames.Z, CoordNames.X, CoordNames.Y, shiftX, shiftY);
			}
		}
		motionFromPositoin = currPosition.clone();
	}	
	
	public void doOperation(){
		cncByteSignalGenerator.doOperation();
	}

	private void treatShift(CoordNames maxShiftCoord, CoordNames otherCoord1, CoordNames otherCoord2,
			BigDecimal shiftBy1, BigDecimal shiftBy2){
		
		EquationOfLine line1 = null;
		EquationOfLine line2 = null;
		
		if(!motionFromPositoin.equals(motionToPositoin)) {
			
			if(shiftBy1.compareTo(bigDecimalZero) == 1)	{
					
				BigDecimal ordinate1 = motionFromPositoin.get(otherCoord1);
				BigDecimal abscissa1 = motionFromPositoin.get(maxShiftCoord);
				
				BigDecimal ordinate2 = motionToPositoin.get(otherCoord1);
				BigDecimal abscissa2 = motionToPositoin.get(maxShiftCoord);			
				
				line1 = new EquationOfLine(abscissa1, ordinate1, abscissa2, ordinate2);
			}
		
			if(shiftBy2.compareTo(bigDecimalZero) == 1)	{
				
				BigDecimal ordinate1 = motionFromPositoin.get(otherCoord2);
				BigDecimal abscissa1 = motionFromPositoin.get(maxShiftCoord);
				
				BigDecimal ordinate2 = motionToPositoin.get(otherCoord2);
				BigDecimal abscissa2 = motionToPositoin.get(maxShiftCoord);			
				
				line2 = new EquationOfLine(abscissa1, ordinate1, abscissa2, ordinate2);
			}
		}
		
		BigDecimal  mainShiftCoordEnd = motionToPositoin.get(maxShiftCoord);
		
		BigDecimal currA = currPosition.get(maxShiftCoord);
		BigDecimal currB = currPosition.get(otherCoord1);
		BigDecimal currC = currPosition.get(otherCoord2);
		
		
		while( (currA.subtract(mainShiftCoordEnd)).abs().compareTo(scale) == 1 
				|| (currA.subtract(mainShiftCoordEnd)).abs().compareTo(scale) == 0 ) {			
			if(currA.compareTo(mainShiftCoordEnd) == -1)
				currA = currA.add(scale).setScale(10);	
			else
				currA = currA.subtract(scale).setScale(10); 					
			
			if(shiftBy1.compareTo(bigDecimalZero) == 1){						
				currB = line1.getFunction(currA);
			}
			
			if(shiftBy2.compareTo(bigDecimalZero) == 1){				
				currC = line2.getFunction(currA);
			}	
			
			currPosition.set(maxShiftCoord, currA);
			currPosition.set(otherCoord1, currB);
			currPosition.set(otherCoord2, currC);
			
			//Config.sleep();
			doOneStep();
			Config.sleep();
		}

	}
	
	private void doOneStep(){
		
		long calculatedDiscretteX = currPosition.getX().divide(scale, 0, RoundingMode.DOWN).longValue();
		long calculatedDiscretteY = currPosition.getY().divide(scale, 0, RoundingMode.DOWN).longValue();
		long calculatedDiscretteZ = currPosition.getZ().divide(scale, 0, RoundingMode.DOWN).longValue();
		
		int calculatedDiscretteShiftX = (int)(calculatedDiscretteX - currDiscretePosition.getX()); 
		int calculatedDiscretteShiftY = (int)(calculatedDiscretteY - currDiscretePosition.getY()); 
		int calculatedDiscretteShiftZ = (int)(calculatedDiscretteZ - currDiscretePosition.getZ()); 
		
		ShiftValues discretteShiftValueX = ShiftValues.get(calculatedDiscretteShiftX);
		ShiftValues discretteShiftValueY = ShiftValues.get(calculatedDiscretteShiftY);
		ShiftValues discretteShiftValueZ = ShiftValues.get(calculatedDiscretteShiftZ);
		
		cncByteSignalGenerator.shiftToNewPosition(discretteShiftValueX, discretteShiftValueY, discretteShiftValueZ);
		
		currDiscretePosition.setX(calculatedDiscretteX);
		currDiscretePosition.setY(calculatedDiscretteY);
		currDiscretePosition.setZ(calculatedDiscretteZ);
	}
}
