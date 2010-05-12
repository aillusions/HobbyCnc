package cnc.operator;


public interface ICncByteSignalGenerator {
	void addCommanderListener(ICncByteSignalGeneratorListener icl);
	void removeCommanderListener(Object o);
}
