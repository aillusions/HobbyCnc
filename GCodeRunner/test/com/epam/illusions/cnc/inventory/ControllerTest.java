package com.epam.illusions.cnc.inventory;

import org.junit.*;


import static org.junit.Assert.*;

public class ControllerTest {
	
	private byte testHelpByte;
	private void onGenSygnalTestHelp(byte b)
	{
		testHelpByte = b;
	}
	
	@Test()
	public void onGenSygnal()
	{
/*		Controller c = new Controller();
		Object listener = new IControllerListener(){
			public void onControllerAcceptSignal(byte b) {
				onGenSygnalTestHelp(b);				
			}};
		c.addControllerListener((IControllerListener)listener);
		c.onGenSygnal((byte)66);
		assertEquals("Listener has not accepted correct value!", testHelpByte, (byte)66);
		c.removeControllerListener(listener);
		c.onGenSygnal((byte)33);
		assertEquals("Listener must be removed!", testHelpByte, (byte)66);*/
	}
}
