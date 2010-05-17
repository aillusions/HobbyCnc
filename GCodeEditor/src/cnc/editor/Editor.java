package cnc.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import cnc.GCodeAcceptor;
import cnc.editor.view.EditorViewFrame;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.memory.BitMapArrayDataStorage;
import cnc.storage.memory.IDataStorage;

public class Editor {
	
	public enum EditModeS{DRAW, TXT};
	public enum EditorTolls{SIMPLE_EDIT, VERTEX_SELECT, CONTINUOUS_EDIT};

	private GCommandsContainer gcc = GCommandsContainer.getInstance();
	
	public void viewMousePressed(double x, double y){
		
		float cncX = EditorStates.convertView_Cnc((long)x);
		float cncY = EditorStates.convertView_Cnc((long)y);

		EditorStates es =  EditorStates.getInstance();
		boolean isCurrentSelectedToolReset = false;
		
		List<GCommand> vertexes = gcc.findVertexesNear(cncX, cncY);
		
		if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){
			String cmd = "\nG00 X" + cncX + " Y" + cncY;
			EditorVertex ev = gcc.getGCommandList().get(gcc.getGCommandList().size()-1).getVertex();
			gcc.addCommand(GCodeParser.parseCommand(cmd, ev));
			return;
		}
		
		if(vertexes != null && vertexes.size() > 0 && es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){
			es.setCurrentSelectedTool(EditorTolls.VERTEX_SELECT);
			isCurrentSelectedToolReset = true;
		}
		
		if(es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){			
			
			String cmd = "\nG00 X" + cncX + " Y" + cncY;
			EditorVertex ev = gcc.getGCommandList().get(gcc.getGCommandList().size()-1).getVertex();
			gcc.addCommand(GCodeParser.parseCommand(cmd, ev));
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
		if ((file = EditorViewFrame.openFileChooser("./parser", "bmp"))!= null) {
			
			EditorStates.getInstance().setImportInProgress(true);
			
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

			EditorStates.getInstance().setImportInProgress(false);
		}		
	}

	public void addGCodesFromFile() {
		
		File file = null;
		if ((file = EditorViewFrame.openFileChooser("./gcodes", "cnc"))!= null) {
			
			EditorStates.getInstance().setImportInProgress(true);
			
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
			
			EditorStates.getInstance().setImportInProgress(false);
		}	
	}
}
