package cnc.operator;

import java.math.BigDecimal;

import cnc.operator.model.BigDecimalPoint3D;

public class GCodeInterpreter {
	
	private enum GcommandTypes{G00, G01, G02}

	CncCommander commander;
	
	public GCodeInterpreter(CncCommander commander){
		this.commander = commander;
	}
	
	public CncCommander getCncCommander(){
		return commander;
	}
	
	public boolean acceptCommand(String cmd){
		//System.out.println(cmd);
	
		boolean result = true;
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
				executeG00Command(args);
				break;
			case G01:
				break;
			case G02: 
				break;
			default:
				System.out.println("UNDEF");
			}
		}		
		return result;
	}	
	
	private void executeG00Command(String[] args){
		
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

		commander.goTo(new BigDecimalPoint3D(x, y, z));				
	}
}
