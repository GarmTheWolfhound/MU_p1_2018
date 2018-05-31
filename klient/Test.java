package client;

import javax.swing.JFrame;
/**
 * 
 * @author Abdullahi Farah, wahid 
 *
 */
public class Test {
	
	public void start() {
	JFrame frame = new JFrame ("Client");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(new ClientViewer() );
	frame.pack();
	frame.setVisible(true);
	frame.setLocation(600,300);
	frame.setResizable(false);
	}
	 public static void main (String[] args) {
			Test app = new Test();
			app.start();
		}
}


