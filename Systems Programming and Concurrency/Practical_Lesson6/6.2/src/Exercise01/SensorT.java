/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise01;

import static galvez.uma.es.SharedData.RND;
import java.util.concurrent.TimeUnit;

public class SensorT extends Thread{
	private static final int NUM_SAMPLES = 1000;
	private final SharedData system;
	public SensorT (SharedData system) {
		this.system = system;
	}

	public void run () {
		while (true) try {
			TimeUnit.SECONDS.sleep(1);
			double avg=0;
			for (int i=0;i<NUM_SAMPLES;i++)
				avg+=RND.nextDouble()*120;
			avg=avg/NUM_SAMPLES;
			system.setTemperature(avg);
		} catch (InterruptedException e) { e.printStackTrace(); }
	}
}
