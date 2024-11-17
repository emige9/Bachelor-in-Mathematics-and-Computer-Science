package exer1;

public class Consumer extends Thread {
	
	private CircularBuffer buffer;
	
	public Consumer(CircularBuffer b) {
		buffer = b;
	}
	
	public void run() {
		for(int i=0; i<100; i++) {
			buffer.consume();
		}
	}

}
