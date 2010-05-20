package cnc.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cnc.GCodeAcceptor;
import cnc.editor.EditorStates.SelectedRegion;
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
	
	private boolean continuousDrawStarted = false;
	private boolean dragStarted = false;
	private double prevDragX;
	private double prevDragY;
	
	public void mousePressedAt(double x, double y, boolean ctrl){
		
		es.setCurrentEditMode(Editor.EditModeS.DRAW);
		
		float cncX = EditorStates.convertPositionView_Cnc((long)x);
		float cncY = EditorStates.convertPositionView_Cnc((long)y);
		
		boolean isCurrentSelectedToolReset = false;
		
		List<GCommand> vertexes = gcc.findVertexesNear(cncX, cncY);
				
		//Start moving selected items
		if(vertexes != null && vertexes.size() > 0 && es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){
			es.setCurrentSelectedTool(EditorTolls.VERTEX_SELECT);
			isCurrentSelectedToolReset = true;
		}
		
		if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){				
			
			continuousDrawStarted = true;
			
			liftHeadOrDownIfNeeded();			
			viewMouseDraggedTo(x, y);
			
			if(es.isLiftForEachStroke()){
				downWorkHead();
			}
		}
		
		if(es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){			
						
			if(es.isLiftForEachStroke()){
				
				GCommand start = null;
				GCommand end = null;
				
				if(es.getCurrentGCmdType() == GcommandTypes.G00){
					start = new GCommandG00(cncX, cncY, null);
					end = new GCommandG00(cncX, cncY, null);
				}
				else{
					start = new GCommandG02(cncX, cncY, null, es.getG02Radius());
					end = new GCommandG02(cncX, cncY, null, es.getG02Radius());
				}
				
				liftHeadOrDownIfNeeded();	
				gcc.addCommand(start);	
				downWorkHead();				
				gcc.addCommand(end);
								
				List<GCommand> oneCmdList = new ArrayList<GCommand>();
				oneCmdList.add(end);
					
				es.setSelectedGCommands(oneCmdList);
					
				prevDragX = x;
				prevDragY = y;
				dragStarted = true;
				
			}else{				
				if(es.getCurrentGCmdType() == GcommandTypes.G00)
					gcc.addCommand(new GCommandG00(cncX, cncY, null));
				else
					gcc.addCommand(new GCommandG02(cncX, cncY, null, es.getG02Radius()));
				
				es.setCurrentSelectedTool(EditorTolls.VERTEX_SELECT);
				isCurrentSelectedToolReset = true;
			}
		}
		
		if(es.getCurrentSelectedTool() == EditorTolls.VERTEX_SELECT){

			vertexes = gcc.findVertexesNear(cncX, cncY);

			if(vertexes.size() > 0){
				
				List<GCommand> oneCmdList = new ArrayList<GCommand>();
				oneCmdList.add(vertexes.get(0));
				
				if(es.getSelectedGCommands() != null && es.getSelectedGCommands().containsAll(vertexes)){
					
					//remove from selected:
					if(ctrl){
						es.removeGCommandsFromSelected(vertexes);
					}
				}else if(ctrl){
					es.addToSelectedGCommands(oneCmdList);
				}else{
					es.setSelectedGCommands(oneCmdList);
				}	
				
				prevDragX = x;
				prevDragY = y;
				dragStarted = true;	
				
			}else{
				if(!ctrl){
					es.clearSelection();
					es.getSelRegion().startSelection((int)x, (int)y);
				}
			}
			
			if(isCurrentSelectedToolReset){
				es.setCurrentSelectedTool(EditorTolls.SIMPLE_EDIT);
			}		
			
		}
	}
	
	
	public void viewMouseReleasedAt(double x, double y){
		
		if(continuousDrawStarted){
			continuousDrawStarted = false;
		}		
		
		dragStarted = false;
		prevDragX = 0;
		prevDragY = 0;
		es.clearSelRegion();
	}
	
	public void viewMouseDraggedTo(double x, double y){
			
		if(continuousDrawStarted){
			
			float cncX = EditorStates.convertPositionView_Cnc((long)x);
			float cncY = EditorStates.convertPositionView_Cnc((long)y);
			
			if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){
				
				if(es.getCurrentGCmdType() != GcommandTypes.G00){
					System.err.println("Continuous drawing works only for " + GcommandTypes.G00 + "command type");
					return;
				}
				
				gcc.addCommand(new GCommandG00(cncX, cncY, null));
				return;
			}
			
		}else if(dragStarted){
				
			float shiftX = EditorStates.convertLengthView_Cnc((long)(prevDragX - x));
			float shiftY = EditorStates.convertLengthView_Cnc((long)(prevDragY - y));
			
			Set<GCommand> gcs = es.getSelectedGCommands();
			if(gcs != null && gcs.size() > 0){

				for(GCommand gc : gcs){					
					if(gc != null){							
						gc.setX(gc.getX() - shiftX);
						gc.setY(gc.getY() - shiftY);
						
						prevDragX = x;
						prevDragY = y;
					}
				}
			}
			
		}else if(es.getSelRegion().isSelectionStarted()){
					
			SelectedRegion sr = es.getSelRegion();
			sr.setEndOfSelection((int)x, (int)y);
			
			float startX = EditorStates.convertPositionView_Cnc(sr.getStartX());
			float startY = EditorStates.convertPositionView_Cnc(sr.getStartY());
			float endX = EditorStates.convertPositionView_Cnc(sr.getEndX());
			float endY = EditorStates.convertPositionView_Cnc(sr.getEndY());
			
			List<GCommand> cmds = gcc.findVertexesInRegion(startX, startY, endX, endY);
			es.setSelectedGCommands(cmds);
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

	public void downWorkHead() {
		
		//GcommandTypes.G00
		GCommand gCmd = new GCommandG00(null, null, 0f);
		GCommand lastCmd = gcc.getGCommandList().get(gcc.getGCommandList().size()-1);
		
		//Hypothetically (preliminary) set - JUST to be able to compare gCmd with another GCommand
		gCmd.setPreviousCmd(lastCmd);
		
		if(!lastCmd.equals(gCmd)){
			gcc.addCommand(gCmd);	
		}		
	}
	
	private void liftHeadOrDownIfNeeded(){
		
		if(es.isLiftForEachStroke()){
			
			List<GCommand> cmdList = gcc.getGCommandList();
			int lastIndex = cmdList.size() - 1;
			GCommand lastCmd = null;
			GCommand preLastCmd = null;
			
			if(lastIndex > -1){
				lastCmd = cmdList.get(lastIndex);
			}
			if((lastIndex - 1) > -1){
				preLastCmd = cmdList.get(lastIndex - 1);
			}
			
			if(es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){	
					
				if(lastCmd.getZ() > 0 && (preLastCmd != null)){				
					downWorkHead();
				}else if(preLastCmd != null && preLastCmd.getZ() <= 0){
					liftWorkHead();
				}
					
			}else if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){		
				if(preLastCmd != null && preLastCmd.getZ() <= 0){
					liftWorkHead();
				}else if(lastCmd.getZ() > 0){
					
				}
			}
		}
	}


	public void undo() {
	
	}


	public void redo() {
		
	}

}
