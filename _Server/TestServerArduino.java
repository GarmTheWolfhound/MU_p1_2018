package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServerArduino implements Runnable {
	private ServerSocket serverSocket;
	private Socket socket;

	public TestServerArduino(int port) {
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
			new Listener(socket).start();
		}

	}

	public class Listener extends Thread {
		private OutputStream os;

		public Listener(Socket socket) {
			try {
				os = socket.getOutputStream();
				os.write('Q');
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					os.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		TestServerArduino test = new TestServerArduino(12345);

	}

}
