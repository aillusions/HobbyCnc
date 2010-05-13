package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class VertexesContainer {
	
	List<ActionListener> listeners = new ArrayList<ActionListener>();
	
	private List<Vertex> vertexList =  new LinkedList<Vertex>();
	private List<Line> lineList =  new LinkedList<Line>();
	
	public List<Vertex> getVertexList() {
		return vertexList;
	}

	public void regenerate(Document doc){
		vertexList =  new LinkedList<Vertex>();
		lineList =  new LinkedList<Line>();
		
		String commands = null;
		try {
			commands = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
		String[] cmdArray = commands.replace("\r", "").split("\n");
		
		Vertex prevPos = new Vertex();
		vertexList.add(new Vertex());
		
		for (int i = 0; i < cmdArray.length; i++) {
			GCommand gc = GCodeParser.parseCommand(cmdArray[i], prevPos);			
			if (gc != null) {			
				Vertex newPos = gc.getCoord();
				vertexList.add(newPos);
				prevPos = newPos;
			}
		}
		notifyAllAboutChanges();		
	}
	
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(){
		for(ActionListener al : listeners){
			ActionEvent ae = new ActionEvent(this , -1, "chemaChanged");
			al.actionPerformed(ae);
		}
	}
}
