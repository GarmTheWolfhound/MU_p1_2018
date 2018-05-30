package server;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;


public class TestServerClient implements Runnable {
	private ServerSocket serverSocket;
	private Socket socket;

	public TestServerClient(int port) {
		try {
			serverSocket = new ServerSocket(port);
			new Thread(this).start();
			System.out.println("Server started, waiting for connections");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				socket = serverSocket.accept();
				System.out.println("Connection established");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				new Listener(socket).start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private class Listener extends Thread {
		InputStream inputStream;
		byte[] sizeAr = new byte[4];
		int size;
		public Listener(Socket socket) throws InterruptedException, IOException {
			long timestamp = System.currentTimeMillis();
			try {
				inputStream = socket.getInputStream();
				inputStream.read(sizeAr);
				size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
				byte[] imageAr = new byte[size];
				inputStream.read(imageAr);
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
				ImageIO.write(image, "jpg", new File("images/image.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
//			try{
//	             Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/project","root","root");
//	             PreparedStatement ps = con.prepareStatement("insert into images(Image) values(?)");
//	             InputStream is = new FileInputStream(new File("images/image.jpg"));
//	             ps.setBlob(1,is);
//	             ps.executeUpdate();
//	             JOptionPane.showMessageDialog(null, "Data Inserted");
//	         }catch(Exception ex){
//	             ex.printStackTrace();
//	         }
			finally {
				try {
					inputStream.close();
					socket.close();
					System.out.println("Socket closed");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	public static void main(String[] args) {
        TestServerClient connection = new TestServerClient(13085);
     
    }
	
}