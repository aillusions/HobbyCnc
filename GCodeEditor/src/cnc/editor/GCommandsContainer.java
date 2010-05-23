package cnc.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cnc.editor.Editor.GcommandTypes;
import cnc.editor.listener.GCodesDocListener;

//Singleton
public class GCommandsContainer implements ActionListener {	
	
	public static final String CMD_COMMAND_REPLACED_MANUALLY = "commandReplacedManually";
	public static final String CMD_REMOVED_COMMANDS = "removedCommands";
	public static final String CMD_CLEAR_COMMANDS_CONATINER = "clear_commands_conatiner";
	public static final String CMD_ADDED_BUNCH_OF_COMMANDS = "added_bunch_of_commands";
	public static final String CMD_ADDED_ONE_COMMAND = "added_one_command";
	
	private static final GCommandsContainer instance = new GCommandsContainer();
	
	private final List<ActionListener> listeners = new ArrayList<ActionListener>();	
	private final List<GCommand> gCommandList =  new LinkedList<GCommand>();
	
	private boolean butchOfCmdsAddingInProgress = false; 
		
	private GCommandsContainer(){
		addCommand(new GCommandOrigin(), this);
	}                        
	
	public static GCommandsContainer getInstance(){
		return instance;
	}
		
	public List<GCommand> getGCommandList() {
		return gCommandList;
	}
	
	public void replaceCommandManually(GCommand oldCmd, GCommand newCmd, Object source){
		
		int oldCommandIndex = getGCommandList().indexOf(oldCmd);	
		
		newCmd.setPreviousCmd(oldCmd.getPreviousCmd());
		
		
		oldCmd.setPreviousCmd(null);
		
		if(getGCommandList().size() > (oldCommandIndex + 1)){
			getGCommandList().get(oldCommandIndex + 1).setPreviousCmd(newCmd);
		}
		
		getGCommandList().set(oldCommandIndex, newCmd);
		
		newCmd.addActionListener(this);
		ActionEvent ae = new ActionEvent(this , -1, CMD_COMMAND_REPLACED_MANUALLY);
		ae.setSource(source);
		notifyAllAboutChanges(ae);	
	}
	
	public void removeGCommands(Collection<GCommand> c){
		
		if(c == null){
			return; 
		}
		
		for(GCommand cmd : c){
			
			if(cmd.getCommandType() != GcommandTypes.ORIGIN){
				int rmIndex = gCommandList.indexOf(cmd);
				
				if((rmIndex + 1) < gCommandList.size() && rmIndex >= 0){
					gCommandList.get(rmIndex + 1).setPreviousCmd(gCommandList.get(rmIndex - 1));
				}
				
				gCommandList.remove(rmIndex);
			}
		}
		
		ActionEvent ae = new ActionEvent(this , -1, CMD_REMOVED_COMMANDS);
		notifyAllAboutChanges(ae);
	}
	
	public void addCommand(GCommand c, Object source){
		
		if(c == null){
			throw new RuntimeException("Argument of " + GCommandsContainer.class.toString()+ ".addCommand(..) can not be null.");
		}
		if(gCommandList.size() > 0){
			c.setPreviousCmd(gCommandList.get(gCommandList.size() -1));
		}
		
		gCommandList.add(c);
		c.addActionListener(this);
		
		if(!butchOfCmdsAddingInProgress) {
			ActionEvent ae = new ActionEvent(source , -1, CMD_ADDED_ONE_COMMAND);
			notifyAllAboutChanges(ae);
		}
	}

	public void addCommandsBunch(String commands, Object source){
		
		butchOfCmdsAddingInProgress = true;
		
		//if(gCommandList.size() == 0){
	//		addCommand(new GCommandOrigin(), source);
		//}
		
		String[] cmdArray = commands.replace("\r", "").split("\n");		
		
		for (int i = 0; i < cmdArray.length; i++) {
			
			GCommand gc = GCodeParser.parseCommand(cmdArray[i]);		
			
			if (gc != null) {				
				addCommand(gc, source);
			}
		}

		ActionEvent ae = new ActionEvent(source , -1, CMD_ADDED_BUNCH_OF_COMMANDS);
		notifyAllAboutChanges(ae);	
			
		butchOfCmdsAddingInProgress = false;
	}

	public List<GCommand> findVertexesNear(float cncX, float cncY) {
		
		EditorStates es = EditorStates.getInstance();
		List<GCommand> result = new ArrayList<GCommand>();
		for(GCommand v : gCommandList){
			if(0.5/es.getScale() > Math.abs(v.getX() - cncX) 
					&& 0.5/es.getScale() > Math.abs(v.getY() - cncY)){
				result.add(v);
			}
		}
		return result;
	}
	
	public List<GCommand> findVertexesInRegion(float startX, float startY, float endX, float endY) {
		
		float left = Math.min(startX, endX);
		float top = Math.min(startY, endY);
		float rigth = Math.max(startX, endX);
		float bottom = Math.max(startY, endY);
				
		//EditorStates es = EditorStates.getInstance();
		List<GCommand> result = new ArrayList<GCommand>();
		for(GCommand v : gCommandList){
			if(left <= v.getX() 
				&& rigth >= v.getX() 
				&& top <= v.getY()
				&& bottom >= v.getY()){
				result.add(v);
			}
		}
		return result;
	}

	public Set<GCommand> getNeighbourVertexes(GCommand gCommand) {
		
		Set<GCommand> result = new HashSet<GCommand>();
		int indexOfCurrent = gCommandList.indexOf(gCommand);
		if(indexOfCurrent - 1 >= 0){
			result.add(gCommandList.get(indexOfCurrent - 1));
		}
		if(indexOfCurrent + 1 < gCommandList.size()){
			result.add(gCommandList.get(indexOfCurrent + 1));
		}
		return result;
	}

	public void clear(Object source) {
		
		gCommandList.clear();
		
		if(!(source instanceof GCodesDocListener)){
			
			ActionEvent ae = new ActionEvent(source , -1, CMD_CLEAR_COMMANDS_CONATINER);
			notifyAllAboutChanges(ae);			
			addCommand(new GCommandOrigin(), source);
		}

	}

	
	//---------------------
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals(GCommand.CMD_COORDINATE_CHANGED)){
			notifyAllAboutChanges(e);
		}
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

}
