package cnc.emulation;

import cnc.operator.model.DoublePoint3D;

public class MachineHead  {

	private static final long serialVersionUID = 1L;

	DoublePoint3D position;
	
	public MachineHead() {
		super();
		position = new DoublePoint3D();
	}

	public DoublePoint3D getPosition() {
		return position;
	}

	/*
     * -1 =< x,y,z =< 1
     */
    public DoublePoint3D DoStep(byte x_, byte y_, byte z_,  double resolution) {
    	double newX = position.getX();
    	double newY = position.getY();
    	double newZ = position.getZ();
    	
    	boolean validChanged = true;
        switch(x_) {
            case -1:
            	if(newX - resolution >= 0)
            		newX = newX - resolution;
            	else
            		validChanged = false;
                break;
            case 1:
            	newX = newX + resolution;
                break;
            case 0:
                //nothing to do
                break;
        }
        if(validChanged)
	        switch(y_) {
	            case -1:
	            	if(newY - resolution >= 0)
	            		newY = newY - resolution;
	            	else
	            		validChanged = false;
	                break;
	            case 1:
	            	newY = newY+ resolution;
	                break;
	            case 0:
	                //nothing to do
	                break;
	        }
        if(validChanged)
	        switch(z_) {
	            case -1:
	            	if(newZ - resolution >= 0)
	            		newZ = newZ - resolution;
	            	else
	            		validChanged = false;	            		
	                break;
	            case 1:
	            	newZ = newZ + resolution;
	                break;
	            case 0:
	                //nothing to do
	                break;
	        }
        if(validChanged)
        {
	    	position.setX(newX);
	    	position.setY(newY);
	    	position.setZ(newZ);
        }
        return position;
    }
    
    public boolean setPosition(double d, double e, double f)
    {
    	boolean res = false;
    	if(d > -1 && e > -1 && f > -1  )
    	{
	    	position.setX(d);
	    	position.setY(e);
	    	position.setZ(f);
	    	res = true;
    	}
    	return res;
    }
}
