package cnc.operator;

import cnc.operator.storage.IDataStorage;
import cnc.parser.Vertex;

public class BmpFilePrinter {
	
	private GCodeInterpreter gCodeInterpreter;
	private IDataStorage store; 
	
	private Vertex originOfCoordinates;
	private Vertex prevVertex;
	private Vertex currVertex;
	private int scale = 5;
			
	public BmpFilePrinter(GCodeInterpreter gCodeInterpreter) {
		originOfCoordinates = new Vertex(0, 0, 0, 0);
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

				moveTo(currVertex.getX(), currVertex.getY(), null);
				liftDown();
				drawPoint();
			}		
			else{
				moveTo(currVertex.getX(), currVertex.getY(), null);
				drawPoint();
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

		gCodeInterpreter.acceptCommand("G00" + xString + yString + zString);
	}

	private void drawPoint(){
		gCodeInterpreter.getCncCommander().doOperation();
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
