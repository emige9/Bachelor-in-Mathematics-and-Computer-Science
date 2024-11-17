package exer4;

public class Fibonacci extends Thread {

	private Integer num, res;
	private boolean ready;
	
	public Fibonacci(Integer n) {
		num = n;
		ready = false;
		res = null;
	}
	
	public Integer getRes() {
		return res;
	}
	
	public boolean getReady() {
		// TODO Auto-generated method stub
		return ready;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public void setRes(Integer res) {
		this.res = res;
	}
	
	public void run() {
		if(num == 0) {
			res = 0;
			ready = true;
		} else if (num == 1) {
			res = 1;
			ready = true;
		} else {
			Fibonacci fN1 = new Fibonacci(num - 1);
			Fibonacci fN2 = new Fibonacci(num - 2);
			fN1.start();
			fN2.start();
			while(!fN1.getReady() || !fN2.getReady()) {
				Thread.yield();
			}
			
			/*while(!fN1.getReady()) {
				Thread.yield();
			}
			
			while(!fN2.getReady()) {
				Thread.yield();
			}*/
		
			
			/*try {
				fN1.join();
				fN2.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			res = fN1.getRes() + fN2.getRes();
			ready = true;
		}
	}

	
}
