package exer2;

public class Lake {
	private volatile int level = 0;
	//private Peterson petRiver = new Peterson();
	//private Peterson petDam = new Peterson();
	//private Peterson petRiverDam = new Peterson();
	
	//peterson for lakes
	private volatile int rTurn = 0;
	private volatile boolean rf0 = false;
	private volatile boolean rf1 = false;
	
	//peterson for dams
	private volatile int dTurn = 0;
	private volatile boolean df0 = false;
	private volatile boolean df1 = false;
	
	//peterson for dams and rivers
	private volatile int drTurn = 0;  // 0 is for dams, 1 is for rivers
	private volatile boolean drf0 = false; // dams
	private volatile boolean drf1 = false; // rivers

	// it is only used by rivers
	public void increment(int id){
		//pre-protocol -> rivers
		//petRiver.preProtocol(id);
		if(id == 0) {
			rf0 = true;
			rTurn = 1;
			
			while(rf1 && rTurn == 1) {
				Thread.yield();
			}
		} else {
			rf1 = true;
			rTurn = 0;
			
			while(rf0 && rTurn == 0) {
				Thread.yield();
			}
		}
		
		//pre-protocol -> rivers and dams
		//petRiverDam.preProtocol(1)
		drf1 = true;
		drTurn = 0;
		
		while(drf0 && drTurn == 0) {
			Thread.yield();
		}
		
		//critical section
		level++;
		System.out.println("River " + this.getlevel());
		
		//post-protocol -> rivers and dams
		//petRiverDam.postProtocol(1);
		drf1 = false;
		
		//post-protocol -> rivers
		//petRiver.postProtocol(id);
		if(id == 0) {
			rf0 = false;
		} else {
			rf1 = false;
		}
	}
	
	
	public void decrement(int id){
		
		//pre-protocol -> dams
		//petDam.preProtocol(id);
		if(id == 0) {
			df0 = true;
			dTurn = 1;
					
			while(df1 && dTurn == 1) {
				Thread.yield();
			}
		} else {
			df1 = true;
			dTurn = 0;
					
			while(df0 && dTurn == 0) {
				Thread.yield();
			}
		}
		
		//synchronization
		while(level == 0) {
			Thread.yield();
		}
		
		//pre-protocol -> rivers and dams
		//petRiverDam.preProtocol(0);
		drf0 = true;
		drTurn = 1;
				
		while(drf1 && drTurn == 1) {
			Thread.yield();
		}
		
		//critical section		
		level--;
		System.out.println("Dam " + this.getlevel());
		
		//post-protocol -> rivers and dams
		//petRiverdDam.postProcotol(0);
		drf0 = false;
				
		// post-protocol -> dams
		//petDam.postProtocol(id);
		if(id == 0) {
			df0 = false;
		} else {
			df1 = false;
		}
	}
	public String getlevel() {
		// TODO Auto-generated method stub
		return "level is: " + level;
	}
}
