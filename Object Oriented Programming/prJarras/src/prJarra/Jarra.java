package prJarra;
/**
 * 
 * @author Usuario
 *
 */

public class Jarra {
	private final int capacidad;
	private int contenido;
	
	/**
	 * 
	 * @param cap
	 */
	
	public Jarra(int cap) {
		
		if (cap<=0) {
			throw new RuntimeException("Capacidad Érronea");
		}
		
		contenido=0;
		capacidad=cap;
	}
	/**
	 * 
	 * @return
	 */
	
	public int capacidad() {
		return capacidad;
	}
	
	public int contenido() {
		return contenido;
	}
	
	public void llena() {
		contenido=capacidad;
	}
	
	public void vacia() {
		contenido=0;
	}
	
	public void llenaDesde (Jarra j) {
		
		if(this==j) {
			throw new RuntimeException("Misma Jarra");
		} else {
			int traspaso=capacidad-contenido;
			if(traspaso<=j.contenido) {
				contenido=capacidad;
				j.contenido=j.contenido-traspaso;
			}else {
				contenido+=j.contenido;
				j.contenido=0;
			}
		}
	}
	
	public String toString() {
		return "J(" + capacidad + "," + contenido + ")";
	}
	
}
