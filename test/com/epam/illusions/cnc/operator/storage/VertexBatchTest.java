package com.epam.illusions.cnc.operator.storage;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import cnc.operator.storage.VertexBatch;
import cnc.parser.Rectangle;
import cnc.parser.Vertex;



public class VertexBatchTest {
	@Test()
	public void dummy()
	{
		assertTrue(true);
	}
	
	@Test()
	public void getNearestVertex()
	{
		List<Vertex> vertexes = new LinkedList<Vertex>();
		vertexes.add(new Vertex(0, 0, true));
		for(int i = 1; i < 11; i++)
		{
			vertexes.add(new Vertex(i,i,i,i));			
		}
		VertexBatch vBatch = new VertexBatch();
		vBatch.setLoadedVertexes(vertexes);
		
		assertEquals(new Vertex(1,1,false), vBatch.getNearestUNUSEDVertex(new Vertex(0,0, true)));
		assertEquals(new Vertex(10,10,false), vBatch.getNearestUNUSEDVertex(new Vertex(10,10, false)));
	}

	@Test()
	public void getDistance()
	{
		assertEquals(Math.sqrt(3), VertexBatch.getDistance(new Vertex(0,0,0,0), new Vertex(1,1,1,1)));		
	}
	
	@Test()
	public void splitRectangle()
	{
		//List<Rectangle> rects = DistanceFinder.splitRectangle(new Rectangle(new Vertex(0,0,0,0), new Vertex(0,100,100,0)));
		//assertEquals(9, rects.size());
	}
}
