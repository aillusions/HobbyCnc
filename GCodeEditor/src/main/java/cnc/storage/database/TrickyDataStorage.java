package cnc.storage.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import cnc.HiberUtil;
import cnc.parser.Line;
import cnc.parser.Rectangle;
import cnc.parser.ParserVertex;


public class TrickyDataStorage implements IDataStorage {

	Session session;
	private Rectangle mainRec;

	Iterator<Rectangle> rectIterator;
	ParserVertex lastReturnedVertex = null;
	VertexBatch vertexBatch;
	
	double maxX=-1, minX=-1, maxY = -1, minY=-1;
	
	public TrickyDataStorage() {
		session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
	}
	
	public void addLine(Line l) {
		// TODO Auto-generated method stub

	}

	public void addLine(int id, String a, String b) {
		// TODO Auto-generated method stub

	}

	public void addVertex(ParserVertex v) {
		if(maxX == -1 && minX == -1 && maxY == -1 && minY == -1)
		{
			maxX = v.getX(); minX = v.getX(); maxY = v.getY() ; minY = v.getY() ;
		}			
		if(maxX < v.getX())
			maxX = v.getX(); 
		if(minX > v.getX())
			minX = v.getX();
		if(maxY < v.getY())
			maxY = v.getY();
		if(minY > v.getY() )
			minY = v.getY();
		session.save(v);
	}



	public void addVertex(String id, String x, String y, String z) {
		addVertex(new ParserVertex(Integer.parseInt(id), Double.parseDouble(x),
				Double.parseDouble(y), Double.parseDouble(z)));

	}

	public void clearStorage() {
		session.createSQLQuery("delete from lines").executeUpdate();
		session.createSQLQuery("delete from vertexes").executeUpdate();
	}

	public Line getLine(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ParserVertex getVertex(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ParserVertex> getVertexesNear(ParserVertex v, boolean used) {

		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ArrayList <ParserVertex> res = new ArrayList<ParserVertex>();

		if (v != null) {
			String qStr = "from Vertex where used = true and x >= :left and x <= :right and y >= :top and y <= :bottom";
			Query q = session.createQuery(qStr);
			q = q.setDouble("left", v.getX() - 1);
			q = q.setDouble("right", v.getX() + 1);
			q = q.setDouble("top", v.getY() - 1);
			q = q.setDouble("bottom", v.getY() + 1);
			ScrollableResults sr = q.scroll(ScrollMode.FORWARD_ONLY);
			ParserVertex currV = null;
			while(sr.next()){
				currV = (ParserVertex)sr.get(0);
				if(currV != v){
					res.add(currV);
				}
			}
		}
		return res;
	}

	public Iterator<Line> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveVertex(ParserVertex v) {

	}
	@SuppressWarnings(value = "unchecked")
	public ParserVertex getNextVertex() {
		if (mainRec == null) {
			mainRec = new Rectangle(new ParserVertex(0, minX, minY, 0), new ParserVertex(0, maxX, maxY, 0), true);
			
			rectIterator = mainRec.iterator();
			if (rectIterator.hasNext())
			{
				Rectangle currentSubRec = rectIterator.next();
				vertexBatch = new VertexBatch();
				vertexBatch.setLoadedVertexes( session.createQuery(
						"from Vertex v where v.used = false "
								+ " and v.x >= :leftX and v.x <= :rightX "
								+ " and v.y >= :topY  and v.y <= :bottomY "
								+ " order by v.x, v.y asc)").setDouble("leftX",
						currentSubRec.getTopLeftCorner().getX()).setDouble("topY",
						currentSubRec.getTopLeftCorner().getY()).setDouble("rightX",
						currentSubRec.getBottomRightCorner().getX()).setDouble(
						"bottomY", currentSubRec.getBottomRightCorner().getY()).list());
			}
		}

		lastReturnedVertex = vertexBatch.getNearestUNUSEDVertex(lastReturnedVertex);
		if(lastReturnedVertex == null)
			if (rectIterator.hasNext())
			{
				Rectangle currentSubRec = rectIterator.next();
				vertexBatch.setLoadedVertexes( session.createQuery(
						"from Vertex v where v.used = false "
								+ " and v.x >= :leftX and v.x <= :rightX "
								+ " and v.y >= :topY  and v.y <= :bottomY "
								+ " order by v.x, v.y asc)").setDouble("leftX",
						currentSubRec.getTopLeftCorner().getX()).setDouble("topY",
						currentSubRec.getTopLeftCorner().getY()).setDouble("rightX",
						currentSubRec.getBottomRightCorner().getX()).setDouble(
						"bottomY", currentSubRec.getBottomRightCorner().getY()).list());
				lastReturnedVertex = getNextVertex(); 
			}
			else
			{
				lastReturnedVertex = null;
			}
		return lastReturnedVertex;
	}

	public long getVertexQty() {
		// TODO Auto-generated method stub
		return 0;
	}

}
