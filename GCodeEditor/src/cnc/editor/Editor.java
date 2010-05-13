package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import cnc.GCodeAcceptor;
import cnc.parser.bmp.BmpFilePrinter;
import cnc.parser.bmp.BmpParser;
import cnc.storage.IDataStorage;
import cnc.storage.light.BitMapArrayDataStorage;

public class Editor implements GCodeAcceptor, ActionListener{
	
	private Document doc;
	
	public Editor(){
		doc = new PlainDocument();
	}
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	public void putGCode(String gcode) {
		System.out.println(gcode);
		try {
			doc.insertString(1, "\r\n" + gcode, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void convertImageToGCodes() {

		final JFileChooser fc = new JFileChooser(new File("./parser"));
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public boolean accept(File f) {
				String ext = f.getName().substring(f.getName().indexOf(".") + 1);
				if (f.isDirectory() || (f.isFile() && ext.equals("bmp"))) {
					return true;
				}
				return false;
			}
		});

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == 0) {
			
			//clear view
			//txtArea_GCodes.setText("");
			//pnl_GraphicOutput.repaint();
			
			IDataStorage store = new BitMapArrayDataStorage();
			store.clearStorage();
			
			BmpParser parser = new BmpParser();
			parser.setStorage(store);
			long qty = parser.loadbitmap(fc.getSelectedFile().getPath());
			
			BmpFilePrinter bmpPrinter = new BmpFilePrinter(this);			
			bmpPrinter.setStore(store);
			bmpPrinter.StartBuild();			
		}		
	}

	private void addGCodesFromFile() {

		final JFileChooser fc = new JFileChooser(new File("./gcodes"));
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public boolean accept(File f) {
				String ext = f.getName()
						.substring(f.getName().indexOf(".") + 1);
				if (f.isDirectory() || (f.isFile() && ext.equals("cnc"))) {
					return true;
				}
				return false;
			}
		});

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == 0) {
			//txtArea_GCodes.setText("");
			//pnl_GraphicOutput.repaint();
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fc.getSelectedFile())));
				String line = null;
				while ((line = br.readLine()) != null) {
					putGCode(line);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}	
	}
	
	private void scale(float ratio) {
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
		//txtArea_GCodes.setText("");
		//pnl_GraphicOutput.repaint();		
		//pnl_GraphicOutput.setPreferredSize(new Dimension(1, 1));
		//pnl_GraphicOutput.revalidate();
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("ConvertImageToGCodes")){
			convertImageToGCodes();
		}else if(e.getActionCommand().equals("AddGCodesFromFile")){
			addGCodesFromFile();
		}else if(e.getActionCommand().equals("Scale")){
			Float.parseFloat(((JComboBox)e.getSource()).getSelectedItem().toString());
			scale(1);
		}else if(e.getActionCommand().equals("Clear")){
			clear();
			System.out.println();
		}
	}
}
