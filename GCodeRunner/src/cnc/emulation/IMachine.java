package cnc.emulation;


public interface IMachine {
	public void removeMachineListener(Object listener);
	public void addMachineListener(IMachineListener listener);
}
