package server;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	Timer timer;
	
	public TimerTest() {
		timer = new Timer();
		timer.schedule(new Biggus(), 0, 1*1000);
	}
	
	public class Biggus extends TimerTask {

		public void run() {
			
			
		}
		
	}

}
