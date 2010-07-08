package cnc.editor;

import cnc.editor.Editor.GcommandTypes;
import cnc.editor.domain.gcmd.GCommand;

public class GCodeParser {	

	public static GCommand parseCommand(String stringToParse){
		
		GCommand result = null;
		
		try{			
			if(stringToParse != null && !stringToParse.trim().equals("") && stringToParse.trim().length() > 3){
				
				stringToParse = stringToParse.trim().toUpperCase().replaceAll("\\s+", " ");				
				GcommandTypes cmdType = findType(stringToParse);
				
				if(cmdType != null){
					
					GCommandFactory gCmdFactory = new GCommandFactory();
					gCmdFactory.setCmdType(cmdType);					
					//String withoutType = stringToParse.replace(cmdType.toString(), "").trim();
					String[] args = stringToParse.split(" ");

					for(int k = 0; k < args.length; k ++){
						if(args[k].indexOf("X") != -1){
							gCmdFactory.setX(Float.parseFloat(args[k].replace("X", "")));
						}else if(args[k].indexOf("Y") != -1){
							gCmdFactory.setY(Float.parseFloat(args[k].replace("Y", "")));
						}else if(args[k].indexOf("Z") != -1){
							gCmdFactory.setZ(Float.parseFloat(args[k].replace("Z", "")));
						}else if(args[k].indexOf("R") != -1){
							gCmdFactory.setR(Float.parseFloat(args[k].replace("R", "")));
						}else if(args[k].indexOf("I") != -1){
							gCmdFactory.setI(Float.parseFloat(args[k].replace("I", "")));
						}else if(args[k].indexOf("J") != -1){
							gCmdFactory.setJ(Float.parseFloat(args[k].replace("J", "")));
						}
					}
					
					result = gCmdFactory.getCommand();
				}		
			}else{
				throw new IllegalArgumentException("Wrong command string: '" + stringToParse + "'.");
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}

		return result;
	}	
	
	
	private static GcommandTypes findType(String cmdStr){
		
		GcommandTypes cmdType = null;
		
		if(cmdStr.contains("G00 ") || cmdStr.contains("G0 ")){			
			cmdType = GcommandTypes.G00;			
		}else if(cmdStr.contains("G01 ") || cmdStr.contains("G1 ")){				
			cmdType = GcommandTypes.G01;			
		}else if(cmdStr.contains("G02 ") || cmdStr.contains("G2 ")){			
			cmdType = GcommandTypes.G02;			
		}else if(cmdStr.contains("G03 ") || cmdStr.contains("G3 ")){	
			cmdType = GcommandTypes.G03;
		}/*else if(cmdStr.contains("ORIGIN ")){
			cmdType = GcommandTypes.ORIGIN;
		}*/else if(cmdStr.contains("G41 ")){
			cmdType = GcommandTypes.G00;
		} 

		return cmdType;
	}

}
