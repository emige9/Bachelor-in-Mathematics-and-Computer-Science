/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise01;

import java.util.concurrent.TimeUnit;
import static galvez.uma.es.SharedData.RND;

public class SensorP extends Thread{
	private static final int NUM_SAMPLES = 650;
	private final SharedData system;
	public SensorP (SharedData system) {
		this.system = system;
	}

	public void run () {
		while (true) try {
			TimeUnit.SECONDS.sleep(2);
			double avg=0;
			for (int i=0;i<NUM_SAMPLES;i++)
				avg+=RND.nextDouble()*1200;
			avg=avg/NUM_SAMPLES;
			system.setPressure(avg);
		} catch (InterruptedException e) { e.printStackTrace(); }
	}
}
