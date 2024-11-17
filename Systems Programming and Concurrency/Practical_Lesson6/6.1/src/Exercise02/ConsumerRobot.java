package Exercise02;

import java.util.concurrent.TimeUnit;

public class ConsumerRobot extends Thread {

	private final int id;
	private final ConveyorBelt belt;
	public ConsumerRobot(int i, ConveyorBelt belt) {
		this.id = i;
		this.belt = belt;
	}
	@Override
	public void run() {
		while(true) try {
			int item = belt.take(id);
			// Here we do whatever we want with the item. This is represented by a sleep
			// This is another way to sleep
			TimeUnit.MILLISECONDS.sleep(ConveyorBelt.rnd.nextInt(500));
		} catch (Exception x) { x.printStackTrace(); }
	}

}
