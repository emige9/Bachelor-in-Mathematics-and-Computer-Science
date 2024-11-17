package Exercise04;

import java.util.Random;
import java.awt.Color;
import java.util.concurrent.Semaphore;

public class Bridge {
	public static final Random rnd = new Random();
	private Color leftLight = Color.green, rightLight = Color.green;
	private Semaphore mutex = new Semaphore(1,true);
	private int numCrossingCars = 0, leftQueueSize, rightQueueSize = 0;
	private Semaphore leftQueue = new Semaphore(0, true), rightQueue = new Semaphore(0, true);
	private String direction = "---";

	public void beginLeftToRight(int id) throws InterruptedException {
		mutex.acquire();
		rightLight = Color.red;
		if(leftLight == Color.green) {
			numCrossingCars++;
			direction = "-->";
			printBridge();
			mutex.release();
		} else {
			leftQueueSize++;
			mutex.release();
			leftQueue.acquire();
			leftQueueSize--;
			numCrossingCars++;
			direction = "-->";
			printBridge();
			
			if(leftQueueSize > 0) {
				leftQueue.release();
			} else {
				mutex.release();
			}
		}
	}

	public void endLeftToRight(int id) throws InterruptedException {
		mutex.acquire();
		numCrossingCars--;
		printBridge();
		
		if(numCrossingCars == 0){
			if(rightQueueSize > 0) {
				if(leftQueueSize == 0) {
					rightLight = Color.green;
				}
				rightQueue.release();
			} else {
				rightLight = Color.green;  // restore the state
				mutex.release();
			}
			direction="---";
			printBridge();
		} else {
			printBridge();
			mutex.release();
		}
	}

	public void beginRightToLeft(int id) throws InterruptedException {
		mutex.acquire();			
		leftLight = Color.red;
		if(rightLight == Color.green) {
			numCrossingCars++;
			direction = "<--";
			printBridge();
			mutex.release();
		} else {
			rightQueueSize++;
			mutex.release();
			rightQueue.acquire();
			rightQueueSize--;
			numCrossingCars++;
			direction = "<--";
			printBridge();
			
			if(rightQueueSize > 0) {
				rightQueue.release();
			} else {
				mutex.release();
			}
		}
	}

	public void endRightToLeft(int id) throws InterruptedException {
		mutex.acquire();
		numCrossingCars--;
		printBridge();
		
		if(numCrossingCars == 0){
			if(leftQueueSize > 0) {
				if(rightQueueSize == 0) {
					leftLight = Color.green;
				}
				leftQueue.release();
			} else {
				leftLight = Color.green;  // restore the state
				mutex.release();
			}
			direction="---";
			printBridge();
		} else {
			printBridge();
			mutex.release();
		}
	}
	
	private void printBridge() {
		System.out.print(leftQueueSize + ":" + (leftLight == Color.green ? "G" : "R") + " ");
		System.out.print("[" + direction + " " + numCrossingCars + " " + direction + "]");
		System.out.print(rightQueueSize + ":" + (rightLight == Color.green ? "G" : "R") + " ");
		System.out.println();
	}
	

}
