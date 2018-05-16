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


public class Server implements Runnable {
	private ServerSocket serverSocket;
	private Socket socket;

	public Server(int port) {
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
				;
				inputStream.read(sizeAr);
				size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
				byte[] imageAr = new byte[size];
				inputStream.read(imageAr);
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
				ImageIO.write(image, "jpg", new File("images/test.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
			if((System.currentTimeMillis() - timestamp) > 5000) {
				System.err.println(socket.toString() + ": Connection timed out");
				serverSocket.close();
				return;
			}else {
				Thread.sleep(16);
			}
		}

	}
	public static void main(String[] args) {
        Server connection = new Server(13085);
     
    }
	
}
