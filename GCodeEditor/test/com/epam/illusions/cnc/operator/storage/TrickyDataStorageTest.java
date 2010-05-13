package com.epam.illusions.cnc.operator.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cnc.parser.Vertex;
import cnc.storage.IDataStorage;
import cnc.storage.database.TrickyDataStorage;



public class TrickyDataStorageTest {
	@Test()
	public void dummy()
	{
		
	}
	
	@Test()
	public void addVertex()
	{
		IDataStorage ds = new TrickyDataStorage();
		ds.clearStorage();
		long startTime = System.currentTimeMillis();
		Vertex[] vertexes = new Vertex[1000];
		for(int i= 0; i < vertexes.length; i ++)
		{
			vertexes[i] = new Vertex(i,i,i,0);
			ds.addVertex(vertexes[i]);
		}
		//System.out.println("Prompt -->" + (System.currentTimeMillis() - startTime) + "ms");
		Vertex v = null;
		int j = 0;
		while((v = ds.getNextVertex())!= null)
		{
			//System.out.println(v);
			assertEquals(vertexes[j], v);			
			v.setUsed(true);
			//ds.saveVertex(v);
			j++;
		}
		
		System.out.println("Tricky -->" + (System.currentTimeMillis() - startTime) + "ms");
		assertEquals(1000, j);
		ds.clearStorage();
		//Tricky -->2844ms
		
	}
}
