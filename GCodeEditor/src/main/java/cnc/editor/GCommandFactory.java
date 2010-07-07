package cnc.editor;

import cnc.editor.Editor.GcommandTypes;

public class GCommandFactory {
	
	private Float x;
	private Float y;
	private Float z;
	private Float r;
	private Float i;
	private Float j;

	private GcommandTypes cmdType;
			
	public GCommand getCommand(){
		
		switch(cmdType){
			case G00: 
				return new GCommandG00(x,y,z);
			case G01:
				return new GCommandG01(x,y,z);
			case G02: 
				if(r != null){
					return new GCommandG02(x,y,z,r);
				}else{
					return new GCommandG02(x,y,z,i,j);
				}
			case G03: 
				if(r != null){
					return new GCommandG03(x,y,z,r);
				}else{
					return new GCommandG03(x,y,z,i,j);
				}
			//case ORIGIN:
				//return new GCommandOrigin();
			default:
				throw new RuntimeException("Command type was not recognized.");
		}
	}

	public void setCmdType(GcommandTypes cmdType) {
		this.cmdType = cmdType;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public void setZ(Float z) {
		this.z = z;
	}

	public void setR(Float r) {
		this.r = r;
	}

	public void setI(Float i) {
		this.i = i;
	}

	public void setJ(Float j) {
		this.j = j;
	}
		
}
