package cnc.storage.database;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.List;

import cnc.parser.ParserVertex;


public class VertexBatch {

	List<ParserVertex> loadedVertexes;

	public List<ParserVertex> getLoadedVertexes() {
		return loadedVertexes;
	}

	public void setLoadedVertexes(List<ParserVertex> loadedVertexes) {
		this.loadedVertexes = loadedVertexes;
	}

	public ParserVertex getNearestUNUSEDVertex(ParserVertex baseVertex) {
		ParserVertex res = null;
		if (loadedVertexes != null) {
			if (baseVertex == null) {
				for (ParserVertex v : loadedVertexes) {
					if (!v.isUsed()) {
						res = v;
						break;
					}
				}
			} else {
	
				double minDisatance = 1000000;
	
				double temp = 0;
				for (ParserVertex v : loadedVertexes) {
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

	public static double getDistance(ParserVertex firstVertex, ParserVertex secondVertex) {
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
