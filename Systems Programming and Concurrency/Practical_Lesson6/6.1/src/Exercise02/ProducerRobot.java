package Exercise02;

import java.util.concurrent.TimeUnit;

public class ProducerRobot extends Thread {

	private final ConveyorBelt belt;
	public ProducerRobot(ConveyorBelt belt) {
		this.belt = belt;
	}
	@Override
	public void run() {
		while(true) try {
			// This is another way to sleep
			TimeUnit.MILLISECONDS.sleep(ConveyorBelt.rnd.nextInt(500));
			// We generate a new item (from 0 to 999) whose type is 1, 2 or 3.
			belt.put(1+ConveyorBelt.rnd.nextInt(3), ConveyorBelt.rnd.nextInt(1000)); //
		} catch (Exception x) { x.printStackTrace(); }
	}

}
