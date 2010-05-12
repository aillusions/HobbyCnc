package cnc.operator;


/**
 * Represent sequence of the bits and expose tools to operate with those bits
 * in convenient manner. Used by VirtualCnc to simplifies byte's management.
 * CncByte based on array of the 8 bits (boolean).
 * 
 * @author Alex
 *
 */
public class CncByte{
	
    //array of bits (1 or 0)
    boolean[] innerStorage;

    /**
     * Constructor 
     * @param iniVal initial value of the array.
     */
    public CncByte(byte iniVal) {
    	setByte( iniVal);    
    }

    
    @Override
	public boolean equals(Object arg0) {
    	boolean res = false;
    	if(arg0 != null && arg0 instanceof CncByte)
    	{
    		CncByte inByte = (CncByte)arg0;
    		res = true;
    		for(int i = 0; i  < innerStorage.length; i ++)
    		{
    			if(innerStorage[i] != inByte.GetBit(i))
    			{
    				res = false;
    				break;
    			}
    		}
    	}
    	return res;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


	public void setByte(byte iniVal) {
        Integer iVal = (int)iniVal;
        int temp = iVal;
        innerStorage = new boolean[8];
        for(int i = 0; i < innerStorage.length; i ++) {
            if((temp & 1) == 1)
                innerStorage[i] = true;
            else
                innerStorage[i] = false;
            temp =  temp >> 1;
        }
    }
    
    
    /**
     * Give byte represents of itself.
     * @return byte that array of the bits represents.
     */
    public byte getByte()
    {
    	byte result = (byte)0;
    	if(innerStorage[7])
    		result += 128;
    	if(innerStorage[6])
    		result += 64;
    	if(innerStorage[5])
    		result += 32;
    	if(innerStorage[4])
    		result += 16;
    	if(innerStorage[3])
    		result += 8;
    	if(innerStorage[2])
    		result += 4;
    	if(innerStorage[1])
    		result += 2;
    	if(innerStorage[0])
    		result += 1;
    	
    	return result;
    }
    
    /**
     * Give bit value in defined position
     * @param index must be between 0 and 7
     * @return  bit (0 or 1) by position (from left to right)
     */
    public boolean GetBit(int index) 
    {
        if(index >= 0 && index <=7)
            return innerStorage[index];
        else
            throw new RuntimeException("Index must be betwen 0 and 7");
    }
    
    /**
     * Set bit value in defined position
     * @param index must be between 0 and 7
     * @param val
     */
    public void SetBit(int index, boolean val)
    {
        if(index >= 0 && index <=7)
            innerStorage[index] = val;
        else
            throw new RuntimeException("Index must be betwen 0 and 7");
    }
    
	//
	public boolean getDirectionXLevel() {
		return GetBit(0);
	}

	public boolean getDirectionYLevel()  {
		return GetBit(2);
	}

	public boolean getDirectionZLevel() {
		return GetBit(4);
	}

	public boolean getTactXLevel() {
		return GetBit(1);
	}

	public boolean getTactYLevel(){
		return GetBit(3);
	}

	public boolean getTactZLevel()  {
		return GetBit(5);
	}

	// ------------------------------------------

	public void setDirectionXLevel(boolean val)  {
		SetBit(0, val);
	}

	public void setDirectionYLevel(boolean val) {
		SetBit(2, val);
	}

	public void setDirectionZLevel(boolean val){
		SetBit(4, val);
	}

	public void setTactXLevel(boolean val) {
		SetBit(1, val);
	}

	public void setTactYLevel(boolean val) {
		SetBit(3, val);
	}

	public void setTactZLevel(boolean val) {
		SetBit(5, val);
	}

	// ---------------------------------------------

	public boolean getOperationLevel()  {
		return GetBit(6);
	}

	public void setOperationLevel(boolean val) {
		SetBit(6, val);
	}

	// ----------------------------------------------

	public void invertDirectionXLevel() {
		setDirectionXLevel(!getDirectionXLevel());
	}

	public void invertDirectionYLevel() {
		setDirectionYLevel(!getDirectionYLevel());
	}

	public void invertDirectionZLevel()  {
		setDirectionZLevel(!getDirectionZLevel());
	}

	
	
	public void invertTactXLevel() {
		setTactXLevel(!getTactXLevel());
	}

	public void invertTactYLevel()  {
		setTactYLevel(!getTactYLevel());
	}

	public void invertTactZLevel()  {
		setTactZLevel(!getTactZLevel());
	}

	public void invertOperationLevel() {
		setOperationLevel(!getOperationLevel());
	}

	public CncByte CloneCncByte() {
		return new CncByte(getByte());
	}
}
