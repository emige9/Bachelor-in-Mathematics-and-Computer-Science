package Exercise02;

import java.util.Random;

public class ConveyorBelt {
	
	// This object is a utility to be used by the Threads
	public static final Random rnd = new Random();

	// Data stores the items given by the robots
	private int[] data;
	// Type is used to determine the type of each item in the data array
	// A value of zero indicates that the cell of the array is empty
	private int[] type;
	public ConveyorBelt(int size) {
		data = new int[size];
		type = new int[size];
		// Initially, all the cells of the array are empty
		for(int i=0;i<size;i++)
			type[i] = 0;
	}
	
	// In put, the Producer must wait if the data buffer is full
	public void put(int type, int item) {
		// TODO Auto-generated method stub
		
	}

	// In take, the Consumer id has to wait if there is no item of type id
	public int take(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
