package cnc.emulation;

import cnc.operator.model.DoublePoint3D;

public interface IMachineListener {
	void onMachineHeadMoved(DoublePoint3D newPosition);
}
