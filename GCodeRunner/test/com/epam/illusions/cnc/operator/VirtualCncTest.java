package com.epam.illusions.cnc.operator;

import org.junit.*;

import cnc.operator.CncByteSignalGenerator;
import cnc.operator.ICncByteSignalGeneratorListener;


import static org.junit.Assert.*;

public class VirtualCncTest {
	@Test()
	public void dummy()
	{
		
	}
	
	private byte signalHelper;
	
	@Test()
	public void doXSteps()
	{
		signalHelper = 0;
		CncByteSignalGenerator vCNC = new CncByteSignalGenerator();
		
		Object icl = new ICncByteSignalGeneratorListener(){
			public void onGenSygnal(byte b) {
				signalHelper = b;
			}			
		};
		
		vCNC.addCommanderListener((ICncByteSignalGeneratorListener)icl);
		vCNC.doXSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)3, signalHelper);
		vCNC.doXSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)1, signalHelper);
		vCNC.doXSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)3, signalHelper);
		vCNC.doXSteps(-3);
		assertEquals("Virtual CNC has generated improper signal!", (byte)0, signalHelper);	
	}
	
	@Test()
	public void doYSteps()
	{
		signalHelper = 0;
		CncByteSignalGenerator vCNC = new CncByteSignalGenerator();
		
		Object icl = new ICncByteSignalGeneratorListener(){
			public void onGenSygnal(byte b) {
				signalHelper = b;
			}			
		};
		
		vCNC.addCommanderListener((ICncByteSignalGeneratorListener)icl);
		vCNC.doYSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)12, signalHelper);
		vCNC.doYSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)4, signalHelper);
		vCNC.doYSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)12, signalHelper);
		vCNC.doYSteps(-3);
		assertEquals("Virtual CNC has generated improper signal!", (byte)0, signalHelper);	
	}
	
	@Test()
	public void doZSteps()
	{
		signalHelper = 0;
		CncByteSignalGenerator vCNC = new CncByteSignalGenerator();
		
		Object icl = new ICncByteSignalGeneratorListener(){
			public void onGenSygnal(byte b) {
				signalHelper = b;
			}			
		};
		
		vCNC.addCommanderListener((ICncByteSignalGeneratorListener)icl);
		vCNC.doZSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)48, signalHelper);
		vCNC.doZSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)16, signalHelper);
		vCNC.doZSteps(1);
		assertEquals("Virtual CNC has generated improper signal!", (byte)48, signalHelper);
		vCNC.doZSteps(-3);
		assertEquals("Virtual CNC has generated improper signal!", (byte)0, signalHelper);	
	}
	
	@Test
	public void goToNewPosition()
	{
		signalHelper = 0;
		CncByteSignalGenerator vCNC = new CncByteSignalGenerator();
		
		Object icl = new ICncByteSignalGeneratorListener(){
			public void onGenSygnal(byte b) {
				signalHelper = b;
			}			
		};
		vCNC.addCommanderListener((ICncByteSignalGeneratorListener)icl);
		//vCNC.goToNewPosition(3, 0, 0);
		assertEquals("Virtual CNC has generated improper LAST signal!", 3, signalHelper);
		//assertEquals("New position is not valid!",  new Point3D(3, 0, 0), vCNC.getCurrPosition());
		
		//vCNC.goToNewPosition(6, 0, 0);
		assertEquals("Virtual CNC has generated improper LAST signal!", 1, signalHelper);
		//assertEquals("New position is not valid!",  new Point3D(6, 0, 0), vCNC.getCurrPosition());
		
		//--
		vCNC = new CncByteSignalGenerator();
		
		//vCNC.goToNewPosition(new Point3D(10, 100,0));
		//assertEquals("Shift is not proper!", new Point3D(10, 100,0).toString(), vCNC.getCurrPosition().toString());
		
		//vCNC.goToNewPosition(new Point3D(100, 1, 0));
		//assertEquals("Shift is not proper!", new Point3D(100, 1, 0).toString(), vCNC.getCurrPosition().toString());

	}
	

	
}
