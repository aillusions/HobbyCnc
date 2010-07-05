package cnc.parser.bmp;

import cnc.GCodeAcceptor;
import cnc.parser.bmp.Point2D;
import cnc.storage.memory.IDataStorage;

public class BmpFilePrinter {

	private GCodeAcceptor gCodeInterpreter;
	private IDataStorage store;	
	private Point2D previousPoint;
	private Point2D currentPoint;
	
	private Integer lastX; 
	private Integer lastY; 
	private Integer lastZ; 
	
	private Integer lastShiftX; 
	private Integer lastShiftY; 
	private Integer lastShiftZ; 
	
	private boolean lastWasSkipped = false;

			
	public BmpFilePrinter(GCodeAcceptor gCodeInterpreter) {
		this.gCodeInterpreter = gCodeInterpreter;
	}	
	
	public void startBuild() {	
		
		previousPoint = null;
		currentPoint = null;
		
		while((currentPoint = store.getNextVertex()) != null ) {	

			if(previousPoint == null){
				
				liftUp();
				moveToIfNecessarily(currentPoint.getX(), currentPoint.getY(), null);
				liftDown();
				
			}else{		
				
				long shiftPCX = Math.abs(currentPoint.getX() - previousPoint.getX());
				long shiftPCY = Math.abs(currentPoint.getY() - previousPoint.getY());
				
				if(shiftPCX > 2 || shiftPCY > 2) {	
					liftUp();
					moveToIfNecessarily(currentPoint.getX(), currentPoint.getY(), null);
					liftDown();
				}
				else{				
					moveToIfNecessarily(currentPoint.getX(), currentPoint.getY(), null);
				}
			}
			
			previousPoint = currentPoint;			
			store.removeVertex(currentPoint);
		}
		liftUp();
		//moveTo(0, 0, null);
		
	}
	
	public void setStore(IDataStorage store) {
		this.store = store;
	}
		
	private boolean isCoordinateInSameDirection(Integer newValue, Integer lastValue, Integer lastShift){
		
		boolean result = false;
		
		if(lastValue != null){
			
			if(newValue != null){
				
				int shift = newValue - lastValue;
				
				if(lastShift != null && shift == lastShift){
					result = true;
				}
			}
		}else if(newValue == null){
			result = true;
		}
			
		return result;
	}
		
	private void moveToIfNecessarily(Integer x, Integer y, Integer z){		
		
		boolean skipCurrent = false;
		
		if(isSameDirection(x,y,z)){		
			
			skipCurrent = true;	
			
		}else{
			
			skipCurrent = false;
			
			if(lastWasSkipped){
				notifyMovement(lastX, lastY, lastZ);
			}	
			
			notifyMovement(x,y,z);
		}

		lastShiftX = calculateShift(x, lastX);
		lastShiftY = calculateShift(y, lastY);
		lastShiftZ = calculateShift(z, lastZ);
		
		lastX = x;
		lastY = y;
		lastZ = z;
		
		lastWasSkipped = skipCurrent;

	}
	
	private Integer calculateShift(Integer newVal, Integer lastVal){
		
		if(newVal == null){
			return 0;
		}else if(lastVal == null){
			return newVal;
		}else{
			return newVal - lastVal;
		}
	}
	
	private boolean isSameDirection(Integer x, Integer y, Integer z){
		
		return isCoordinateInSameDirection(x, lastX, lastShiftX) 
				&& isCoordinateInSameDirection(y, lastY, lastShiftY) 
				&& isCoordinateInSameDirection(z, lastZ, lastShiftZ);
	}
	
	private void notifyMovement(Integer x, Integer y, Integer z){
		
		String xString = "";
		String yString = "";
		String zString = "";
		
		if(x!= null){
			xString = " X" + x/5d;
		}
		
		if(y!= null){
			yString = " Y" + y/5d;
		}
		
		if(z!= null){
			zString = " Z" + z/5d;
		}
		
		gCodeInterpreter.putGCode("G00" + xString + yString + zString);
	}
	
	private void liftUp(){
		moveToIfNecessarily(null, null, 10);
	}
	
	private void liftDown(){
		moveToIfNecessarily(null, null, 0);
	}
}
