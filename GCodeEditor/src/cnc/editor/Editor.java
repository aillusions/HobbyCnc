package cnc.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import cnc.GCodeAcceptor;
import cnc.editor.view.EditorMainFrame;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.memory.BitMapArrayDataStorage;
import cnc.storage.memory.IDataStorage;

public class Editor {
	
	public enum EditModeS{DRAW, TXT};
	public enum EditorTolls{SIMPLE_EDIT, VERTEX_SELECT, CONTINUOUS_EDIT};
	public enum GcommandTypes{G00, G01, G02};

	private GCommandsContainer gcc = GCommandsContainer.getInstance();
	private EditorStates es = EditorStates.getInstance();
	
	public void viewMousePressed(double x, double y){
		
		float cncX = EditorStates.convertView_Cnc((long)x);
		float cncY = EditorStates.convertView_Cnc((long)y);

		boolean isCurrentSelectedToolReset = false;
		
		List<GCommand> vertexes = gcc.findVertexesNear(cncX, cncY);
		
		if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){
			
			if(es.getCurrentGCmdType() != GcommandTypes.G00){
				throw new RuntimeException("Continuous drawing works only for " + GcommandTypes.G00 + "command type");
			}
			
			String cmd = "\n" + es.getCurrentGCmdType() + " X" + cncX + " Y" + cncY;
			gcc.addCommand(GCodeParser.parseCommand(cmd));
			return;
		}
		
		if(vertexes != null && vertexes.size() > 0 && es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){
			es.setCurrentSelectedTool(EditorTolls.VERTEX_SELECT);
			isCurrentSelectedToolReset = true;
		}
		
		if(es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){			
			
			//es.getCurrentGCmdType()
			if(es.getCurrentGCmdType() == GcommandTypes.G00)
				gcc.addCommand(new GCommandG00(cncX, cncY, null));
			else
				gcc.addCommand(new GCommandG02(cncX, cncY, null));
			
			es.setCurrentSelectedTool(EditorTolls.VERTEX_SELECT);
			isCurrentSelectedToolReset = true;
		}
		
		if(es.getCurrentSelectedTool() == EditorTolls.VERTEX_SELECT){
			vertexes = gcc.findVertexesNear(cncX, cncY);
			if(vertexes.size() > 0){
				List<GCommand> neighbourVertexes =  gcc.getNeighbourVertexes(vertexes.get(0));
				es.setSelectedVertex(vertexes.get(0), neighbourVertexes);
			}else{
				es.clearSelection();
			}
			
			if(isCurrentSelectedToolReset){
				es.setCurrentSelectedTool(EditorTolls.SIMPLE_EDIT);
			}
		}
	}
	
	public void convertImageToGCodes() {
		
		File file = null;
		if ((file = EditorMainFrame.openFileChooser("./parser", "bmp"))!= null) {
			
			es.setImportInProgress(true);
			
			IDataStorage store = new BitMapArrayDataStorage();
			
			BmpParser parser = new BmpParser();
			parser.setStorage(store);
			parser.loadbitmap(file.getPath());
			
			final StringBuffer codesBuffer = new StringBuffer();
			
			BmpFilePrinter bmpPrinter = new BmpFilePrinter(new GCodeAcceptor(){
				public void putGCode(String gcode) {
					codesBuffer.append("\r\n" + gcode);					
				}				
			});			
			
			bmpPrinter.setStore(store);
			bmpPrinter.StartBuild();	
			
			gcc.clear();
			gcc.addCommandsBunch(codesBuffer.toString());			

			es.setImportInProgress(false);
		}		
	}

	public void addGCodesFromFile() {
		
		File file = null;
		if ((file = EditorMainFrame.openFileChooser("./gcodes", "cnc"))!= null) {
			
			es.setImportInProgress(true);
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				
				String line = null;
				final StringBuffer codesBuffer = new StringBuffer();
				
				while ((line = br.readLine()) != null) {
					codesBuffer.append("\r\n" + line);
				}
				
				gcc.clear();
				gcc.addCommandsBunch(codesBuffer.toString());
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			es.setImportInProgress(false);
		}	
	}

	public void liftWorkHead() {
		
		//GcommandTypes.G00,
		GCommand gCmd = new GCommandG00(null, null, 2f);
		GCommand lastCmd = gcc.getGCommandList().get(gcc.getGCommandList().size()-1);
		
		//Hypothetically (preliminary) set - JUST to be able to compare gCmd with another GCommand
		gCmd.setPreviousCmd(lastCmd);
		
		if(!lastCmd.equals(gCmd)){
			gcc.addCommand(gCmd);	
		}
	}

	public void descendWorkHead() {
		
		//GcommandTypes.G00
		GCommand gCmd = new GCommandG00(null, null, 0f);
		GCommand lastCmd = gcc.getGCommandList().get(gcc.getGCommandList().size()-1);
		
		//Hypothetically (preliminary) set - JUST to be able to compare gCmd with another GCommand
		gCmd.setPreviousCmd(lastCmd);
		
		if(!lastCmd.equals(gCmd)){
			gcc.addCommand(gCmd);	
		}		
	}
}
