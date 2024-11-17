package exer4;

public class Driver {
	
	public static void main(String[] args) {
		Fibonacci fib = new Fibonacci(5);
		fib.start();
		
		try {
			fib.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(fib.getRes());
	}
}
