package cnc.storage.memory;

import cnc.parser.bmp.ParserVertex;

public interface IDataStorage {
	void addVertex(ParserVertex v);
	ParserVertex getNextVertex();
	void saveVertex(ParserVertex v);
	void clearStorage();
}
