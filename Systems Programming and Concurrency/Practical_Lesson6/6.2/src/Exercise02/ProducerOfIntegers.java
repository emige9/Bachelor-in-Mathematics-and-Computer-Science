package Exercise02;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProducerOfIntegers extends Thread {
	private static final Random rnd = new Random();

	private final int id;
	private final Buffer<Integer> b;

	public ProducerOfIntegers(int id, Buffer<Integer> b) {
		this.id = id;
		this.b = b;
	}
	
	@Override
	public void run() {
		while (true) try {
			b.put(id, rnd.nextInt(1000));
			TimeUnit.MILLISECONDS.sleep(rnd.nextInt(200));
			
		} catch (Exception x) { x.printStackTrace(); }
	}
}
