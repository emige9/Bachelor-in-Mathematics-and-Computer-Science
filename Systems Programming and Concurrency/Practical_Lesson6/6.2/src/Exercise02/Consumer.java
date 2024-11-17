package Exercise02;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Consumer<T> extends Thread {
	private static final Random rnd = new Random();
	
	private final int id;
	private final Buffer<T> b;

	public Consumer(int id, Buffer<T> b) {
		this.id = id;
		this.b = b;
	}
	
	@Override
	public void run() {
		while (true) try {
			T data = b.get(id);
			// Here we simulate we do whatever we need with the piece of data
			TimeUnit.MILLISECONDS.sleep(rnd.nextInt(600));
		} catch (Exception x) { x.printStackTrace(); }
	}
}
