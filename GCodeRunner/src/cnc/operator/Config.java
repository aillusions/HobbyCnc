package cnc.operator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Config {
	public static int trickyStorageRectangleSideLength = 100;

	public static void sleep(){
			String s = "1";
			int i = 0;
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public static int stepsOnPixelRatio = 100;
	public static double distanceToSheet = 5d;

}
