package Exercise02;

import java.util.ArrayList;
import java.util.List;

// To make the problem more general, we use an ArraList
// instead of an array.
public class Buffer<T> {
	//
	//
	private List<T> data;
	private final int numConsumers;
	public Buffer(int sizeBuffer, int numConsumers) {
		this.numConsumers = numConsumers;
		data = new ArrayList<T>(sizeBuffer);
		for(int i=0;i<sizeBuffer;i++) 
			data.add(null);
		//
		//	
	}

	public void put(int id, T item) throws InterruptedException {
	}

	public T get(int id) throws InterruptedException {
		return null;
	}

	private void displayBuffer() {
	}
}
