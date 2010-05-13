package cnc.operator.storage.database;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.List;

import cnc.parser.Vertex;


public class VertexBatch {

	List<Vertex> loadedVertexes;

	public List<Vertex> getLoadedVertexes() {
		return loadedVertexes;
	}

	public void setLoadedVertexes(List<Vertex> loadedVertexes) {
		this.loadedVertexes = loadedVertexes;
	}

	public Vertex getNearestUNUSEDVertex(Vertex baseVertex) {
		Vertex res = null;
		if (loadedVertexes != null) {
			if (baseVertex == null) {
				for (Vertex v : loadedVertexes) {
					if (!v.isUsed()) {
						res = v;
						break;
					}
				}
			} else {
	
				double minDisatance = 1000000;
	
				double temp = 0;
				for (Vertex v : loadedVertexes) {
					temp = getDistance(baseVertex, v);
					if (temp < minDisatance && !(v.isUsed())) {						
						minDisatance = temp;
						res = v;
						if(temp == 1)
							break;
					}
				}
			}
		}
		return res;
	}

	public static double getDistance(Vertex firstVertex, Vertex secondVertex) {
		double res = 0;
		if (firstVertex != null && secondVertex != null)
			res = sqrt(pow(firstVertex.getX() - secondVertex.getX(), 2)
					+ pow(firstVertex.getY() - secondVertex.getY(), 2)
					+ pow(firstVertex.getZ() - secondVertex.getZ(), 2));
		else
			throw new RuntimeException("Arguments can not be null!");
		return res;
	}
}
