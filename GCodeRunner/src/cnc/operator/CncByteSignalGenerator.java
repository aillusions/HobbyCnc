package cnc.operator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class CncByteSignalGenerator implements ICncByteSignalGenerator {

	public enum ShiftValues {
		stepBackward(-1), noShift(0), stepForward(1);

		private int shift;

		private ShiftValues(int shift) {
			this.shift = shift;
		}

		public int getShiftValue() {
			return shift;
		}

		public static ShiftValues get(int shiftValue) {
			switch (shiftValue) {
			case -1:
				return stepBackward;
			case 0:
				return noShift;
			case 1:
				return stepForward;
			default:
				throw new IllegalArgumentException(
						"Illegal argument shiftValue: " + shiftValue);
			}
		}
	}
	
	private CncByte currCncByte;
	private List<ICncByteSignalGeneratorListener> byteListeners;
	
	public CncByteSignalGenerator() {
		currCncByte = new CncByte((byte) 0);
		byteListeners = new LinkedList<ICncByteSignalGeneratorListener>();
	}

	public void doXSteps(int steps) {
		if (steps > 0)
			currCncByte.setDirectionXLevel(true);
		else
			currCncByte.setDirectionXLevel(false);
		for (int i = 0; i < java.lang.Math.abs(steps); i++) {
			currCncByte.invertTactXLevel();
			notifyListeners();
		}
	}

	public void doYSteps(int steps) {
		if (steps > 0)
			currCncByte.setDirectionYLevel(true);
		else
			currCncByte.setDirectionYLevel(false);
		for (int i = 0; i < java.lang.Math.abs(steps); i++) {
			currCncByte.invertTactYLevel();
			notifyListeners();
		}
	}

	public void doZSteps(int steps) {
		if (steps > 0)
			currCncByte.setDirectionZLevel(true);
		else
			currCncByte.setDirectionZLevel(false);
		for (int i = 0; i < java.lang.Math.abs(steps); i++) {
			currCncByte.invertTactZLevel();
			notifyListeners();
		}
	}

	public void shiftToNewPosition(ShiftValues xShift, ShiftValues yShift,	ShiftValues zShift) {

		if (xShift != null)
			if (xShift.getShiftValue() > 0) {
				currCncByte.setDirectionXLevel(true);
				currCncByte.invertTactXLevel();
			} else if (xShift.getShiftValue() < 0) {
				currCncByte.setDirectionXLevel(false);
				currCncByte.invertTactXLevel();
			}

		if (yShift != null)
			if (yShift.getShiftValue() > 0) {
				currCncByte.setDirectionYLevel(true);
				currCncByte.invertTactYLevel();
			} else if (yShift.getShiftValue() < 0) {
				currCncByte.setDirectionYLevel(false);
				currCncByte.invertTactYLevel();
			}

		if (zShift != null)
			if (zShift.getShiftValue() > 0) {
				currCncByte.setDirectionZLevel(true);
				currCncByte.invertTactZLevel();
			} else if (zShift.getShiftValue() < 0) {
				currCncByte.setDirectionZLevel(false);
				currCncByte.invertTactZLevel();
			}

		notifyListeners();
	}
	
	public void doOperation() {
		currCncByte.invertOperationLevel();
		notifyListeners();
	}
	
	private void notifyListeners() {
		Iterator<ICncByteSignalGeneratorListener> it = byteListeners.iterator();
		while (it.hasNext()) {
			it.next().onGenSygnal(currCncByte.getByte());
		}
	}

	public void addCommanderListener(ICncByteSignalGeneratorListener listener) {
		byteListeners.add(listener);
	}

	public void removeCommanderListener(Object o) {
		byteListeners.remove(o);
	}
}
