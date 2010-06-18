package cnc.storage.database;

import java.util.Iterator;
import java.util.List;

import cnc.parser.Line;
import cnc.parser.ParserVertex;



public interface IDataStorage {
	void addVertex(ParserVertex v);
	void addVertex(String id, String x, String y, String z );
	void addLine(Line l);
	void addLine(int id, String a, String b);
	void clearStorage();
	ParserVertex getVertex(int id);
	Line getLine(int id);
	Iterator<Line> iterator();
	List<ParserVertex> getVertexesNear(ParserVertex v, boolean used);
	ParserVertex getNextVertex();
	void saveVertex(ParserVertex v);
	long getVertexQty();
}
