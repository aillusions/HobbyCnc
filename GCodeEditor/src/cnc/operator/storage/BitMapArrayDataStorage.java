package cnc.operator.storage;

import java.util.Iterator;
import java.util.List;

import cnc.parser.Line;
import cnc.parser.Vertex;

public class BitMapArrayDataStorage implements IDataStorage {
	
	private char [][] bitMap = new char[1000][1000];
	
	private Vertex lastVertex = null;
	
	private void setNewCurrentVertex(Vertex lastVertex){
		//System.out.println(this.lastVertex + " - " + lastVertex);
		this.lastVertex = lastVertex;
	}
	
	public void addVertex(Vertex vertex){		
		bitMap[(int)vertex.getX()][(int)vertex.getY()] = 1;
	}
	
	public void saveVertex(Vertex vertex){
		bitMap[(int)vertex.getX()][(int)vertex.getY()] = 0;
	}
	
	public Vertex getNextVertex(){
		if(lastVertex == null){
			for(int i = 0; i < 1000; i++){
				for(int j = 0; j < 1000; j++){
					if(bitMap[i][j]==1){
						setNewCurrentVertex(new Vertex(-1,i,j,0));
						return lastVertex;
					}
				}
			}
		}else{
			int lastX = (int)lastVertex.getX();
			int lastY = (int)lastVertex.getX();
			
			for (int a = 1; a < 100; a++) {
				for (int i = lastX - a; i <= lastX + a; i++) {
					for (int j = lastY - a; j <= lastY + a; j++) {
						if (i < 1000 && j < 1000 && i > -1 && j > -1 && bitMap[i][j] == 1) {
							setNewCurrentVertex(new Vertex(-1, i, j, 0));
							return lastVertex;
						}
					}
				}
			}
			
			/*	for(int i = 0; i < 1000; i++){
				for(int j = 0; j < 1000; j++){
					if(((lastX+i) < 1000) && ((lastY+j) < 1000) && bitMap[lastX+i][lastY+j] == 1){
						setNewCurrentVertex(new Vertex(-1,lastX+i,lastY+j,0));
						return lastVertex;
					}else if(((lastX-i) > -1) && ((lastY+j) < 1000) && bitMap[lastX-i][lastY+j] == 1){
						setNewCurrentVertex(new Vertex(-1,lastX-i,lastY+j,0));
						return lastVertex;
					}else if(((lastX+i) < 1000) && ((lastY-j) > -1) && bitMap[lastX+i][lastY-j] == 1){
						setNewCurrentVertex(new Vertex(-1,lastX+i,lastY-j,0));
						return lastVertex;
					}else if(((lastX-i) > -1) && ((lastY-j) > -1) && bitMap[lastX-i][lastY-j] == 1){
						setNewCurrentVertex(new Vertex(-1,lastX-i,lastY-j,0));
						return lastVertex;
					}
				}
			}*/
		
		}
		return null;
	}

	
	
	//----------------------------------------------------------
	//----------------------------------------------------------
	//----------------------------------------------------------
	
	public void addLine(Line l) {
		// TODO Auto-generated method stub
		
	}

	public void addLine(int id, String a, String b) {
		// TODO Auto-generated method stub
		
	}

	public void addVertex(String id, String x, String y, String z) {
		// TODO Auto-generated method stub
		
	}

	public void clearStorage() {
		// TODO Auto-generated method stub
		
	}

	public Line getLine(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vertex getVertex(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getVertexQty() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Vertex> getVertexesNear(Vertex v, boolean used) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Line> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
