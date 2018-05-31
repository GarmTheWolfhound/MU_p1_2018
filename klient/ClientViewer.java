package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientViewer extends JPanel implements ActionListener {
	/**
	 * 
	 * @author Abdullahi Farah
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNamn = new JLabel("Välkommen till POV Display");
	private JButton btnDraw = new JButton ("Rita");
	private JButton btnBild = new JButton ("Skicka bild");
	private JButton btnText = new JButton ("Skicka text");
	private JButton btnExit = new JButton ("Avbryt");
	private Font fontButtons = new Font("Sanserif", Font.PLAIN,32);
	private Font fontLabel = new Font("Sanserif", Font.PLAIN,40);

	
	public ClientViewer() {
		setPreferredSize (new Dimension (500,300));		

		lblNamn.setFont(fontLabel);
		btnDraw.setFont(fontButtons);
		btnBild.setFont(fontButtons);
		btnText.setFont(fontButtons);
		btnExit.setFont(fontButtons);
		
        btnDraw.addActionListener( this ); 					//lägger till knappar, listener o annat
        btnBild.addActionListener( this );
        btnText.addActionListener( this ); 
        btnExit.addActionListener(this);
		add(lblNamn);
		add(btnDraw);
		add(btnBild);
		add(btnText);
		add(btnExit);
	}
	
	 public void actionPerformed(ActionEvent e) {			//Knapparna
		 if (e.getSource() == btnDraw) {
			 SwingPaint swing = new SwingPaint();
			 swing.show();
		 }
			 
		 if (e.getSource()== btnBild) {
			 new SendImage();
			 
		 }
		 if (e.getSource() == btnText) {
			 new SendText();
		 }
		 if (e.getSource() == btnExit) {
			 System.exit(0);
		 }
	 }

}