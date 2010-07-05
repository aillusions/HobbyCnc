package cnc.storage.memory;

import cnc.parser.bmp.Point2D;

public class BitMapArrayDataStorage implements IDataStorage {
	
	private char [][] bitMap = new char[1000][1000];
	
	private Point2D lastVertex = null;
	
	public void addVertex(Point2D vertex){		
		bitMap[(int)vertex.getX()][(int)vertex.getY()] = 1;
	}
	
	public void removeVertex(Point2D vertex){
		bitMap[(int)vertex.getX()][(int)vertex.getY()] = 0;
	}
	
	public Point2D getNextVertex(){
		if(lastVertex == null){
			for(int i = 0; i < 1000; i++){
				for(int j = 0; j < 1000; j++){
					if(bitMap[i][j]==1){
						setNewCurrentVertex(new Point2D(i,j));
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
							setNewCurrentVertex(new Point2D(i, j));
							return lastVertex;
						}
					}
				}
			}
		}
		return null;
	}
	
	private void setNewCurrentVertex(Point2D lastVertex){
		this.lastVertex = lastVertex;
	}
	
}
