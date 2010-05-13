package com.epam.illusions.cnc.operator.storage;

import org.junit.Test;
import static org.junit.Assert.*;

import cnc.parser.Vertex;
import cnc.storage.IDataStorage;
import cnc.storage.database.SimpleDataStorage;



public class SimpleDataStorageTest {
	
	@Test()
	public void dummy()
	{
		
	}
	
	@Test()
	public void getVertexNear()
	{		
		IDataStorage ds = new SimpleDataStorage();
		ds.clearStorage();
		long startTime = System.currentTimeMillis();
		Vertex[] vertexes = new Vertex[1000];
		for(int i= 0; i < vertexes.length; i ++)
		{
			vertexes[i] = new Vertex(i,i,i,0);
			ds.addVertex(vertexes[i]);
		}
		//assertFalse(new Vertex(1,2.3,2.3,2.3).equals(new Vertex(2,2.3,2.3,2.3)));
		Vertex v = null;
		int j = 0;
		while((v = ds.getVertexesNear(v, false).get(0))!= null)
		{
			assertEquals(vertexes[j], v);
			v.setUsed(true);
			ds.saveVertex(v);
			j++;
		}
		long endTime = System.currentTimeMillis();
		assertEquals(1000, j);
		ds.clearStorage();
		System.out.println("Simple Data Storage -->" + (endTime - startTime)  + "ms");
		//Simple Data Storage -->47360ms
	}
}
