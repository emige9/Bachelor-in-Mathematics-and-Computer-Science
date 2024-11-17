package exer5;

import java.util.ArrayList;
import java.util.List;

public class Node extends Thread {
	private List<Integer> list;
	
	public Node(List<Integer> l){
		list = l;
	}
	
	private void add(List<Integer> l, int from, int to) {
		l.addAll(list.subList(from, to));
	}
	
	private void merge(List<Integer> la, List<Integer> lb) {
		int indexA = 0, indexB = 0, index = 0;
		
		while(indexA < la.size() && indexB < lb.size()) {
			if(la.get(indexA) < lb.get(indexB)) {
				list.set(index, la.get(indexA));
				indexA++;
			} else {
				list.set(index, lb.get(indexB));
				indexB++;
			}
			index++;
		}
		
		while(indexA < la.size()) {
			list.set(index, la.get(indexA));
			indexA++;
			index++;
		}
		
		while(indexB < lb.size()) {
			list.set(index, lb.get(indexB));
			indexB++;
			index++;
		}
	}
	
	public void printList() {
		for(int i=0; i<list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.print(" ");
	}
	
	public void run() {
		if(list.size() > 1) {
			List<Integer> la = new ArrayList<Integer>();
			List<Integer> lb = new ArrayList<Integer>();
			int med = list.size()/2;
			add(la, 0, med);
			add(lb,med, list.size());
			Node leftNode = new Node(la);
			Node rightNode = new Node(lb);
			leftNode.start();
			rightNode.start();
			
			try {
				leftNode.join();
				rightNode.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			merge(la,lb);
		}
	}
	
	
}
