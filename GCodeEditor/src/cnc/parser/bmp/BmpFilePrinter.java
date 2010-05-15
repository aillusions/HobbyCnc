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

			
	public BmpFilePrinter(GCodeAcceptor gCodeInterpreter) {
		originOfCoordinates = new ParserVertex(0, 0, 0);
		this.gCodeInterpreter = gCodeInterpreter;
	}	
	
	public void StartBuild() {	
		
		prevVertex = originOfCoordinates;
		currVertex = null;
		ParserVertex scipedVertex = null;
		
		while((currVertex = store.getNextVertex()) != null ) {	

			long shiftX = currVertex.getX() - prevVertex.getX();
			long shiftY = currVertex.getY() - prevVertex.getY();
			
			if(shiftX == 0 || shiftY == 0){
				scipedVertex = currVertex;
				currVertex.setUsed(true);
				store.saveVertex(currVertex);				
				continue;
			}else if(scipedVertex != null){
				moveTo(scipedVertex.getX(), scipedVertex.getY(), null);
				scipedVertex = null;
			}

			if( shiftX > 3 || shiftY > 3 ) {					
				liftUp();

				moveTo(currVertex.getX(), currVertex.getY(), null);
				liftDown();
			}		
			else{
				moveTo(currVertex.getX(), currVertex.getY(), null);
			}			
			
			currVertex.setUsed(true);
			prevVertex = currVertex;			
			store.saveVertex(currVertex);
		}
		if(scipedVertex != null){
			moveTo(scipedVertex.getX(), scipedVertex.getY(), null);
			scipedVertex = null;
		}
		
		liftUp();
		moveTo(0L, 0L, null);
		
	}
	
	private void moveTo(Long x, Long y, Long z){
		
		String xString = "";
		String yString = "";
		String zString = "";
		
		if(x!= null){
			xString = " X" + x;
		}
		
		if(y!= null){
			yString = " Y" + y;
		}
		
		if(z!= null){
			zString = " Z" + z;
		}		
		gCodeInterpreter.putGCode("G00" + xString + yString + zString);
	}
	
	private void liftUp(){
		moveTo(null, null, 10L);
	}
	
	private void liftDown(){
		moveTo(null, null, 0L);
	}
	
	public IDataStorage getStore() {
		return store;
	}

	public void setStore(IDataStorage store) {
		this.store = store;
	}	
}
