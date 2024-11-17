package Exercise03;

import java.util.concurrent.TimeUnit;

public class Controller extends Thread {

	private final CardinalDirections myDir;
	private final Runway rw;

	public Controller(CardinalDirections myDir, Runway rw) {
		this.myDir = myDir;
		this.rw = rw;
	}
	
	@Override
	public void run() {
		while(true) try {
			rw.listenForLanding(myDir);
		} catch (Exception x) { x.printStackTrace(); }
	}

}
