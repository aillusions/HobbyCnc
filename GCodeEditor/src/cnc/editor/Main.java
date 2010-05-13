package cnc.editor;

import javax.swing.SwingUtilities;

import cnc.editor.view.EditorViewFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EditorViewFrame inst = new EditorViewFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
}
