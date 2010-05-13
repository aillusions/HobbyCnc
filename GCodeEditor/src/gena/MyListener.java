package gena;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;




//editor
public class MyListener implements ActionListener{

	private Document doc;
	public MyListener(){
		doc = new PlainDocument();
	}
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public void actionPerformed(ActionEvent e) {		
		if(e.getActionCommand().equals("ABC")){
			try {
				doc.insertString(1, "aaaaaaaaaaaaaaaaa", null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
