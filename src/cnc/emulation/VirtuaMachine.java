package cnc.emulation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import cnc.emulation.model.BitArray;
import cnc.emulation.model.Brick;
import cnc.emulation.model.Building;
import cnc.operator.ICncByteSignalGeneratorListener;
import cnc.operator.model.BigDecimalPoint3D;
import cnc.operator.model.DoublePoint3D;


public class VirtuaMachine implements ICncByteSignalGeneratorListener, IMachine {
	
	private MachineHead head = null;
	Building building = null;
	List<IMachineListener> listeners;
	
	// Minimal shift by axis per step
	public static double RESOLUTION = 1;

	private byte sygnalLevel = 0;



	public VirtuaMachine() {
		head = new MachineHead();
		building = new Building();
		
		listeners = new LinkedList<IMachineListener>();
	}

	public boolean setHeadPosition(double d, double e, double f) {
		return head.setPosition(d, e, f);
	}

	public byte getSygnalLevel() {
		return sygnalLevel;
	}

	public boolean setHeadPosition(BigDecimalPoint3D doublePoint3D) {
		return head.setPosition(doublePoint3D.getX().doubleValue(), doublePoint3D.getY().doubleValue(),
				doublePoint3D.getZ().doubleValue());
	}

	public DoublePoint3D getHeadPosition() {
		return head.getPosition();
	}

	public DoublePoint3D receiveByte(byte val) {
		boolean changed = false;
		try {
			byte argX, argY, argZ;
			if (sygnalLevel != val) {
				BitArray oldBits = new BitArray(sygnalLevel);
				BitArray newBits = new BitArray(val);
				if (oldBits.GetBit(1) != newBits.GetBit(1)) // tact
					if (!newBits.GetBit(0)) // direction
						argX = -1;
					else
						argX = 1;
				else
					argX = 0;

				if (oldBits.GetBit(3) != newBits.GetBit(3))
					if (!newBits.GetBit(2))
						argY = -1;
					else
						argY = 1;
				else
					argY = 0;

				if (oldBits.GetBit(5) != newBits.GetBit(5))
					if (!newBits.GetBit(4))
						argZ = -1;
					else
						argZ = 1;
				else
					argZ = 0;
				double x = head.getPosition().getX();
				double y = head.getPosition().getY();
				double z = head.getPosition().getZ();
				
				head.DoStep(argX, argY, argZ, RESOLUTION);
				if(x != head.getPosition().getX() || y != head.getPosition().getY() || z != head.getPosition().getZ())
				{
					changed = true;
				}
				if (oldBits.GetBit(6) != newBits.GetBit(6)) {
					doOperation(Brick.BrickType.WHITE);
					changed = true;
				}
				if (oldBits.GetBit(7) != newBits.GetBit(7)) {
					doOperation(Brick.BrickType.BLUE);
					changed = true;
				}
			}
			sygnalLevel = val;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(changed)
			onChangedHappened() ;
		return head.getPosition();
	}

	private void doOperation(Brick.BrickType type) {
		building.putTheBrick(new Brick(head.getPosition(), true, type));
	}

	public Building getBuilding() {
		return building;
	}

	//public void onControllerAcceptSignal(byte b) {
	//	receiveByte(b);
	//}

	private void onChangedHappened() {
		Iterator<IMachineListener> it = listeners.iterator();
		while(it.hasNext())
		{
			it.next().onMachineHeadMoved(head.getPosition());
		}
	}

	public void removeMachineListener(Object listener) {
		listeners.remove(listener);
	}

	public void addMachineListener(IMachineListener listener) {
		listeners.add(listener);
	}

	public void onGenSygnal(byte b) {
		receiveByte(b);	
	}

}
