package Exercise03;

import static Exercise03.CardinalDirections.North;
import static Exercise03.CardinalDirections.South;

public class Driver {
	private static final int NUM_PLANES = 5;

	public static void main(String[] args) {
		Runway rw = new Runway();
		Controller north = new Controller(North, rw);
		Controller south = new Controller(South, rw);
		for(int i=0; i<NUM_PLANES; i++)
			new Plane(rw); // The constructor starts the plane automatically
		north.start();
		south.start();
		// This is an endless program
	}

}
