package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

//Singleton
public class GCommandsContainer {
	
	
	private GCommandsContainer(){}                        
	
	private static final GCommandsContainer instance = new GCommandsContainer();
	
	public static GCommandsContainer getInstance(){
		return instance;
	}
	
	private List<ActionListener> listeners = new ArrayList<ActionListener>();
	
	private List<GCommand> gCommandList =  new LinkedList<GCommand>();
	private List<Line> lineList =  new LinkedList<Line>();
	
	public List<GCommand> getGCommandList() {
		return gCommandList;
	}

	public void regenerate(Document doc){
		gCommandList =  new LinkedList<GCommand>();
		lineList =  new LinkedList<Line>();
		
		String commands = null;
		try {
			commands = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
		String[] cmdArray = commands.replace("\r", "").split("\n");
		
		EditorVertex prevPos = new EditorVertex();
		
		for (int i = 0; i < cmdArray.length; i++) {
			GCommand gc = GCodeParser.parseCommand(cmdArray[i], prevPos);		
			
			if (gc != null) {
				gc.setEditorLineIndex(i);		
				gCommandList.add(gc);
				prevPos = gc.getVertex();
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

	public List<GCommand> findVertexesNear(float cncX, float cncY) {
		
		EditorStates es = EditorStates.getInstance();
		List<GCommand> result = new ArrayList<GCommand>();
		for(GCommand v : gCommandList){
			if(0.7/es.getScale() > Math.abs(v.getVertex().getX() - cncX) && 0.7/es.getScale() > Math.abs(v.getVertex().getY() - cncY)){
				result.add(v);
			}
		}
		return result;
	}

	public List<GCommand> getNeighbourVertexes(GCommand gCommand) {
		List<GCommand> result = new ArrayList<GCommand>();
		int indexOfCurrent = gCommandList.indexOf(gCommand);
		if(indexOfCurrent - 1 >= 0){
			result.add(gCommandList.get(indexOfCurrent - 1));
		}
		if(indexOfCurrent + 1 < gCommandList.size()){
			result.add(gCommandList.get(indexOfCurrent + 1));
		}
		return result;
	}
}
