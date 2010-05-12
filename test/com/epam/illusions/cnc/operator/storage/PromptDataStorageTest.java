package com.epam.illusions.cnc.operator.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cnc.operator.storage.IDataStorage;
import cnc.parser.Vertex;



public class PromptDataStorageTest {
	@Test()
	public void dummy()
	{
		
	}
	
	@Test()
	public void addVertex()
	{
		IDataStorage ds = null;//new PromptDataStorage("c:/vertexes.cnc");
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
		while((v = ds.getVertexesNear(v, false).get(0))!= null)
		{
			//System.out.println("Prompt --> "  + j +"; " + (System.currentTimeMillis() - startTime) + "ms");
			assertEquals(vertexes[j], v);
			//System.out.println(v);
			v.setUsed(true);
			ds.saveVertex(v);
			j++;
		}
		
		System.out.println("Prompt -->" + (System.currentTimeMillis() - startTime) + "ms");
		assertEquals(1000, j);
		ds.clearStorage();
		//Prompt -->10969ms
		
	}
}
