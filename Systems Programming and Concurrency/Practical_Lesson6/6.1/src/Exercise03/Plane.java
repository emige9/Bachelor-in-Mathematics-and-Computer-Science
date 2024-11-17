package Exercise03;

import java.util.concurrent.TimeUnit;

public class Plane extends Thread {
	
	private final Runway rw;

	public Plane(Runway rw) {
		this.rw = rw;
		start();
	}

	@Override
	public void run() {
		while(true) try {
			// The plane is flying
			TimeUnit.MILLISECONDS.sleep(Runway.rnd.nextInt(1000));
			// And now needs to land. It reaches the runway from a random cardinal direction
			CardinalDirections approximation = CardinalDirections.getRandomDirection();
			rw.requestLanding(approximation);
			// Random time taken to land
			TimeUnit.MILLISECONDS.sleep(Runway.rnd.nextInt(200));
			rw.finishLanding(approximation);
		} catch (Exception x) { x.printStackTrace(); }
	}
}
