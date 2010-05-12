package com.epam.illusions.cnc;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;


public class PrjRowCounter {
	@Test()
	public void dummy() {
		assertTrue(true);
	}
	
	@Test()
	public void counter() {		
		try {
			System.out.println("    Count of src rows: " + getAllDirFileLineCounter("D:/Hobby2/HobbyCnc/src") );
			System.out.println("    Count of test rows: " + getAllDirFileLineCounter("D:/Hobby2/HobbyCnc/test") );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getAllDirFileLineCounter(String dir) throws IOException
	{
		int res = 0;
		for(File javaFile : new File(dir).listFiles() )
		{
			if(javaFile.isFile())
			{
				int rows = getFileLineCounter(javaFile);
				//System.out.println(rows + " \t--> " + javaFile);
				res += rows;
			}
		}
		for(File f : new File(dir).listFiles() )
		{
			if(f.isDirectory())
				res += getAllDirFileLineCounter(f.getPath());
		}
		return res;
	}
	
	public int getFileLineCounter(File f) throws IOException
	{
		BufferedReader br = new  BufferedReader(new InputStreamReader(new FileInputStream(f)));
		int res = 0;
		while(br.readLine() != null)
		{
			res ++;
		}
		br.close();
		return res;
	}
}
