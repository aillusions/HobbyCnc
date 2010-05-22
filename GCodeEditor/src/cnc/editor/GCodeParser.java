package cnc.editor;

import cnc.editor.Editor.GcommandTypes;

public class GCodeParser {	

	public static GCommand parseCommand(String cmd){
		
		if(cmd != null && !cmd.trim().equals("") && cmd.trim().length() > 3){
			
			cmd = cmd.trim().toUpperCase().replaceAll("\\s+", " ");
			GcommandTypes cmdType = null;
			
			if(cmd.contains("G00 ") || cmd.contains("G0 ")){
				
				cmdType = GcommandTypes.G00;
				
			}else if(cmd.contains("G01 ") || cmd.contains("G1 ")){		
				
				cmdType = GcommandTypes.G01;
				
			}else if(cmd.contains("G02 ") || cmd.contains("G2 ")){		
				
				cmdType = GcommandTypes.G02;
				
			}else if(cmd.contains("G03 ") || cmd.contains("G3 ")){		
				
				cmdType = GcommandTypes.G03;
			} 
		       
			if(cmdType != null){	
				
				String withoutType = cmd.replace(cmdType.toString(), "").trim();
				String[] args = withoutType.split(" ");
				
				Float x = null, y = null, z = null, r = null, i = null, j = null;
				
				for(int k = 0; k < args.length; k ++){
					if(args[k].indexOf("X") != -1){
						x = Float.parseFloat(args[k].replace("X", ""));
					}else if(args[k].indexOf("Y") != -1){
						y = Float.parseFloat(args[k].replace("Y", ""));
					}else if(args[k].indexOf("Z") != -1){
						z = Float.parseFloat(args[k].replace("Z", ""));
					}else if(args[k].indexOf("R") != -1){
						r = Float.parseFloat(args[k].replace("R", ""));
					}else if(args[k].indexOf("I") != -1){
						i = Float.parseFloat(args[k].replace("I", ""));
					}else if(args[k].indexOf("J") != -1){
						j = Float.parseFloat(args[k].replace("J", ""));
					}
				}
				
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
				default:
					System.out.println("UNDEF");
				}
			}		
		} 
		

		return null;
	}	
	
/*	private static GCommand getNewPosition(String[] args, GCommand previousPosition){
		
		Float x = null, y = null, z = null;
		
		for(int i = 0; i < args.length; i ++){
			if(args[i].indexOf("X") != -1){
				x = Float.parseFloat(args[i].replace("X", ""));
			}else if(args[i].indexOf("Y") != -1){
				y = Float.parseFloat(args[i].replace("Y", ""));
			}else if(args[i].indexOf("Z") != -1){
				z = Float.parseFloat(args[i].replace("Z", ""));
			}
		}
		
		if(x == null){
			x = previousPosition.getX();
		}
		if(y == null){
			y = previousPosition.getY();
		}
		if(z == null){
			z = previousPosition.getZ();
		}

		return new GCommand(x, y, z);				
	}*/
}
