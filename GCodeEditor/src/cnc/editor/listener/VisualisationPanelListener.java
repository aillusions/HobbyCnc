package cnc.editor.listener;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.GCommandsContainer;
import cnc.editor.view.VisualisationPanel;

public class VisualisationPanelListener implements MouseListener, MouseMotionListener {

	private Editor editor;


	public VisualisationPanelListener(Editor editor){
		this.editor = editor;
	}
	
	public void mousePressed(MouseEvent e) {
	
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();		
		if(e.getButton() == 1){
			editor.mousePressedAt(x,y, e.isControlDown());
		}else if(e.getButton() == 3){
	        JPopupMenu menu = new JPopupMenu(); 
	        menu.add(new EditAction()); 
	        menu.add(new DeleteAction()); 
	        menu.addSeparator(); 
	        menu.add(new SelectAllAction()); 
	        
	        Point pt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), (VisualisationPanel)e.getSource());
	        menu.show((VisualisationPanel)e.getSource(), pt.x, pt.y);
		}		
	}

	public void mouseDragged(MouseEvent e) {		
			
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();		

		editor.viewMouseDraggedTo(x,y);

	}
	
	public void mouseMoved(MouseEvent e) {	
		
		//TODO add condition - vertex hover
		((JPanel)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void focusGained(FocusEvent e) {
					
	}
	
	public void focusLost(FocusEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {	
		
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();	
		
		editor.viewMouseReleasedAt(x, y);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
	class EditAction extends AbstractAction{ 
		
		private static final long serialVersionUID = 1L;

		public EditAction(){ 
	        super("Edit"); 
	    } 
	 
	    public void actionPerformed(ActionEvent e){ 
	    	
	    } 
	 
	    public boolean isEnabled(){ 
	       return true;
	    } 
	}
	class DeleteAction extends AbstractAction{ 
		
		private static final long serialVersionUID = 1L;

		public DeleteAction(){ 
	        super("Delete"); 
	    } 
	 
	    public void actionPerformed(ActionEvent e){ 

	    	GCommandsContainer.getInstance().removeGCommands(EditorStates.getInstance().getSelectedGCommands());
	    	EditorStates.getInstance().clearSelection();
	    } 
	 
	    public boolean isEnabled(){ 
	       return true;
	    } 
	}
	class SelectAllAction extends AbstractAction{ 
		
		private static final long serialVersionUID = 1L;

		public SelectAllAction(){ 
	        super("Select All"); 
	    } 
	 
	    public void actionPerformed(ActionEvent e){ 
	    	
	    } 
	 
	    public boolean isEnabled(){ 
	       return true;
	    } 
	}


}
