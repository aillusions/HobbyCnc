package cnc.editor.doc;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;

import cnc.editor.doc.MyPlainDocument.EditSource;

public class MyDocumentEvent extends DefaultDocumentEvent{
	
	private static final long serialVersionUID = 1L;
	
	private EditSource editSource;
	
	public EditSource getEditSource() {
		return editSource;
	}

	public void setEditSource(EditSource editSource) {
		this.editSource = editSource;
	}

	public MyDocumentEvent(AbstractDocument abstractDocument, int offs,	int len, EventType type) {
		abstractDocument.super(offs, len, type);
	}

}
