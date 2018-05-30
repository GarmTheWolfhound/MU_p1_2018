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
/**
 * 
 * @author Daniel Lone
 * A socket server that listens to two ports with two socket 
 */

public class Server implements Runnable {
	private ServerSocket serverSocketClient;
	private Socket socketClient;
	private ServerSocket serverSocketEmbedded;
	private Socket socketEmbedded;
	private int [][] res = new int[120][16];
	private int [][] arr = new int[120][16];
	int controllbit = -1;
	ArrayGenerator test = new ArrayGenerator();

	/**
	 * 
	 * @param port1 defined for the client
	 * @param port2 defined for the embedded system
	 */
	public Server(int port1, int port2) {
		try {
			serverSocketClient = new ServerSocket(port1);
			new Thread(this).start();
			serverSocketEmbedded = new ServerSocket(port2);
			new Thread(this).start();
			System.out.println("Server started, waiting for connections");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @return returns a 2d array  
	 */
	public int[][] generateArray(){
		controllbit = 1;
		res = test.generateMultipleSets(120, 16, 0, 1);
		return res;
		
	}
	/**
	 * Run method, an instance of client is created that handles the requests
	 */
	public void run() {
		while (true) {
			try {
				socketClient = serverSocketClient.accept();
				System.out.println("Connection with client established");
				socketEmbedded = serverSocketEmbedded.accept();
				System.out.println("Connection with IS established");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				new ClientListener(socketClient).start();
				new ImbeddedListener(socketEmbedded).start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * 
	 * @author Daniel Lone
	 * 	ClientListener that retrieves an image from the client at saves it locally
	 */
	private class ClientListener extends Thread {
		InputStream inputStream;
		byte[] sizeAr = new byte[4];
		int size;
		/**
		 * 
		 * @param socket
		 * @throws InterruptedException
		 * @throws IOException
		 */
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
			/**
			 * A query for storing image in SQL database
			 */
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
	/**
	 * 
	 * @author Daniel Lone
	 *	ImbeddedListener sends an array to the immbedded system
	 */
	public class ImbeddedListener extends Thread {
		private OutputStream os;
	/**
	 * 
	 * @param socket
	 */
		public ImbeddedListener(Socket socket) {
			try {
				os = socket.getOutputStream();
				os.write(controllbit+2);
				System.out.println(controllbit+2);
				for(int i = 0; i < res.length; i++) {
					System.out.print("{");
					for(int j = 0; j < 16; j++) {
						os.write(res[i][j]);
						System.out.print(res[i][j] + ", ");
					}
					System.out.println("}");
				}
				os.flush();
				controllbit = 0;
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
