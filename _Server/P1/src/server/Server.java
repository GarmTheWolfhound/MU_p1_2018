package server;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class Server implements Runnable {
	private ServerSocket serverSocketClient;
	private Socket socketClient;
	private ServerSocket serverSocketImbedded;
	private Socket socketImbedded;

	public Server(int port1, int port2) {
		try {
			serverSocketClient = new ServerSocket(port1);
			new Thread(this).start();
			serverSocketImbedded = new ServerSocket(port2);
			new Thread(this).start();
			System.out.println("Server started, waiting for connections");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				socketClient = serverSocketClient.accept();
				System.out.println("Connection with client established");
				socketImbedded = serverSocketImbedded.accept();
				System.out.println("Connection with IS established");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				new ClientListener(socketClient).start();
				new ImbeddedListener(socketImbedded).start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private class ClientListener extends Thread {
		InputStream inputStream;
		byte[] sizeAr = new byte[4];
		int size;
		public ClientListener(Socket socket) throws InterruptedException, IOException {
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
					socketClient.close();
					System.out.println("Socket closed");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public class ImbeddedListener extends Thread {
		private OutputStream os;
	
		public ImbeddedListener(Socket socket) {
			try {
				os = socket.getOutputStream();
				os.write(123);
//				os.write(controllbit+2);
//				System.out.println(controllbit+2);
//				for(int i = 0; i < res.length; i++) {
//					System.out.print("{");
//					for(int j = 0; j < 16; j++) {
//						os.write(res[i][j]);
//						System.out.print(res[i][j] + ", ");
//					}
//					System.out.println("}");
//				}
				os.flush();
//				controllbit = 0;
			} catch (IOException e) {
				e.printStackTrace();
			} 
				finally {
				try {
					os.close();
					socket.close();
					System.out.println("Connection closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
        Server connection = new Server(13085, 33333);
     
    }
	
}
