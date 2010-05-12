package cnc;


import jnpout32.pPort;

import cnc.operator.Config;
import cnc.operator.ICncByteSignalGeneratorListener;

public class CncLPTDriver implements ICncByteSignalGeneratorListener {

	private short Addr = 0x378;
	
	private pPort lpt = new pPort();
	
	public void onGenSygnal(byte b) {
		lpt.output(Addr, b);	
		Config.sleep();		
	}
	
}
