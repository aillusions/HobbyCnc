package cnc.operator;

import cnc.CncLPTDriver;
import cnc.emulation.Machine2DView;
import cnc.emulation.VirtuaMachine;
import cnc.operator.storage.IDataStorage;
import cnc.operator.storage.TrickyDataStorage;
import cnc.parser.bmp.BmpParser;

public class Run {

	CncCommander cncCommander;
	GCodeInterpreter gCodeInterpreter;
	CncByteSignalGenerator byteSignalGenerator;
	VirtuaMachine machine;
	Machine2DView view;
	BmpFilePrinter bmpPrinter;
	CncLPTDriver cncDriver;
	
	Run(){
		machine = new VirtuaMachine();	
		byteSignalGenerator = new CncByteSignalGenerator();
		cncDriver = new CncLPTDriver();
	}
	
	public void run(){
		//System.out.println("Start");		
		long startTime = System.currentTimeMillis();	

		byteSignalGenerator.addCommanderListener(machine);
		//byteSignalGenerator.addCommanderListener(cncDriver);		
		
		view = new Machine2DView(machine.getBuilding().getBricks(), byteSignalGenerator, 100, 5, 0.0025d);		
		
		view.setVisible(true);
		machine.addMachineListener(view);
		
		cncCommander = new CncCommander("0.0025", byteSignalGenerator);
		gCodeInterpreter = new GCodeInterpreter(cncCommander);		
		
		bmpPrinter = new BmpFilePrinter(gCodeInterpreter);		
		BmpParser parser = new BmpParser();		
		IDataStorage store  = new TrickyDataStorage();
		store.clearStorage();
		parser.setStorage(store);
		parser.loadbitmap("parser/BmpTest.bmp");		
		bmpPrinter.setStore(store);
		bmpPrinter.StartBuild();	
				
		
		//gCodeInterpreter.acceptCommand("G00 X10");
		//gCodeInterpreter.acceptCommand("G00 Z10");
		//gCodeInterpreter.acceptCommand("G00 Y10");
		
		//System.out.println("Building completed in: "  + (System.currentTimeMillis() - startTime)/1000 + " sec.");
		int i = 0;
		while(i < 10){
			gCodeInterpreter.acceptCommand("G00 X5 Y5");
			gCodeInterpreter.acceptCommand("G00 X0 Y0");
			i++;
		}
	}
	
	public static void main(String[] args) {
		new Run().run();
	}	
}
