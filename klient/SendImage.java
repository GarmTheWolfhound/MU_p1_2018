package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SendImage extends JFrame implements ActionListener {
	/**
	 * 
	 * @author Abdullahi Farah
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File f = null;
	private JLabel label = new JLabel();
	private JTextField textField = new JTextField(null);
	private JPanel main = new JPanel();
	private JButton btnSend = new JButton("Skicka");
	private JButton btnBack = new JButton("Tillbaka");
	private JButton btnUpload = new JButton ("Ladda upp fil");
	private JButton btnExit = new JButton ("Avbryt");
	private JButton btnMario = new JButton ("Mario");
	private JButton btnGubbe = new JButton ("Gubbe");
	private String s;
	ImageIcon image;

	private Font fontButtons = new Font("Sanserif", Font.PLAIN,32);

	public SendImage() {

		btnUpload.setFont(fontButtons);
		btnExit.setFont(fontButtons);
		btnSend.setFont(fontButtons);
		btnBack.setFont(fontButtons);
		btnMario.setFont(fontButtons);
		btnGubbe.setFont(fontButtons);
		textField.setPreferredSize(new Dimension(400,25));
		textField.setEditable(false);

		setSize(522,600);
		setLocation(600,300);
		setVisible(true);
		add(main);


		label.setPreferredSize(new Dimension(400,300));
		//		label.setBounds(400, 300, 200, 200);
		main.getRootPane().add(textField);

		main.add(btnUpload);
		main.add(textField);
		main.add(btnSend);
		main.add(btnBack);
		main.add(btnExit);
		//		main.add(btnGubbe);
		//		main.add(btnMario);
		main.add(label);

		btnExit.addActionListener( this ); 
		btnUpload.addActionListener( this ); 
		btnBack.addActionListener( this ); 
		btnSend.addActionListener( this ); 
		//        btnGubbe.addActionListener( this ); 
		//        btnMario.addActionListener( this ); 

	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpload) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(f);
			if (chooser.showOpenDialog(btnUpload) == JFileChooser.APPROVE_OPTION) {			//Hittar sökväg till vad fil och sparar det som s
				textField.setText(chooser.getSelectedFile().getAbsolutePath());
				textField.setEditable(false);

				File selectedFile = chooser.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				label.setIcon(ResizeImage(path));
				s = path;

			}


		}
		if (e.getSource() == btnExit) {
			System.exit(0);

		}

		if (e.getSource() == btnBack) {
			this.dispose();

		}

		if (e.getSource() == btnSend) {
			try {
				send();
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		}
	}



	public ImageIcon ResizeImage(String imgPath){			
		ImageIcon MyImage = new ImageIcon(imgPath);
		Image img = MyImage.getImage();		
		Image newImage = img.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
		image = new ImageIcon(newImage);
		s = imgPath;					
		return image;
	}
	// skickar bilden till server
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

}
