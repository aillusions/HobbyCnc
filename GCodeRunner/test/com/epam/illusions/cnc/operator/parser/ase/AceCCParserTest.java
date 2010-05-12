package com.epam.illusions.cnc.operator.parser.ase;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class AceCCParserTest {

	@Test()
	public void dummy()
	{
		assertTrue(true);
	}
}

/*
public static void main(String args[]) throws ParseException {
        java.io.BufferedReader r  = null;
		try {
			r = new java.io.BufferedReader(new java.io.FileReader("Hello.ase"));
		} catch (Exception e) {
			e.printStackTrace();
		}                                                                                       
    	AseCCParser parser = new AseCCParser(r); 	
    	parser.setStorage(new DataStorage());
    	parser.getStorage().clearStorage();
    	parser.setLineCounter(0);
        parser.Start();
        System.out.println("------------> END");
  }*/

