/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise01;

/**
 *
 * @author galvez
 */
public class Driver {
	public static void main(String[] args) {
		SharedData system = new SharedData();
		SensorT t = new SensorT(system);
		SensorP p = new SensorP(system);
		SystemMonitor sysMon = new SystemMonitor(system);
		t.start();
		p.start();
		sysMon.start();
	}
}
