package client;

/**
 * 
 * @author Abdullahi Farah
 *
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import java.awt.color.*;

public class DrawProgram extends JFrame implements ActionListener, MouseListener{

	private JPanel main = new JPanel();
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private JButton btnBack = new JButton("Tillbaka");
	private JButton btnExit = new JButton("Avbryt");
	private JButton btnSend = new JButton("Skicka");
	private ImageIcon s = new ImageIcon("images/black");
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	private Font fontButtons = new Font("Sanserif", Font.PLAIN,32);
	
	public DrawProgram() {
		
		btnBack.setFont(fontButtons);
		btnSend.setFont(fontButtons);
		btnExit.setFont(fontButtons);
		
		label.setPreferredSize(new Dimension(400,300));
		setSize(522,600);
		setLocation(600,300);

		
		setVisible(true);
//		String url = "images\\black.jpg";
//		ImageIcon icon = new ImageIcon(url);
//		label.setIcon(icon);
		
		add(main);
//		main.add(label);
		main.add(btnSend);
		main.add(btnBack);
		main.add(btnExit);
		
		
		
        btnExit.addActionListener( this ); 
        btnSend.addActionListener( this ); 
        btnBack.addActionListener( this );
        
        addMouseListener(this);
	}
	
	
	

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnBack) {
			 this.dispose();
			
		}
		
		if (e.getSource() == btnExit) {
			System.exit(0);
		}
		
		if (e.getSource() == btnSend) {
			
		}
		
	}

	
	public void mousePressed(MouseEvent e) {
		x1 = e.getX() ;
		y1 = e.getY() ;
//		repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX() ;
		y2 = e.getY() ;
		repaint();
	}
	
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
//		repaint();
	}
	
	public void mouseMoved(java.awt.event.MouseEvent e) {
		x2 = e.getX();
		y2 = e.getX();
//		repaint();
		
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		g.drawLine(x1, y1, x2, y2);
		
	}




	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
