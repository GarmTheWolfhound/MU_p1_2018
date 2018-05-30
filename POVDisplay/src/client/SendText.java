package client;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SendText extends JFrame implements ActionListener {
	/**
	 * 
	 * @author Abdullahi Farah
	 *
	 */
        private static final long serialVersionUID = 1L;
        private JPanel main = new JPanel();
        private JTextField textField = new JTextField();
        private JButton btnBack = new JButton("Tillbaka");
        private JButton btnSend = new JButton ("Skicka text");
        private JButton btnExit = new JButton ("Avbryt");
        private Font fontButtons = new Font("Sanserif", Font.PLAIN,32);
        private Font fontLabel = new Font("Sanserif", Font.PLAIN,60);
        private String s;
        
        public SendText() {
                
                btnBack.setFont(fontButtons);
                btnSend.setFont(fontButtons);
                btnExit.setFont(fontButtons);
                textField.setFont(fontLabel);
                textField.setPreferredSize(new Dimension(400,250));
                textField.setBackground(Color.BLACK);
                textField.setCaretColor(Color.RED);
                textField.setForeground(Color.RED);
                setSize(522,500);
                setLocation(600,300);
                setVisible(true);
                add(main);
                
                main.add(textField);
                main.add(btnSend);
                main.add(btnBack);
                main.add(btnExit);
                
        btnExit.addActionListener( this ); 
        btnSend.addActionListener( this ); 
        btnBack.addActionListener(this);

                
        }
        
        public void actionPerformed(ActionEvent e) {
                 if (e.getSource() == btnExit) {
                         System.exit(0);
                 }
                 
                 if (e.getSource() == btnSend) {
                         String ttext = textField.getText();
                         String text = ttext ;

                        
                        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = img.createGraphics();
                        Font font = new Font("Arial", Font.PLAIN, 48);
                        g2d.setFont(font);
                        FontMetrics fm = g2d.getFontMetrics();
                        int width = fm.stringWidth(text);
                        int height = fm.getHeight();
                        g2d.dispose();

                        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        g2d = img.createGraphics();
                        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
                        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                        g2d.setFont(font);
                        fm = g2d.getFontMetrics();
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(text, 0, fm.getAscent());
                        g2d.dispose();
                        try {
                            ImageIO.write(img, "jpg", new File("Text1.jpg"));
                            
                            s = "Text1.jpg" ;
                            
                            send();
                            
                            
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (Exception e1) {
							e1.printStackTrace();
						}

                 }
                 
                 if (e.getSource() == btnBack) {
                         this.dispose();
                         
                 }
                 
        }
    	public void send() throws Exception {
    		
            Socket socket = new Socket("192.168.43.58", 13085);

            OutputStream outputStream = socket.getOutputStream();

            BufferedImage image = ImageIO.read(new File(s));



            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(image, "jpg", byteArrayOutputStream);



            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

            outputStream.write(size);

            outputStream.write(byteArrayOutputStream.toByteArray());

            outputStream.flush();

            System.out.println("Flushed: " + System.currentTimeMillis());



            Thread.sleep(120000);

            System.out.println("Closing: " + System.currentTimeMillis());

            socket.close();

        }
