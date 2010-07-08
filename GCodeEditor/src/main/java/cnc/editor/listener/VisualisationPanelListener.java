package cnc.editor.listener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import cnc.editor.Editor;
import cnc.editor.EditorStates;
import cnc.editor.FiguresContainer;
import cnc.editor.GCodeParser;
import cnc.editor.domain.figure.FPoint;
import cnc.editor.domain.gcmd.GCommand;
import cnc.editor.view.VisualisationPanel;

public class VisualisationPanelListener implements MouseListener, MouseMotionListener {

	private Editor editor;
	private EditorStates es = EditorStates.getInstance();

	public VisualisationPanelListener(Editor editor){
		this.editor = editor;
	}
	
	public void mousePressed(MouseEvent e) {
		
		((VisualisationPanel)e.getSource()).requestFocusInWindow();
	
		VisualisationPanel vPanel = ((VisualisationPanel)e.getSource());
		
		double x = e.getPoint().getX();
		double y = vPanel.getEditorY(e.getPoint().getY());	
		
		if(e.getButton() == 1){
			
			editor.mousePressedAt(x, y, e.isControlDown());
			
		}else if(e.getButton() == 3){
			
	        JPopupMenu menu = new JPopupMenu(); 
	        menu.add(new EditAction()); 
	        menu.add(new DeleteAction()); 
	        menu.add(new FrezeAction()); 
	        menu.addSeparator(); 
	        menu.add(new MergeAction()); 
	        menu.add(new SelectAllAction()); 
	        
	        Point pt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), (VisualisationPanel)e.getSource());
	        menu.show((VisualisationPanel)e.getSource(), pt.x, pt.y);
		}		
	}

	public void mouseDragged(MouseEvent e) {		
			
		VisualisationPanel vPanel = ((VisualisationPanel)e.getSource());
		
		double x = e.getPoint().getX();
		double y = vPanel.getEditorY(e.getPoint().getY());		

		editor.viewMouseDraggedTo(x,y);
		
		Point p = new Point(((int)e.getPoint().getX()), ((VisualisationPanel)e.getSource()).getEditorY(e.getPoint().getY()));
		es.setMousePosition(p);

	}
	
	public void mouseMoved(MouseEvent e) {	
		
		//TODO add condition - vertex hover
		((JPanel)e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Point p = new Point(((int)e.getPoint().getX()), ((VisualisationPanel)e.getSource()).getEditorY(e.getPoint().getY()));
		es.setMousePosition(p);
	}

	
	public void mouseReleased(MouseEvent e) {	
		
		VisualisationPanel vPanel = ((VisualisationPanel)e.getSource());
		
		double x = e.getPoint().getX();
		double y = vPanel.getEditorY(e.getPoint().getY());	
		
		editor.viewMouseReleasedAt(x, y);
	}

	public void mouseClicked(MouseEvent e) {
		((VisualisationPanel)e.getSource()).requestFocusInWindow();
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
	    
	    	List<FPoint> cmds = EditorStates.getInstance().getSelectedGCommands();
	    	if(cmds.size() != 1){
	    		return;
	    	}    		    	
	    	
	    	final FPoint cmd = cmds.iterator().next();
	    	
	    	JMenuItem menuItem  = (JMenuItem)e.getSource();
	    	JPopupMenu menu = (JPopupMenu)menuItem.getParent();
	    	VisualisationPanel vp = (VisualisationPanel)menu.getInvoker();	    	
	    
	    	int x = (int)EditorStates.convertPositionCnc_View(cmd.getX().intValue());	    	
	    	int y = (int)(vp.getViewY(EditorStates.convertPositionCnc_View(cmd.getY().intValue())));
	    	
	    	final JTextField textField = new JTextField(cmd.toString());
	    	textField.setFont(new Font("Arial", 0, 13));
	    	textField.setForeground(Color.red);
	    	textField.setBounds(x, y, 200, 21);
	    	textField.setMargin(new java.awt.Insets(0, 0, 0, 0));
	    	
	    	textField.addFocusListener(new FocusListener() {
				
				public void focusLost(FocusEvent e) {
					JTextField thisTextField = (JTextField)e.getSource();
					VisualisationPanel mainVPanel = (VisualisationPanel)thisTextField.getParent();
					if(mainVPanel != null){
						mainVPanel.remove(thisTextField);
						mainVPanel.repaint();
					}
				}
				
				public void focusGained(FocusEvent e) {
					
				}
			});
	    	
			textField.addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent e) {

				}

				public void keyReleased(KeyEvent e) {

				}

				public void keyPressed(KeyEvent e) {
					
					int key = e.getKeyCode();
					
					if (key == KeyEvent.VK_ENTER) {
						
						GCommand newGC = GCodeParser.parseCommand(textField.getText());
						//GCommandsContainer.getInstance().replaceGCommand(cmd, newGC);
						
						JTextField thisTextField = (JTextField)e.getSource();
						VisualisationPanel mainVPanel = (VisualisationPanel)thisTextField.getParent();
						mainVPanel.remove(thisTextField);
						mainVPanel.repaint();						
					}
					
					if (key == KeyEvent.VK_ESCAPE) {
						JTextField thisTextField = (JTextField)e.getSource();
						VisualisationPanel mainVPanel = (VisualisationPanel)thisTextField.getParent();
						mainVPanel.remove(thisTextField);
						mainVPanel.repaint();	
					}
				}

			});
	    	
	    	vp.add(textField);
	    	textField.requestFocus();
	    } 
	 
	    public boolean isEnabled(){ 
	       return true;
	    } 
	}
	
	class MergeAction extends AbstractAction{ 
		
		private static final long serialVersionUID = 1L;

		public MergeAction(){ 
	        super("Merge"); 
	    } 
	 
	    public void actionPerformed(ActionEvent e){ 	    	
	    } 
	 
	    public boolean isEnabled(){ 
	       return false;
	    } 
	}
	class FrezeAction extends AbstractAction{ 
		
		private static final long serialVersionUID = 1L;

		public FrezeAction(){ 
	        super("Freze"); 
	    } 
	 
	    public void actionPerformed(ActionEvent e){ 
	    	
	    } 
	 
	    public boolean isEnabled(){ 
	       return false;
	    } 
	}
	
	class DeleteAction extends AbstractAction{ 
		
		private static final long serialVersionUID = 1L;

		public DeleteAction(){ 
	        super("Delete"); 
	    } 
	 
	    public void actionPerformed(ActionEvent e){ 

	    	FiguresContainer.getInstance().removePoints(EditorStates.getInstance().getSelectedGCommands());
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
	    	EditorStates.getInstance().setSelectedGCommands(FiguresContainer.getInstance().getAllPointsList());
	    } 
	 
	    public boolean isEnabled(){ 
	       return true;
	    } 
	}

}
