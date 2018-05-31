package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.RenderedImage;

import javax.swing.JComponent;
 
/**
* Component for drawing !
*
* @author Abdullahi Farah, wahid
*
*/
public class DrawArea extends JComponent {
 
  private RenderedImage image;
  private Graphics2D g2;
  private int currentX, currentY, oldX, oldY;
 
  public DrawArea() {
    setDoubleBuffered(false);
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();
      }
    });
 
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
 
        if (g2 != null) {
          g2.drawLine(oldX, oldY, currentX, currentY);
          repaint();
          oldX = currentX;
          oldY = currentY;
        }
      }
    });
  }
 
  protected void paintComponent(Graphics g) {
    if (image == null) {
      image = (RenderedImage) createImage(getSize().width, getSize().height);
      g2 = (Graphics2D) ((Image) image).getGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      clear();
    }
 
    g.drawImage((Image) image, 0, 0, null);
    
  }
 
  public void clear() {
    g2.setPaint(Color.black);
    g2.fillRect(0, 0, getSize().width, getSize().height);
    g2.setPaint(Color.red);
    repaint();
  }
  
  public Image getImage() {
	return (Image) image;
	  
  }
 
}
