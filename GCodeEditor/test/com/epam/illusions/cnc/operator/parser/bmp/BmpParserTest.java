package com.epam.illusions.cnc.operator.parser.bmp;

import java.util.Iterator;

import org.junit.Test;

import cnc.operator.storage.SimpleDataStorage;
import cnc.parser.Vertex;
import cnc.parser.bmp.BmpParser;


public class BmpParserTest {
	@Test()
	public void dummy()
	{
		
	}
	
	@Test()
	public void start()
	{
		BmpParser parser = new BmpParser();
		SimpleDataStorage store = new SimpleDataStorage();
		parser.setStorage(store);
		parser.loadbitmap("parser/BmpTest.bmp");
		Iterator<Vertex> vertexIterator = store.vertexIterator();
		while(vertexIterator.hasNext())
		{
			//System.out.println( vertexIterator.next());
		}
        System.out.println("------------> END");
	}
}
