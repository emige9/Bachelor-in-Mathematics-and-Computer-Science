package exer1;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CircularBuffer buffer = new CircularBuffer(10);
		Consumer c = new Consumer(buffer);
		Producer p = new Producer(buffer);
		
		c.start();
		p.start();
		
		try {
			c.join();
			p.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Game over!");
		
	}

}
