package cnc;

import cnc.emulation.Machine2DView;
import cnc.emulation.VirtuaMachine;
import cnc.operator.CncByteSignalGenerator;
import cnc.operator.CncCommander;
import cnc.operator.GCodeInterpreter;

public class Run {
	CncCommander cncCommander;
	GCodeInterpreter gCodeInterpreter;
	CncByteSignalGenerator byteSignalGenerator;
	VirtuaMachine machine;
	Machine2DView view;

	CncLPTDriver cncDriver;

	Run() {
		machine = new VirtuaMachine();
		byteSignalGenerator = new CncByteSignalGenerator();
		// cncDriver = new CncLPTDriver();
	}

	public void run() {
		long startTime = System.currentTimeMillis();

		byteSignalGenerator.addCommanderListener(machine);
		// byteSignalGenerator.addCommanderListener(cncDriver);

		view = new Machine2DView(machine.getBuilding().getBricks(),
				byteSignalGenerator, 100, 5, 0.0025d);

		view.setVisible(true);
		machine.addMachineListener(view);

		cncCommander = new CncCommander("0.0025", byteSignalGenerator);
		gCodeInterpreter = new GCodeInterpreter(cncCommander);

		gCodeInterpreter.acceptCommand("G00 X10");
		gCodeInterpreter.acceptCommand("G00 Z10");
		gCodeInterpreter.acceptCommand("G00 Y10");

		int i = 0;
		while (i < 10) {
			gCodeInterpreter.acceptCommand("G00 X5 Y5");
			gCodeInterpreter.acceptCommand("G00 X0 Y0");
			i++;
		}
	}

	public static void main(String[] args) {
		new Run().run();
	}
}
