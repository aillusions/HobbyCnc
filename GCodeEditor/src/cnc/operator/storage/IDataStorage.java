package cnc.operator.storage;

import java.util.Iterator;
import java.util.List;

import cnc.parser.Line;
import cnc.parser.Vertex;



public interface IDataStorage {
	void addVertex(Vertex v);
	void addVertex(String id, String x, String y, String z );
	void addLine(Line l);
	void addLine(int id, String a, String b);
	void clearStorage();
	Vertex getVertex(int id);
	Line getLine(int id);
	Iterator<Line> iterator();
	List<Vertex> getVertexesNear(Vertex v, boolean used);
	Vertex getNextVertex();
	void saveVertex(Vertex v);
	long getVertexQty();
}
