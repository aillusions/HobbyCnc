package gena;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyListener l = new MyListener();
		ViewFrame v = new ViewFrame(l);
		v.setVisible(true);
	}

}
