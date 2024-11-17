package exer2;

public class Dam extends Thread{
	
	private Lake lake;
	private int id;
	
	public Dam(Lake lake,int id){
		this.lake = lake;
		this.id = id;
	}
	
	
	public void run(){
		
		for (int i= 0; i<10000; i++){
			lake.decrement(id);
			
		}
	}

}
