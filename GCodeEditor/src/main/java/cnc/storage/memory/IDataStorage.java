package cnc.storage.memory;

import cnc.parser.bmp.Point2D;

public interface IDataStorage {
	void addVertex(Point2D v);
	Point2D getNextVertex();
	void removeVertex(Point2D v);
}
