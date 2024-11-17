package Exercise04;

public class Driver {
	public static final int NUM_CARS = 10;
	public static void main(String[] args) {
		Bridge b = new Bridge();
		Car[] cars = new Car[NUM_CARS];
		for(int i=0;i<cars.length;i++) {
			cars[i] = new Car(i, b);
			cars[i].start();
		}
		// This is an endless program
	}

}
