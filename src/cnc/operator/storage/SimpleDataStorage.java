package cnc.operator.storage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import cnc.HiberUtil;
import cnc.parser.Line;
import cnc.parser.Vertex;

public class SimpleDataStorage implements IDataStorage, Iterable<Line> {


	public void addLine(Line l) {
		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(l);
		session.getTransaction().commit();
	}

	public void addLine(int id, int a, int b) {
		addLine(new Line(id, getVertex(a), getVertex(b)));
	}

	public void addLine(int id, String a, String b) {
		addLine(new Line(id, getVertex(Integer.parseInt(a)), getVertex(Integer
				.parseInt(b))));
	}

	public void addLine(int id, Vertex a, Vertex b) {
		addLine(new Line(id, a, b));
	}

	public void addVertex(Vertex v) {
		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(v);
		session.getTransaction().commit();
	}

	public void saveVertex(Vertex v) {
		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(v);
		session.getTransaction().commit();
	}

	public void addVertex(String id, String x, String y, String z) {
		addVertex(new Vertex(Integer.parseInt(id), Double.parseDouble(x),
				Double.parseDouble(y), Double.parseDouble(z)));
	}

	public void clearStorage() {
		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.createSQLQuery("delete from lines").executeUpdate();
		session.createSQLQuery("delete from vertexes").executeUpdate();
		session.getTransaction().commit();
	}


	public Line getLine(int id) {
		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Line res = null;
		res = (Line) session.get(Line.class, id);
		session.getTransaction().commit();
		return res;
	}

	public Vertex getVertex(int id) {
		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Vertex res = null;
		res = (Vertex) session.get(Vertex.class, id);
		session.getTransaction().commit();
		return res;
	}
	
	public Vertex getVertexNear(Vertex v, boolean used) {
		Session session = HiberUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Vertex res = null;
		if (v == null) {
			Object buff = session
					.createSQLQuery(
							"select id from (select id, min(abs(x-:valX)) + min(abs(y-:valY)) as distance  from vertexes "
									+ "where used = false group by id order by 2 limit 1) as SUBQ")
					.setDouble("valX", 0).setDouble("valY", 0).uniqueResult();
			if (buff != null)
				res = (Vertex) session.get(Vertex.class, Integer.parseInt(buff
						.toString()));
		} else {
			res = (Vertex) session.createQuery(
					"from Vertex where USED = false AND x =" + (v.getX() + 1)
							+ " AND y = " + v.getY()).uniqueResult();
			if (res == null)
			{
			res = (Vertex) session.createQuery(
					"from Vertex where USED = false AND x =" + (v.getX() - 1)
							+ " AND y = " + v.getY()).uniqueResult();
			}
			
			if (res == null)
			{
			res = (Vertex) session.createQuery(
					"from Vertex where USED = false AND x =" + (v.getX())
							+ " AND y = " + (v.getY()+1)).uniqueResult();
			}
			if (res == null)
			{
			res = (Vertex) session.createQuery(
					"from Vertex where USED = false AND x =" + (v.getX())
							+ " AND y = " + (v.getY()-1)).uniqueResult();
			}
			
			if (res == null) {
				Object buff = session
						.createSQLQuery(
								"select id from (select id, min(abs(x-:valX)) + min(abs(y-:valY)) as distance  from vertexes "
										+ "where used = false group by id order by 2 limit 1) as SUBQ")
						.setDouble("valX", v.getX())
						.setDouble("valY", v.getY()).uniqueResult();
				
				if (buff != null)
					res = (Vertex) session.get(Vertex.class, Integer
							.parseInt(buff.toString()));
			}
		}
		session.getTransaction().commit();
		return res;
	}

	public Iterator<Line> iterator() {
		Iterator<Line> res = new DataStorageIterator();
		return res;
	}

	public Iterator<Vertex> vertexIterator() {
		Iterator<Vertex> res = new VertexIterator();
		return res;
	}

	public static class DataStorageIterator implements Iterator<Line> {
		int currId = -1;
		SimpleDataStorage ds = new SimpleDataStorage();

		public boolean hasNext() {
			return (ds.getLine(currId + 1) != null);
		}

		public Line next() {
			return ds.getLine(++currId);
		}

		public void remove() {
			throw new RuntimeException("Was not implemented!");
		}
	}

	public static class VertexIterator implements Iterator<Vertex> {
		int currId = -1;
		SimpleDataStorage ds = new SimpleDataStorage();

		public boolean hasNext() {
			return (ds.getVertex(currId + 1) != null);
		}

		public Vertex next() {
			return ds.getVertex(++currId);
		}

		public void remove() {
			throw new RuntimeException("Was not implemented!");
		}
	}

	public Vertex getNextVertex() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Vertex> getVertexesNear(Vertex v, boolean used) {
		// TODO Auto-generated method stub
		return null;
	}
}
