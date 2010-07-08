package cnc.editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import cnc.GCodeAcceptor;
import cnc.editor.domain.figure.FLine;
import cnc.editor.domain.figure.FLineG00;
import cnc.editor.domain.figure.FLineG01;
import cnc.editor.domain.figure.FLineG02;
import cnc.editor.domain.figure.FLineG03;
import cnc.editor.domain.figure.FPoint;
import cnc.editor.domain.gcmd.GCommand;
import cnc.editor.domain.gcmd.GCommandG00;
import cnc.editor.domain.gcmd.GCommandG02;
import cnc.editor.domain.gcmd.GCommandG03;
import cnc.editor.view.EditorMainFrame;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.memory.BitMapArrayDataStorage;
import cnc.storage.memory.IDataStorage;

public class Editor {
	
	public enum EditorTolls{SIMPLE_EDIT, VERTEX_SELECT/*, CONTINUOUS_EDIT*/};
	public enum GcommandTypes{/*ORIGIN,*/ G00, G01, G02, G03}

	//private GCommandsContainer gcc = GCommandsContainer.getInstance();
	private FiguresContainer gcc = FiguresContainer.getInstance();
	private EditorStates es = EditorStates.getInstance();
	
	//private boolean continuousDrawStarted = false;
	private boolean dragStarted = false;
	private double prevDragX;
	private double prevDragY;
	
	public void mousePressedAt(double x, double y, boolean ctrl){
		
		float cncX = EditorStates.convertPositionView_Cnc((long)x);
		float cncY = EditorStates.convertPositionView_Cnc((long)y);
		
		boolean isCurrentSelectedToolReset = false;
		
		//List<GCommand> vertexes = gcc.findVertexesNear(cncX, cncY);
				
		//Start moving selected items
/*		if(vertexes != null && vertexes.size() > 0 && es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){
			es.setCurrentSelectedTool(EditorTolls.VERTEX_SELECT);
			isCurrentSelectedToolReset = true;
		}*/
		
/*		if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){				
			
			continuousDrawStarted = true;
			
			liftHeadOrDownIfNeeded();			
			viewMouseDraggedTo(x, y);
			
			if(es.isLiftForEachStroke()){
				downWorkHead();
			}
		}*/
		
		if(es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){			
						
//			if(es.isLiftForEachStroke()){
//				
//				GCommand end = null;				
//				GCommand start = new GCommandG00(cncX, cncY, null);
//				
//				GcommandTypes cType = es.getCurrentGCmdType();
//				
//				if(cType == GcommandTypes.G00){					
//					end = new GCommandG00(cncX, cncY, null);
//				}else if(cType == GcommandTypes.G01){
//					end = new GCommandG01(cncX, cncY, null);
//				}else if(cType == GcommandTypes.G02){
//					if(es.getArcR() != null){
//						end = new GCommandG02(cncX, cncY, null, es.getArcR());
//					}else{
//						end = new GCommandG02(cncX, cncY, null, es.getArcI(), es.getArcJ());
//					}
//				}else if(cType == GcommandTypes.G03) {
//					if(es.getArcR() != null){
//						end = new GCommandG03(cncX, cncY, null, es.getArcR());
//					}else{
//						end = new GCommandG03(cncX, cncY, null, es.getArcI(), es.getArcJ());
//					}
//				}
//				
//				liftHeadOrDownIfNeeded();	
//				gcc.addCommand(start);	
//				downWorkHead();				
//				gcc.addCommand(end);
//								
//				List<GCommand> oneCmdList = new ArrayList<GCommand>();
//				oneCmdList.add(end);
//					
//				es.setSelectedGCommands(oneCmdList);
//					
//				prevDragX = x;
//				prevDragY = y;
//				dragStarted = true;
//				
//			}else{	
				
				//GcommandTypes cType = es.getCurrentGCmdType();
				
/*				GCommand cmd = null;
				
				if(cType == GcommandTypes.G00){					
					cmd = new GCommandG00(cncX, cncY, null);
				}else if(cType == GcommandTypes.G01){
					cmd = new GCommandG01(cncX, cncY, null);
				}else if(cType == GcommandTypes.G02){
					if(es.getArcR() != null){
						cmd = new GCommandG02(cncX, cncY, null, es.getArcR());
					}else{
						cmd = new GCommandG02(cncX, cncY, null, es.getArcI(), es.getArcJ());
					}
				}else if(cType == GcommandTypes.G03) {
					if(es.getArcR() != null){
						cmd = new GCommandG03(cncX, cncY, null, es.getArcR());
					}else{
						cmd = new GCommandG03(cncX, cncY, null, es.getArcI(), es.getArcJ());
					}
				}*/
				
				gcc.getCurrentFigure().addPoint(cncX, cncY/*, cType*/);
				
				es.setCurrentSelectedTool(EditorTolls.VERTEX_SELECT);
				isCurrentSelectedToolReset = true;
			//}
		}
		
		if(es.getCurrentSelectedTool() == EditorTolls.VERTEX_SELECT){
			
			List<FPoint> vertexes;
			
			vertexes = gcc.findPointsNear(cncX, cncY);

			if(vertexes.size() > 0){
				
				List<FPoint> oneCmdList = new ArrayList<FPoint>();
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
		
/*		if(continuousDrawStarted){
			continuousDrawStarted = false;
		}	*/	
		
		dragStarted = false;
		prevDragX = 0;
		prevDragY = 0;
		es.clearSelRegion();
	}
	
	public void viewMouseDraggedTo(double x, double y){
			
//		if(continuousDrawStarted){
//			
//			float cncX = EditorStates.convertPositionView_Cnc((long)x);
//			float cncY = EditorStates.convertPositionView_Cnc((long)y);
//			
//			if(es.getCurrentSelectedTool() == EditorTolls.CONTINUOUS_EDIT){
//				
//				if(es.getCurrentGCmdType() != GcommandTypes.G01){
//					System.err.println("Continuous drawing works only for " + GcommandTypes.G01 + "command type");
//					return;
//				}
//				
//				gcc.addCommand(new GCommandG01(cncX, cncY, null));
//				return;
//			}
//			
//		}else 
		if(dragStarted){
				
			float shiftX = EditorStates.convertLengthView_Cnc((long)(prevDragX - x));
			float shiftY = EditorStates.convertLengthView_Cnc((long)(prevDragY - y));
			
			List<FPoint> gcs = es.getSelectedGCommands();
			if(gcs != null && gcs.size() > 0){

				for(FPoint gc : gcs){					
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
			
			List<FPoint> cmds = gcc.findVertexesInRegion(startX, startY, endX, endY);
			es.setSelectedGCommands(cmds);
		}				
				
	}
	
	public void convertImageToGCodes() {
		
		File file = null;
		List<String> exts = new ArrayList<String>();
		exts.add("bmp");
		
		if ((file = EditorMainFrame.openFileChooser("./parser", exts))!= null) {
			
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
			bmpPrinter.startBuild();	
			
			//gcc.clear();
			//gcc.addCommandsBunch(codesBuffer.toString().trim());			

			es.setImportInProgress(false);
		}		
	}

	public void addGCodesFromFile() {
		
		File file = null;
		List<String> exts = new ArrayList<String>();
		exts.add("ncc");
		
		if ((file = EditorMainFrame.openFileChooser("./gcodes", exts))!= null) {
			
			es.setImportInProgress(true);
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				
				String line = null;
				final StringBuffer codesBuffer = new StringBuffer();
				
				while ((line = br.readLine()) != null) {
					
					if(!line.trim().equals("")){
						codesBuffer.append("\r\n" + line);
					}
				}
				
				gcc.clear();
				gcc.addCommandsBunch(codesBuffer.toString().trim());
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			es.setImportInProgress(false);
		}	
	}
	
	public static FLine createLine(FPoint pFrom, FPoint pTo, GCommand gCmd){
		
		FLine line = null;
		
		Editor.GcommandTypes lineType = gCmd.getCommandType();
		
		if(lineType == GcommandTypes.G00){					
			line = new FLineG00(pFrom, pTo);
		}else if(lineType == GcommandTypes.G01){
			line = new FLineG01(pFrom, pTo);
		}else if(lineType == GcommandTypes.G02){
			GCommandG02 g02 = (GCommandG02)gCmd;
			if(g02.getRadius() != null){
				line = new FLineG02(pFrom, pTo, g02.getRadius());
			}else{
				line = new FLineG02(pFrom, pTo, g02.getI(), g02.getJ());
			}
		}else if(lineType == GcommandTypes.G03) {
			
			GCommandG03 g03 = (GCommandG03)gCmd;
			if(g03.getRadius() != null){
				line = new FLineG03(pFrom, pTo, g03.getRadius());
			}else{
				line = new FLineG03(pFrom, pTo, g03.getI(), g03.getJ());
			}
		}
		
		return line;
	}
	
	public static FLine createLine(FPoint pFrom, FPoint pTo){
		
		FLine line = null;
		
		EditorStates es = EditorStates.getInstance();		
		Editor.GcommandTypes lineType = es.getCurrentGCmdType();
		
		if(lineType == GcommandTypes.G00){					
			line = new FLineG00(pFrom, pTo);
		}else if(lineType == GcommandTypes.G01){
			line = new FLineG01(pFrom, pTo);
		}else if(lineType == GcommandTypes.G02){
			if(es.getArcR() != null){
				line = new FLineG02(pFrom, pTo, es.getArcR());
			}else{
				line = new FLineG02(pFrom, pTo, es.getArcI(), es.getArcJ());
			}
		}else if(lineType == GcommandTypes.G03) {
			if(es.getArcR() != null){
				line = new FLineG03(pFrom, pTo, es.getArcR());
			}else{
				line = new FLineG03(pFrom, pTo, es.getArcI(), es.getArcJ());
			}
		}
		
		return line;
	}

	public void undo() {
	
	}


	public void redo() {
		
	}


	public void save() {
		File file = null;
		List<String> exts = new ArrayList<String>();
		exts.add("ncc");
		
		if ((file = EditorMainFrame.openFileChooser("./gcodes", exts))!= null) {
			
			es.setImportInProgress(true);
			
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file)));
				
				FPoint lastPointTo = null;
				
				for(FLine fLine : gcc.getAllLinesList()){
					
					if(lastPointTo == null || !lastPointTo.equals(fLine.getPointFrom())){
						
						bw.write(new GCommandG00(null, null, 2f) + "\r\n");
						bw.write(new GCommandG00(fLine.getPointFrom().getX(), fLine.getPointFrom().getY(), null) + "\r\n");
						bw.write(new GCommandG00(null, null, es.getCuttingDepth()) + "\r\n");
					}
							
					//bw.write(new GCommandG00(fLine.getPointTo().getX(), fLine.getPointTo().getY(), null) + "\r\n");
					bw.write(fLine.toString()+ "\r\n");
					
					lastPointTo = fLine.getPointTo();
				}
				bw.flush();
				bw.close();
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			es.setImportInProgress(false);
		}	
	}
		
}
