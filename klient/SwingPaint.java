package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingPaint {
	/**
	 * 
	 * @author Abdullahi Farah
	 * 
	 */
	JPanel controls = new JPanel();
	JButton clearBtn, saveBtn, backBtn;
	JFrame frame = new JFrame("Swing Paint");
//	ImageIcon image;
	String s;
	DrawArea drawArea;
	Image image;
	ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clearBtn) {
				drawArea.clear();
			}
			if (e.getSource() == backBtn) {
				frame.dispose();
				dispose();
			}
			if (e.getSource() == saveBtn) {
				image = drawArea.getImage();
				
				
				try {
					ImageIO.write((RenderedImage) image, "jpg", new File("images/test.jpg"));
					send();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		}
	};


	public void show() {

		JFrame frame = new JFrame("Swing Paint");
		Container content = frame.getContentPane();

		content.setLayout(new BorderLayout());

		drawArea = new DrawArea();
		content.add(drawArea, BorderLayout.CENTER);

		

		clearBtn = new JButton("Clear");
		backBtn = new JButton("Tillbaka");
		saveBtn = new JButton("Spara");
		clearBtn.addActionListener(actionListener);
		saveBtn.addActionListener(actionListener);
		backBtn.addActionListener(actionListener);

		controls.add(clearBtn);
		controls.add(saveBtn);
		controls.add(backBtn);
		content.add(controls, BorderLayout.NORTH);

		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


	}
	
	public void dispose() {
		frame.dispose();
	}
	



	public void send() throws Exception {
		s = "images/test.jpg";
				
        Socket socket = new Socket("127.0.0.1", 13085);			//("192.168.43.58", 13085);

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

}