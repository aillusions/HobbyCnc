package cnc.operator.storage;

import java.io.BufferedWriter;
import java.util.Iterator;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import cnc.HiberUtil;
import cnc.operator.Config;
import cnc.parser.Line;
import cnc.parser.Rectangle;
import cnc.parser.Vertex;


public class PromptDataStorage implements IDataStorage {

	BufferedWriter tempVertexWriter;
	Session session;

	private Rectangle mainRec;

	public PromptDataStorage() {
		session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
	}

	public void addLine(Line l) {
	}

	public void addLine(int id, String a, String b) {
	}

	public Line getLine(int id) {
		return null;
	}

	public void addVertex(String id, String x, String y, String z) {
		addVertex(new Vertex(Integer.parseInt(id), Double.parseDouble(x),
				Double.parseDouble(y), Double.parseDouble(z)));
	}

	public void clearStorage() {
		session.createSQLQuery("delete from lines").executeUpdate();
		session.createSQLQuery("delete from vertexes").executeUpdate();
	}

	public Vertex getVertex(int id) {
		return null;
	}

	public Iterator<Line> iterator() {
		return null;
	}

	private void prepare() {
		if (mainRec == null) {

			Vertex topPoint = (Vertex) session
					.createQuery(
							"from Vertex v where v.x  = (select min(vv.x) from Vertex vv) ")
					.list().get(0);
			Vertex rightPoint = (Vertex) session
					.createQuery(
							"from Vertex v where  v.y = (select max(vv.y) from Vertex vv)")
					.list().get(0);

			Vertex leftPoint = (Vertex) session
					.createQuery(
							"from Vertex v where v.y = (select min(vv.y) from Vertex vv) ")
					.list().get(0);
			Vertex bottomPoint = (Vertex) session
					.createQuery(
							"from Vertex v where v.x = (select max(vv.x) from Vertex vv) ")
					.list().get(0);

			mainRec = new Rectangle(new Vertex(0, topPoint.getX(), leftPoint
					.getY(), 0), new Vertex(0, bottomPoint.getX(), rightPoint
					.getY(), 0), true);
		}
	}

	public Vertex getVertexNear(Vertex v, boolean used) {
		Vertex res = null;
		prepare();

		if (v == null)
			v = mainRec.getTopLeftCorner();
		Rectangle searchRec = mainRec.getRectanglePointFrom(v);

		ScrollableResults sr = session.createQuery(
				"select v.id, min(abs(v.x-:valX)) + min(abs(v.y-:valY)) from "
						+ " Vertex v where v.used = false "
						+ " and v.x >= :leftX and v.x <= :rightX "
						+ " and v.y >= :topY  and v.y <= :bottomY "
						+ " group by v.id order by 2 asc)").setDouble("valX",
				v.getX()).setDouble("valY", v.getY()).setDouble("leftX",
				searchRec.getTopLeftCorner().getX()).setDouble("topY",
				searchRec.getTopLeftCorner().getY()).setDouble("rightX",
				searchRec.getBottomRightCorner().getX()).setDouble("bottomY",
				searchRec.getBottomRightCorner().getY()).scroll(
				ScrollMode.FORWARD_ONLY);

		if (sr.next()) {
			res = (Vertex) session.get(Vertex.class, (Integer) sr.get(0));
		} else {
			int shift = Config.trickyStorageRectangleSideLength;
			sr = session.createQuery(
					"select v.id, min(abs(v.x-:valX)) + min(abs(v.y-:valY)) from "
							+ " Vertex v where v.used = false "
							+ " and v.x >= :leftX and v.x <= :rightX "
							+ " and v.y >= :topY  and v.y <= :bottomY "
							+ " group by v.id order by 2 asc)").setDouble(
					"valX", v.getX()).setDouble("valY", v.getY()).setDouble(
					"leftX", searchRec.getTopLeftCorner().getX()-shift).setDouble(
					"topY", searchRec.getTopLeftCorner().getY()-shift).setDouble(
					"rightX", searchRec.getBottomRightCorner().getX()+shift)
					.setDouble("bottomY",
							searchRec.getBottomRightCorner().getY()+shift).scroll(
							ScrollMode.FORWARD_ONLY);
			if (sr.next()) {
				res = (Vertex) session.get(Vertex.class, (Integer) sr.get(0));
			} else {
				sr = session.createQuery(
						"select v.id, min(abs(v.x-:valX)) + min(abs(v.y-:valY)) from "
								+ " Vertex v where v.used = false "
								+ " group by v.id order by 2 asc)").setDouble(
						"valX", v.getX()).setDouble("valY", v.getY()).scroll(
						ScrollMode.FORWARD_ONLY);
				if (sr.next()) {
					res = (Vertex) session.get(Vertex.class, (Integer) sr
							.get(0));
				}
			}
		}

		return res;
	}

	public void saveVertex(Vertex v) {
		// session.flush();
	}

	public void addVertex(Vertex v) {
		session.save(v);
	}

	public Vertex getNextVertex() {
		return null;
	}

	public List<Vertex> getVertexesNear(Vertex v, boolean used) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getVertexQty() {
		// TODO Auto-generated method stub
		return 0;
	}

}
