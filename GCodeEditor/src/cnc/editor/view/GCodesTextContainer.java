package cnc.editor.view;

import javax.swing.JTextArea;
import javax.swing.text.Document;

import cnc.editor.listener.GCodesTextContainerListener;

public class GCodesTextContainer extends JTextArea {
	
	private static final long serialVersionUID = 1L;

	public GCodesTextContainer(Document doc, GCodesTextContainerListener listener) {
		super(doc);
		addFocusListener(listener);
		addKeyListener(listener);
		addMouseListener(listener);
	}

}
