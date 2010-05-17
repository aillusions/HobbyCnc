package cnc.editor;

import cnc.editor.GCommand;
import cnc.editor.GCommand.GcommandTypes;

public class GCodeParser {	

	public static GCommand parseCommand(String cmd){
		
		if(cmd != null && !cmd.trim().equals("") && cmd.trim().length() > 3){
			
			cmd = cmd.trim().toUpperCase().replaceAll("\\s+", " ");
			GcommandTypes cmdType = null;
			
			if(cmd.substring(0, 3).trim().equals("G00")){				
				cmdType = GcommandTypes.G00;
				
			}else if(cmd.substring(0, 3).trim().equals("G01")){				
				cmdType = GcommandTypes.G01;
				
			} else if(cmd.substring(0, 3).trim().equals("G02")){				
				cmdType = GcommandTypes.G02;
			} 
		       
			if(cmdType != null){			
				String withoutType = cmd.replace(cmdType.toString(), "").trim();
				String[] args = withoutType.split(" ");
				
				switch(cmdType){
				case G00: 
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
					return new GCommand(GcommandTypes.G00, x,y,z);
				case G01:
					break;
				case G02: 
					break;
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
