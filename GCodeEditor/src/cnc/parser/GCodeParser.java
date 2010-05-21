package cnc.parser;

import java.math.BigDecimal;

import cnc.editor.Editor.GcommandTypes;
import cnc.editor.domain.BigDecimalPoint3D;
import cnc.editor.domain.GCommand;


public class GCodeParser {	

	public static GCommand parseCommand(String cmd, BigDecimalPoint3D previousPosition){
		
		//System.out.println(cmd);
		if(cmd != null && !cmd.trim().equals("")){
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
				return new GCommand(GcommandTypes.G00, getNewPosition(args, previousPosition ));
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
	
	private static BigDecimalPoint3D getNewPosition(String[] args, BigDecimalPoint3D previousPosition){
		
		BigDecimal x = null, y = null, z = null;
		
		for(int i = 0; i < args.length; i ++){
			if(args[i].indexOf("X") != -1){
				x = new BigDecimal(args[i].replace("X", ""));
			}else if(args[i].indexOf("Y") != -1){
				y = new BigDecimal(args[i].replace("Y", ""));
			}else if(args[i].indexOf("Z") != -1){
				z = new BigDecimal(args[i].replace("Z", ""));
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

		return new BigDecimalPoint3D(x, y, z);				
	}
}
