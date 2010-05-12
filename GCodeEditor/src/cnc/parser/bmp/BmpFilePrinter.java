package cnc.parser.bmp;

import cnc.GCodeAcceptor;
import cnc.operator.storage.IDataStorage;
import cnc.parser.Vertex;

public class BmpFilePrinter {
	
	private GCodeAcceptor gCodeInterpreter;
	private IDataStorage store; 
	
	private Vertex originOfCoordinates;
	private Vertex prevVertex;
	private Vertex currVertex;
	private int scale = 5;
	private long qty = 0;
			
	public BmpFilePrinter(GCodeAcceptor gCodeInterpreter, long qty) {
		originOfCoordinates = new Vertex(0, 0, 0, 0);
		this.gCodeInterpreter = gCodeInterpreter;
		this.qty = qty;
	}	
	
	public void StartBuild() {	
		
		prevVertex = originOfCoordinates;
		currVertex = null;
		
		long index = 0;
		long pager = 0;
		while((currVertex = store.getNextVertex()) != null ) {
			if(pager < 100){
				pager ++;
			}else{
				System.out.println(index + " of " + qty );
				pager = 0;
			}
			

			int shiftX = (int)Math.abs(currVertex.getX() - prevVertex.getX());
			int shiftY = (int)Math.abs(currVertex.getY() - prevVertex.getY());			

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
			index ++;		
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
