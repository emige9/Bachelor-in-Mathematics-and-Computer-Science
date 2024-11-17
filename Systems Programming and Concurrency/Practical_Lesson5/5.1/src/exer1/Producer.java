package exer1;

public class Producer extends Thread {
	
	private CircularBuffer buffer;
	
	public Producer(CircularBuffer b) {
		buffer = b;
	}
	
	public void run(){
		for(int i=0; i<100; i++) {
			buffer.produce(i);
		}
	}

}
