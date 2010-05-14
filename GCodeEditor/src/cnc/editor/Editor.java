package cnc.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import cnc.GCodeAcceptor;
import cnc.editor.view.EditorStates;
import cnc.editor.view.EditorViewFrame;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.memory.BitMapArrayDataStorage;
import cnc.storage.memory.IDataStorage;

public class Editor {
	
	private Document doc;
	
	public Editor(){		
		doc = new PlainDocument();
	}
	
	public void viewMousePressed(double x, double y){
		
		EditorStates es = EditorStates.getInstance();
		String cmd = "\nG00 X" + Math.round(x/es.getScale()/5) + " Y"	+ Math.round(y/es.getScale()/5);
		try {
			doc.insertString(doc.getLength(), cmd, null);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void convertImageToGCodes() {
		
		File file = null;
		if ((file = EditorViewFrame.openFileChooser("./parser", "bmp"))!= null) {
			
			clearDocument();
			
			IDataStorage store = new BitMapArrayDataStorage();
			store.clearStorage();
			
			BmpParser parser = new BmpParser();
			parser.setStorage(store);
			long qty = parser.loadbitmap(file.getPath());
			
			final StringBuffer codesBuffer = new StringBuffer();
			
			BmpFilePrinter bmpPrinter = new BmpFilePrinter(new GCodeAcceptor(){
				public void putGCode(String gcode) {
					codesBuffer.append("\r\n" + gcode);					
				}				
			});			
			
			bmpPrinter.setStore(store);
			bmpPrinter.StartBuild();	
			
			try {
				doc.insertString(1, codesBuffer.toString(), null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}		
	}

	public void addGCodesFromFile() {
		
		File file = null;
		if ((file = EditorViewFrame.openFileChooser("./gcodes", "cnc"))!= null) {
			
			clearDocument();
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				
				String line = null;
				final StringBuffer codesBuffer = new StringBuffer();
				
				while ((line = br.readLine()) != null) {
					codesBuffer.append("\r\n" + line);
				}
							
				doc.insertString(1, codesBuffer.toString(), null);	
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}	
	}
	
	public void clearDocument() {
		
		try {
			getDoc().remove(0, getDoc().getLength());
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	public Document getDoc() {		
		return doc;
	}

	public void setDoc(Document doc) {		
		this.doc = doc;
	}
}
