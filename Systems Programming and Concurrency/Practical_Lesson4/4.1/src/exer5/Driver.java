package exer5;

import java.util.Arrays;
import java.util.List;

public class Driver {
	
	public static void main(String[] args) {
		
		List<Integer> list = Arrays.asList(4, 7, 2, 6, 1, 16, 9, 0);
		Node node = new Node(list);
		node.start();
		
		
		try {
			node.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		node.printList();
		
	}
}
