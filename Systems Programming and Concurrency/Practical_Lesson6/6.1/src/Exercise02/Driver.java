package Exercise02;

public class Driver {

	public static void main(String[] args) {
		ConveyorBelt belt = new ConveyorBelt(5);
		ConsumerRobot[] c = new ConsumerRobot[3];
		// Consumer robots are numbered from 1 to 3 and not from 0 to 2
		for(int i=0;i<3;i++) {
			c[i] = new ConsumerRobot(i+1, belt);
			c[i].start();
		}
		ProducerRobot mainRobot = new ProducerRobot(belt);
		mainRobot.start();
		// This is an endless program
	}

}
