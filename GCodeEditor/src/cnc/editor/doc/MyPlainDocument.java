package cnc.editor.doc;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.PlainDocument;

public class MyPlainDocument extends PlainDocument{

	public enum EditSource{GUI, TXT, DEFAULT}
	
	private static final long serialVersionUID = 1L;

	private EditSource editSource;

	public EditSource getEditSource() {
		return editSource;
	}

	public void setEditSource(EditSource editSource) {
		this.editSource = editSource;
	}
		
    protected void fireInsertUpdate(DocumentEvent e) {
    	MyDocumentEvent myEvent = new MyDocumentEvent((AbstractDocument)e.getDocument(),e.getOffset(), e.getLength(),e.getType());
    	myEvent.setEditSource(editSource);
    	super.fireInsertUpdate(myEvent);

    }

    protected void fireChangedUpdate(DocumentEvent e) {
    	MyDocumentEvent myEvent = new MyDocumentEvent((AbstractDocument)e.getDocument(),e.getOffset(), e.getLength(),e.getType());
    	myEvent.setEditSource(editSource);
    	super.fireChangedUpdate(myEvent);
    }

    protected void fireRemoveUpdate(DocumentEvent e) {
      	MyDocumentEvent myEvent = new MyDocumentEvent((AbstractDocument)e.getDocument(),e.getOffset(), e.getLength(),e.getType());
      	myEvent.setEditSource(editSource);
      	super.fireRemoveUpdate(myEvent);

    }	
}
