package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cnc.GCodeAcceptor;
import cnc.editor.view.EditorViewFrame;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.memory.BitMapArrayDataStorage;
import cnc.storage.memory.IDataStorage;

public class Editor {
	
	public enum EditMode{DRAW, TXT};
	public enum EditorTolls{SIMPLE_EDIT, VERTEX_SELECT};
	
	private List<ActionListener> listeners = new ArrayList<ActionListener>();
	private GCommandsContainer gcc = GCommandsContainer.getInstance();
	
	public void viewMousePressed(double x, double y){
		
		float cncX = EditorStates.convertView_Cnc((long)x);
		float cncY = EditorStates.convertView_Cnc((long)y);

		EditorStates es =  EditorStates.getInstance();
		
		if(es.getCurrentSelectedTool() == EditorTolls.SIMPLE_EDIT){			
			
			String cmd = "\nG00 X" + cncX + " Y" + cncY;
			EditorVertex ev = gcc.getGCommandList().get(gcc.getGCommandList().size()-1).getVertex();
			gcc.addCommand(GCodeParser.parseCommand(cmd, ev));

		}else if(es.getCurrentSelectedTool() == EditorTolls.VERTEX_SELECT){
			
			List<GCommand> vertexes = gcc.findVertexesNear(cncX, cncY);
			if(vertexes.size() > 0){
				ActionEvent ae = new ActionEvent(vertexes , -1, "select node");
				notifyActionListeners(ae);
				List<GCommand> neighbourVertexes =  gcc.getNeighbourVertexes(vertexes.get(0));
				es.setSelectedVertex(vertexes.get(0), neighbourVertexes);
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

	public void addActionListener(ActionListener al) {
		listeners.add(al);		
	}
	
	private void notifyActionListeners(ActionEvent ae){
		
		for(ActionListener al : listeners){
			al.actionPerformed(ae);
		}
	}

}
