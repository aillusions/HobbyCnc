package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

//Singleton
public class VertexesContainer {
	
	
	private VertexesContainer(){}                        
	
	private static final VertexesContainer instance = new VertexesContainer();
	
	public static VertexesContainer getInstance(){
		return instance;
	}
	
	List<ActionListener> listeners = new ArrayList<ActionListener>();
	
	private List<EditorVertex> vertexList =  new LinkedList<EditorVertex>();
	private List<Line> lineList =  new LinkedList<Line>();
	
	public List<EditorVertex> getVertexList() {
		return vertexList;
	}

	public void regenerate(Document doc){
		vertexList =  new LinkedList<EditorVertex>();
		lineList =  new LinkedList<Line>();
		
		String commands = null;
		try {
			commands = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
		String[] cmdArray = commands.replace("\r", "").split("\n");
		
		EditorVertex prevPos = new EditorVertex();
		vertexList.add(new EditorVertex());
		
		for (int i = 0; i < cmdArray.length; i++) {
			GCommand gc = GCodeParser.parseCommand(cmdArray[i], prevPos);			
			if (gc != null) {			
				EditorVertex newPos = gc.getCoord();
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
