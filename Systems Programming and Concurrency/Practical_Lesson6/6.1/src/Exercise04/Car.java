package Exercise04;

import java.util.concurrent.TimeUnit;

public class Car extends Thread {

	private final int id;
	private final Bridge b;

	public Car(int id, Bridge b) {
		this.id = id;
		this.b = b;
	}
	
	@Override
	public void run() {
		while(true) try {
			// It roads around
			TimeUnit.MILLISECONDS.sleep(Bridge.rnd.nextInt(1000));
			// It requires to cross the bridge
			if (Bridge.rnd.nextBoolean()) {
				b.beginLeftToRight(id);
				// It takes a time to cross 
				TimeUnit.MILLISECONDS.sleep(Bridge.rnd.nextInt(200));
				b.endLeftToRight(id);
			} else {
				b.beginRightToLeft(id);
				// It takes a time to cross 
				TimeUnit.MILLISECONDS.sleep(Bridge.rnd.nextInt(200));
				b.endRightToLeft(id);
			}
		} catch (Exception x) { x.printStackTrace(); }
	}
}
