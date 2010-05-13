package com.epam.illusions.cnc.myutil;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.Test;

import cnc.parser.Vertex;
import cnc.storage.database.SimpleDataStorage;



public class LineTest {
	@Test()
	public void dummy()
	{
		assertTrue(true);
	}
	
	@Test()
	public void percistenceTest()
	{

	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
	    System.out.println(sdf.format(cal.getTime()));	    
	    
			    for(int i=0; i<100; i ++ )
			    {
			    	SimpleDataStorage ds = new SimpleDataStorage();	
			    	ds.clearStorage();
			    	ds.addVertex(new Vertex(0,0.1,0.2,0.3));
			    	ds.addVertex(new Vertex(1,0.1,0.2,0.3));			    	
			    	ds.addLine(1, new Vertex(2,2,2,2), new Vertex(3,3,3,3));
			    }
	    
	    cal = Calendar.getInstance();
	    sdf = new SimpleDateFormat("HH:mm:ss:SSS");	    
	    System.out.println(sdf.format(cal.getTime()));	   
	}
	
}
