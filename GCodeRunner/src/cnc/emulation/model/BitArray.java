package cnc.emulation.model;

public class BitArray {

    //array of bits (1 or 0)
    boolean[] innerStorage;
    
    private BitArray()
    {
    
    }
    public BitArray(byte iniVal) {
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
    
    /*
     *return bit (0 or 1) by position (from left to right)
     */
    public boolean GetBit(int index) throws Exception
    {
        if(index >= 0 && index <=7)
            return innerStorage[index];
        else
            throw new Exception("Index must be betwen 0 and 7");
    }
    
}
