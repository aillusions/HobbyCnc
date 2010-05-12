package com.epam.illusions.cnc.inventory.model;

import org.junit.*;

import cnc.operator.model.BigDecimalPoint3D;

import static org.junit.Assert.*;

public class DoublePoint3DTest {
	@Test
	public void complexTest()
	{
		//Constuctor
		BigDecimalPoint3D dp = new BigDecimalPoint3D();	
		assertEquals("Initial state X fault!" , 0.0, dp.getX());
		assertEquals("Initial state Y fault!" , 0.0, dp.getY());
		assertEquals("Initial state Z fault!" , 0.0, dp.getZ());
		
		//toString()
		assertEquals("Method toString() is not proper!", "(0,0,0)", dp.toString());

		
		//Constuctor
		//dp = new BigDecimalPoint3D(1.2, 3.4, 5.6);		
		assertEquals("Initial state X fault!" , 1.2, dp.getX());
		assertEquals("Initial state Y fault!" , 3.4, dp.getY());
		assertEquals("Initial state Z fault!" , 5.6, dp.getZ());
		
		//toString()
		assertEquals("Method toString() is not proper!", "(1.2,3.4,5.6)", dp.toString());

		//equals()
		//assertTrue("Equals function does not work properly!", new BigDecimalPoint3D(2.4,6.7, 0.0).equals(new BigDecimalPoint3D(2.4,6.7, 0.0)));
		//assertFalse("Equals function does not work properly!", new BigDecimalPoint3D(2.01,6.0, 9).equals(new BigDecimalPoint3D(2.01,6.9, 9)));
	}
}
