package jnpout32;

public class ioTest {
	
	short Addr = 0x378;
	int stepCount = 1000;
	int pauseMls = 1;	
	pPort lpt;
	
	public ioTest() {
		lpt = new pPort();
	}

	public void moveCaret(short a, short b)
	{
		short curr = a;
		for(int i = 0; i < stepCount; i++)
		{
			try { Thread.sleep(pauseMls); } catch (InterruptedException e) {}
			lpt.output(Addr, curr);
			if(curr == a)
				curr = b;
			else
				curr = a;
		}
		
	}
	
	public static void main(String args[]) throws InterruptedException {
		ioTest test = new ioTest();
		
		test.moveCaret((short) 2,(short) 3); 	// TOP
		test.moveCaret((short) 0,(short) 1); 	// BOTTOM
		
		
		//test.moveCaret((short) 0,(short) 4); 	// <--  left
		//test.moveCaret((short) 0,(short) 16); 	// \/   forward
		//test.moveCaret((short) 8,(short) 12);	// -->  right
		//test.moveCaret((short) 32,(short) 48);	// /\   backward
	}

}
