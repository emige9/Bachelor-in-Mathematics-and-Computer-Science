package exer2;

public class Peterson {

	private volatile int turn = 0;
	private volatile boolean f0 = false, f1 = false;
	
	public void preProtocol(int id) {
		if(id == 0) {
			f0 = true;
			turn = 1;
			
			while(f1 && turn == 1) {
				Thread.yield();
			}
		} else {
			f1 = true;
			turn = 0;
			
			while(f0 && turn == 0) {
				Thread.yield();
			}
		}
	}
	
	public void postProtocol(int id) {
		if(id == 0) {
			f0 = false;
		} else {
			f1 = false;
		}
	}
}
