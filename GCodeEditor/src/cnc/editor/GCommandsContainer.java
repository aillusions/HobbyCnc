package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cnc.editor.GCommand.GcommandTypes;

//Singleton
public class GCommandsContainer implements ActionListener {	
	
	private GCommandsContainer(){
		gCommandList.add(new GCommand(GcommandTypes.G00, new EditorVertex(0, 0, 0)));
	}                        
	
	private static final GCommandsContainer instance = new GCommandsContainer();
	
	public static GCommandsContainer getInstance(){
		return instance;
	}
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();
	
	private final List<GCommand> gCommandList =  new LinkedList<GCommand>();
	
	public List<GCommand> getGCommandList() {
		return gCommandList;
	}
	
	public void addCommand(GCommand c){
		gCommandList.add(c);
		c.getVertex().addActionListener(this);
		notifyAllAboutChanges(null);
	}

	public void addCommandsBunch(String commands){
			
		String[] cmdArray = commands.replace("\r", "").split("\n");
		
		EditorVertex prevPos = new EditorVertex();
		
		for (int i = 0; i < cmdArray.length; i++) {
			GCommand gc = GCodeParser.parseCommand(cmdArray[i], prevPos);		
			
			if (gc != null) {
				//gc.setEditorLineIndex(i);		
				gCommandList.add(gc);
				prevPos = gc.getVertex();
			}
		}
		notifyAllAboutChanges(null);		
	}
	
	public void addActionListener(ActionListener al){
		listeners.add(al);
	}
	
	private void notifyAllAboutChanges(ActionEvent e){
		
		for(ActionListener al : listeners){
			ActionEvent ae = new ActionEvent(this , -1, "chemaChanged");
			al.actionPerformed(ae);
		}
	}

	public List<GCommand> findVertexesNear(float cncX, float cncY) {
		
		EditorStates es = EditorStates.getInstance();
		List<GCommand> result = new ArrayList<GCommand>();
		for(GCommand v : gCommandList){
			if(0.7/es.getScale() > Math.abs(v.getVertex().getX() - cncX) 
					&& 0.7/es.getScale() > Math.abs(v.getVertex().getY() - cncY)){
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

	public void clear() {
		gCommandList.clear();	
		gCommandList.add(new GCommand(GcommandTypes.G00, new EditorVertex(0, 0, 0)));
		notifyAllAboutChanges( new ActionEvent(this , -1, "clear"));
	}

	public void actionPerformed(ActionEvent e) {
		notifyAllAboutChanges(e);
	}
}
