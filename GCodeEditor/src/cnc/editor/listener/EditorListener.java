package cnc.editor.listener;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.text.BadLocationException;

import cnc.editor.EditorStates;
import cnc.editor.GCommand;
import cnc.editor.view.GCodesTextContainer;

public class EditorListener implements ActionListener{

	private GCodesTextContainer gCodesTextContainer;
	
	public EditorListener(GCodesTextContainer gCodesTextContainer) {
		
		this.gCodesTextContainer = gCodesTextContainer;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		try {
			
			List<GCommand> vertexes = (List<GCommand>)arg0.getSource();
			
			GCommand gCommand1 = vertexes.get(0);
			
			int editorLineIndex = EditorStates.getLineNumberInTextEditor(gCommand1);
			int lineStart = gCodesTextContainer.getLineStartOffset(editorLineIndex);
			int lineEnd = gCodesTextContainer.getLineEndOffset(editorLineIndex);

			gCodesTextContainer.select(lineStart, lineEnd);
			gCodesTextContainer.scrollRectToVisible(new Rectangle(1,1,1,1));

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}