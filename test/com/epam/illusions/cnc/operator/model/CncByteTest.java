package com.epam.illusions.cnc.operator.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cnc.operator.CncByte;


public class CncByteTest {
	@Test()
	public void dummy() {
		assertTrue(true);
	}
	
	@Test()
	public void equals() {
		assertEquals("Equals does not work prperly!",new CncByte((byte)56), new CncByte((byte)56));
		assertFalse("Equals does not work prperly!", new CncByte((byte)2).equals(new CncByte((byte)3)));
		assertFalse("Equals does not work prperly!", new CncByte((byte)0).equals(null));
	}
}
