package cnc.storage.memory;

import cnc.parser.bmp.ParserVertex;

public class BitMapArrayDataStorage implements IDataStorage {
	
	private char [][] bitMap = new char[1000][1000];
	
	private ParserVertex lastVertex = null;
	
	private void setNewCurrentVertex(ParserVertex lastVertex){
		this.lastVertex = lastVertex;
	}
	
	public void addVertex(ParserVertex vertex){		
		bitMap[(int)vertex.getX()][(int)vertex.getY()] = 1;
	}
	
	public void saveVertex(ParserVertex vertex){
		bitMap[(int)vertex.getX()][(int)vertex.getY()] = 0;
	}
	
	public ParserVertex getNextVertex(){
		if(lastVertex == null){
			for(int i = 0; i < 1000; i++){
				for(int j = 0; j < 1000; j++){
					if(bitMap[i][j]==1){
						setNewCurrentVertex(new ParserVertex(i,j,0));
						return lastVertex;
					}
				}
			}
		}else{
			int lastX = (int)lastVertex.getX();
			int lastY = (int)lastVertex.getY();
			
			for (int a = 1; a < 1000; a++) {
				for (int i = lastX - a; i <= lastX + a; i++) {
					for (int j = lastY - a; j <= lastY + a; j++) {
						if (i < 1000 && j < 1000 && i > -1 && j > -1 && bitMap[i][j] == 1) {
							setNewCurrentVertex(new ParserVertex(i, j, 0));
							return lastVertex;
						}
					}
				}
			}
		}
		return null;
	}

	
	//----------------------------------------------------------
	//----------------------------------------------------------
	//----------------------------------------------------------
	

	public void clearStorage() {
		// TODO Auto-generated method stub
		
	}

}
