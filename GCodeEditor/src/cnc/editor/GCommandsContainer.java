package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cnc.editor.GCommand.GcommandTypes;

//Singleton
public class GCommandsContainer implements ActionListener {	
	
	public static final String CMD_CLEAR_COMMANDS_CONATINER = "clear_commands_conatiner";
	public static final String CMD_ADDED_BUNCH_OF_COMMANDS = "added_bunch_of_commands";
	public static final String CMD_ADDED_ONE_COMMAND = "added_one_command";

	private GCommandsContainer(){
		addCommand(new GCommand(GcommandTypes.G00, new EditorVertex(0, 0, 0)));
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
		ActionEvent ae = new ActionEvent(this , -1, CMD_ADDED_ONE_COMMAND);
		notifyAllAboutChanges(ae);
	}

	public void addCommandsBunch(String commands){
			
		String[] cmdArray = commands.replace("\r", "").split("\n");
		
		EditorVertex prevPos = new EditorVertex();
		
		for (int i = 0; i < cmdArray.length; i++) {
			GCommand gc = GCodeParser.parseCommand(cmdArray[i], prevPos);		
			
			if (gc != null) {
				gCommandList.add(gc);
				gc.getVertex().addActionListener(this);
				prevPos = gc.getVertex();
			}
		}

		ActionEvent ae = new ActionEvent(this , -1, CMD_ADDED_BUNCH_OF_COMMANDS);
		notifyAllAboutChanges(ae);		
	}
	
	public void addActionListener(ActionListener al){
		listeners.add(al);
		ActionEvent ae = new ActionEvent(this , -1, CMD_ADDED_BUNCH_OF_COMMANDS);
		al.actionPerformed(ae);
	}
	
	private void notifyAllAboutChanges(ActionEvent e){		
		for(ActionListener al : listeners){
			al.actionPerformed(e);
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
		notifyAllAboutChanges(new ActionEvent(this , -1, CMD_CLEAR_COMMANDS_CONATINER));
		addCommand(new GCommand(GcommandTypes.G00, new EditorVertex(0, 0, 0)));
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(EditorVertex.CMD_COORDINATE_CHANGED)){
			notifyAllAboutChanges(e);
		}
	}
}
