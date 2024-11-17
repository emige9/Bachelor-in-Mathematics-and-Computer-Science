package Exercise02;

import java.util.LinkedList;
import java.util.List;

public class Driver {

	private static final int NUM_CELLS = 5;
	private static final int NUM_CONSUMERS = 3;

	public static void main(String[] args) {
		Buffer<Integer> b = new Buffer<>(NUM_CELLS, NUM_CONSUMERS);
		ProducerOfIntegers p = new ProducerOfIntegers(0, b);
		List<Consumer<Integer>> c = new LinkedList<>();
		for(int i=0;i<NUM_CONSUMERS;i++) {
			Consumer<Integer> aConsumer = new Consumer<>(i, b);
			c.add(aConsumer);
			aConsumer.start();
		}
		p.start();
		// This is an endless program
	}

}
