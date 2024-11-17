/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise01;

import java.util.concurrent.TimeUnit;

public class SystemMonitor extends Thread{
	private final SharedData system;
	public SystemMonitor (SharedData system) {
		this.system = system;
	}
	
	public void run () {
		while (true) try {
			TimeUnit.SECONDS.sleep(1);
			system.displayStatus();
		} catch (InterruptedException e) { e.printStackTrace(); }
	}

}

