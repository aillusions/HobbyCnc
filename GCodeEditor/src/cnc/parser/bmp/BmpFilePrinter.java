package cnc.parser.bmp;

import cnc.GCodeAcceptor;
import cnc.parser.bmp.ParserVertex;
import cnc.storage.memory.IDataStorage;

public class BmpFilePrinter {
	
	private GCodeAcceptor gCodeInterpreter;
	private IDataStorage store; 
	
	private ParserVertex originOfCoordinates;
	private ParserVertex prevVertex;
	private ParserVertex currVertex;
	private int scale = 5;
			
	public BmpFilePrinter(GCodeAcceptor gCodeInterpreter) {
		originOfCoordinates = new ParserVertex(0, 0, 0);
		this.gCodeInterpreter = gCodeInterpreter;
	}	
	
	public void StartBuild() {	
		
		prevVertex = originOfCoordinates;
		currVertex = null;
		
		while((currVertex = store.getNextVertex()) != null ) {
	

			int shiftX = (int)Math.abs(currVertex.getX() - prevVertex.getX());
			int shiftY = (int)Math.abs(currVertex.getY() - prevVertex.getY());			

			if( shiftX > 3 || shiftY > 3 ) {					
				liftUp();

				moveTo(new Double(currVertex.getX()), new Double(currVertex.getY()), null);
				liftDown();
			}		
			else{
				moveTo(new Double(currVertex.getX()), new Double(currVertex.getY()), null);
			}			
			
			currVertex.setUsed(true);
			prevVertex = currVertex;			
			store.saveVertex(currVertex);
		}
		liftUp();
		moveTo(0d, 0d, null);
		
	}
	
	private void moveTo(Double x, Double y, Double z){
		
		String xString = "";
		String yString = "";
		String zString = "";
		
		if(x!= null){
			xString = " X" + x/scale;
		}
		
		if(y!= null){
			yString = " Y" + y/scale;
		}
		
		if(z!= null){
			zString = " Z" + z/scale;
		}		
		gCodeInterpreter.putGCode("G00" + xString + yString + zString);
	}
	
	private void liftUp(){
		moveTo(null, null, 10d);
	}
	
	private void liftDown(){
		moveTo(null, null, 0d);
	}
	
	public IDataStorage getStore() {
		return store;
	}

	public void setStore(IDataStorage store) {
		this.store = store;
	}	
}
