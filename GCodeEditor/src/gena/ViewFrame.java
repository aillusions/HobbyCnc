package gena;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ViewFrame extends JFrame {
	private JButton btn_Clear;
	private JTextArea text;
	
	public ViewFrame(MyListener l) {
		btn_Clear = new JButton();
		btn_Clear.setText("clear");
		btn_Clear.setBounds(1, 8, 84, 21);
		btn_Clear.setMargin(new java.awt.Insets(0, 0, 0, 0));
		btn_Clear.setActionCommand("ABC");
		btn_Clear.addActionListener(l);
		
		text = new JTextArea(l.getDoc());
		text.setBounds(18, 148, 284, 121);
		
		add(btn_Clear);
		add(text);
		setLayout(null);
		setSize(400, 400);
		
	}
}
