package cnc.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Rectangle implements Iterable<Rectangle>  {
	
	/* Black rectangle: 56624 vertexes.
	 * * 100 - Building completed in: 80844 ms.
	 * * 60  - Building completed in: 46407 ms.
	 * * 50  - Building completed in: 41187 ms.
	 * * 40  - Building completed in: 38657 ms./38484 ms
	 * * 35  - Building completed in: 37813 ms./38454 ms.
	 * * 25  - Building completed in: 50297 ms.
	
	*/
	/* Text: 49821 vertexes.
	 * 180 - Building completed in: 54234 ms. 
	 * 140 - Building completed in: 43828 ms.
	 * 120 - Building completed in: 39891 ms. 
	 * 100 - Building completed in: 38766 ms.
	 * 80  - Building completed in: 43063 ms. 
	 * 40  - Building completed in: 96062 ms. 
	 * */
	

	private ParserVertex topLeftCorner = null;
	private ParserVertex bottomRightCorner = null;
	
	private  List<Rectangle> subRectangles;
	
	public List<Rectangle> getSubRectangles() {
		return subRectangles;
	}

	public ParserVertex getTopLeftCorner() {
		return topLeftCorner;
	}
	public void setTopLeftCorner(ParserVertex topLeftCorner) {
		this.topLeftCorner = topLeftCorner;
	}
	public ParserVertex getBottomRightCorner() {
		return bottomRightCorner;
	}
	public void setBottomRightCorner(ParserVertex bottomRightCorner) {
		this.bottomRightCorner = bottomRightCorner;
	}
	public Rectangle(ParserVertex topLeftCorner, ParserVertex bottomRightCorner, boolean splitAtOnce) {
		super();
		this.topLeftCorner = topLeftCorner;
		this.bottomRightCorner = bottomRightCorner;
		if(splitAtOnce)
			subRectangles = splitRectangle(this);
	}
	@Override
	public String toString() {
		
		return topLeftCorner.toString() + "; " + bottomRightCorner;
	} 
	
	
	public List<Rectangle> splitRectangle(Rectangle mainRec)
	{
		List<Rectangle> res = new LinkedList<Rectangle>();
		int tileSize = 100; //Length of the tile sides.
		int stepsX = 0;
		int stepsY = 0;
		int vertCount = -1;
		while((mainRec.getTopLeftCorner().getY() + (++stepsY)*tileSize-1) <= mainRec.getBottomRightCorner().getY())
		{
			stepsX = 0;
			while((mainRec.getTopLeftCorner().getX() + (++stepsX)*tileSize-1) <= mainRec.getBottomRightCorner().getX())
			{				
				Rectangle rect = new Rectangle(new ParserVertex(++vertCount, (mainRec.getTopLeftCorner().getX() + (stepsX-1)*tileSize), (mainRec.getTopLeftCorner().getY() + (stepsY-1)*tileSize), 0 ), 
						new ParserVertex(++vertCount, (mainRec.getTopLeftCorner().getX() + (stepsX)*tileSize-1), (mainRec.getTopLeftCorner().getY() + (stepsY)*tileSize-1), 0 ), false);
				res.add(rect);
			}
		}

		--stepsY;
		--stepsX;
		if((mainRec.getBottomRightCorner().getY() - (stepsY)*tileSize+1) > 0)
		{
			for(int i = 0; i < stepsX; i ++)
			res.add(new Rectangle(new ParserVertex(++vertCount, mainRec.getTopLeftCorner().getX() + i*tileSize, stepsY*tileSize, 0), 
					new ParserVertex(++vertCount, mainRec.getTopLeftCorner().getX() + (i+1)*tileSize, mainRec.getBottomRightCorner().getY(), 0), false));
		}

		if((mainRec.getBottomRightCorner().getX() - (stepsX)*tileSize+1) > 0)
		{
			for(int i = 0; i < stepsY; i ++)
				res.add(new Rectangle(new ParserVertex(++vertCount, stepsX*tileSize, mainRec.getTopLeftCorner().getY() + i*tileSize, 0), 
						new ParserVertex(++vertCount, mainRec.getBottomRightCorner().getX(), mainRec.getTopLeftCorner().getY() + (i+1)*tileSize, 0), false) );

		}
		
		if((mainRec.getBottomRightCorner().getY() - (stepsY)*tileSize+1) > 0 && (mainRec.getBottomRightCorner().getX() - (stepsX)*tileSize+1) > 0)
		{
			res.add(new Rectangle(new ParserVertex(++vertCount, mainRec.getTopLeftCorner().getX() + stepsX*tileSize, mainRec.getTopLeftCorner().getY() + stepsY*tileSize, 0), 
					new ParserVertex(++vertCount, mainRec.getBottomRightCorner().getX(), mainRec.getBottomRightCorner().getY(), 0), false));

		}

		return res;
	}
	
	public Rectangle getRectanglePointFrom(ParserVertex v)
	{
		Rectangle res = null;
		if(subRectangles == null)
		{
			throw new RuntimeException("Rectangle is not splitted!");
		}
		for(Rectangle r : subRectangles )
		{
			if(r.getTopLeftCorner().getX() <= v.getX() &&  r.getTopLeftCorner().getY() <= v.getY())
			{
				if(r.getBottomRightCorner().getX() >= v.getX() &&  r.getBottomRightCorner().getY() >= v.getY())
				{
					res = r;
					break;
				}
			}
		}
		return res;
	}

	public Iterator<Rectangle> iterator() {
		return new RectangleIterator(this);
	}
	
	public static class RectangleIterator implements Iterator<Rectangle> {
		Rectangle rect;
		int currPosition = -1;
		public RectangleIterator(Rectangle rectangle)
		{
			rect = rectangle;
		}

		public boolean hasNext() {
			return (rect.getSubRectangles().size() > (currPosition + 1) );
		}

		public Rectangle next() {
			return rect.getSubRectangles().get(++currPosition);
		}

		public void remove() {
			throw new RuntimeException("Was not implemented!");
		}
	}

}
