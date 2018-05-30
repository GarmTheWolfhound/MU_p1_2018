package client;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class LineDrawer<MouseEvent> extends JFrame {
	private int xbegin = 0;
	private int ybegin = 0;
	private int xend = 0;
	private int yend = 0;
	
	public LineDrawer() {
		setTitle("Ritning");
		setSize(500,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener( (MouseListener) mouseHandler);
		addMouseMotionListener(mouseMotionHandler);
		setVisible(true);
	}
	
	public MouseListener mouseHandler = new MouseListener() {

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			xbegin = xend = e.getX();
			ybegin = yend = e.getX();
			
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			xend = e.getX();
			yend = e.getX();
			repaint();
			
		}
	};
	
	public MouseMotionListener mouseMotionHandler = new MouseMotionListener() {

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			xend = e.getX();
			yend = e.getX();
			repaint();
			
		}

		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {
			xend = e.getX();
			yend = e.getX();
			repaint();
			
		}
		
	};
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(xbegin, ybegin, xend, yend);
	}
	public static void main (String []args) {
		LineDrawer ld = new LineDrawer();
	}
}

