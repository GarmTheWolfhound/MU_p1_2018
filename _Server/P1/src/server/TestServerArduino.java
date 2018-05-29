package server;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TestServerArduino implements Runnable {
	private ServerSocket serverSocket;
	private Socket socket;
	Timer timer ;
	private int [][] res = new int[120][16];
	private int [][] arr = new int[120][16];
	int controllbit = -1;
	Test test = new Test();
	
	public int[][] generateArray(){
		controllbit = 1;
		res = test.generateMultipleSets(120, 16, 0, 1);
		return res;
		
	}
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
		TestServerArduino test = new TestServerArduino(33333);
	}

}
