package cnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jnpout32.pPort;

import cnc.operator.CncByteSignalGenerator;
import cnc.operator.Config;
import cnc.operator.ICncByteSignalGeneratorListener;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class HobbyDashBoard extends javax.swing.JFrame  {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	private JMenuItem jMenuItem3;
	private JMenu jMenu2;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem1;
	private JMenu jMenu1;
	private JButton jButton1;

	public static void main(String[] args) {
		HobbyDashBoard view = new HobbyDashBoard();
		view.setVisible(true);
		
	}

	public HobbyDashBoard() throws HeadlessException {
		initGUI();
	}

	private void initGUI() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));

        //Create a yellow label to put in the content pane.
        //JLabel yellowLabel = new JLabel();
        //AnchorLayout yellowLabelLayout = new AnchorLayout();
       // yellowLabel.setLayout(yellowLabelLayout);
       // yellowLabel.setOpaque(true);
       // yellowLabel.setBackground(new Color(248, 213, 131));
        //yellowLabel.setPreferredSize(new java.awt.Dimension(665, 220));

        //Set the menu bar and add the label to the content pane.
        this.setJMenuBar(greenMenuBar);
        {
        	jMenu1 = new JMenu();
        	greenMenuBar.add(jMenu1);
        	jMenu1.setText("File");
        	{
        		jMenuItem1 = new JMenuItem();
        		jMenu1.add(jMenuItem1);
        		jMenuItem1.setText("jMenuItem1");
        	}
        	{
        		jMenuItem2 = new JMenuItem();
        		jMenu1.add(jMenuItem2);
        		jMenuItem2.setText("jMenuItem2");
        	}
        }
        {
        	jMenu2 = new JMenu();
        	greenMenuBar.add(jMenu2);
        	jMenu2.setText("Run");
        	{
        		jMenuItem3 = new JMenuItem();
        		jMenu2.add(jMenuItem3);
        		jMenuItem3.setText("Run");
        	}
        }
       // this.getContentPane().add(yellowLabel, BorderLayout.CENTER);

        //Display the window.
        this.pack();
        this.setSize(449, 298);
        this.setVisible(true);
        this.setTitle("Hobby Cnc Dashboard");
        {
        	jPanel1 = new JPanel();
        	getContentPane().add(jPanel1, BorderLayout.CENTER);
        	//jPanel1.setLayout(jPanel1Layout);
        	jPanel1.setPreferredSize(new java.awt.Dimension(547, 254));
        	jPanel1.setLayout(null);
        	{
        		jButton1 = new JButton();
        		jPanel1.add(jButton1);
        		jButton1.setText("jButton1");
        		jButton1.setBounds(374, 223, 55, 21);
        	}
        	{
        		jScrollPane1 = new JScrollPane();
        		jPanel1.add(jScrollPane1);
        		jScrollPane1.setBounds(7, 7, 150, 232);
        		{
        			jTextArea1 = new JTextArea();
        			jScrollPane1.setViewportView(jTextArea1);
        			jTextArea1.setText("jTextArea1");
        			jTextArea1.setBounds(16, 21, 143, 145);
        		}
        	}
        }
	}
	


}
