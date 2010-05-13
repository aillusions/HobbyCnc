package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JComboBox;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import cnc.GCodeAcceptor;
import cnc.editor.view.EditorViewFrame;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.IDataStorage;
import cnc.storage.light.BitMapArrayDataStorage;

public class Editor implements GCodeAcceptor{
	
	private Document doc;
	
	public Editor(){
		doc = new PlainDocument();
	}
	
	public void putGCode(String gcode) {
		try {
			doc.insertString(1, "\r\n" + gcode, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void convertImageToGCodes() {
		File file = null;
		if ((file = EditorViewFrame.openFileChooser("./parser", "bmp"))!= null) {
			
			IDataStorage store = new BitMapArrayDataStorage();
			store.clearStorage();
			
			BmpParser parser = new BmpParser();
			parser.setStorage(store);
			long qty = parser.loadbitmap(file.getPath());
			
			BmpFilePrinter bmpPrinter = new BmpFilePrinter(this);			
			bmpPrinter.setStore(store);
			bmpPrinter.StartBuild();			
		}		
	}

	public void addGCodesFromFile() {
		File file = null;
		if ((file = EditorViewFrame.openFileChooser("./gcodes", "cnc"))!= null) {
		
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				String line = null;
				while ((line = br.readLine()) != null) {
					putGCode(line);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}	
	}
	
	public void scale(float ratio) {
		//scale = Float.parseFloat(comBox_Scale.getSelectedItem().toString());
		//pnl_GraphicOutput.setScale(ratio);
		//pnl_GraphicOutput.repaint();
	}
	
	protected void clear() {
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}
}
