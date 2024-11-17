package prPatinetes;

public class Empleado {
	
	// variables de instancia: latitud y longitud
	private String dni;
	private Posicion posicion;
	
	/*
	 *  constructor: crea un objeto Empleado
	 *  a partir del dni (dni) y la posicion (p)
	 *  recibidos por parametros
	 */
	public Empleado(String d, Posicion p) {
		dni = d;
		posicion = p;
	}

	// devuelve el dni
	public String getDni() {
		return dni;
	}

	// devuelve la posicion
	public Posicion getPosicion() {
		return posicion;
	}

	// modifica la posicion
	public void setPosicion(Posicion p) {
		posicion = p;
	}
	
	/*
	 *  comprueba y devuelve si dos empleados 
	 *  (this y el empleado recibido por parametro (o))
	 *  son iguales o no 
	 */
	public boolean equals(Object o) {
		boolean res = false;
		if (o instanceof Empleado) {
			Empleado e = (Empleado) o;
			res = this.dni.equalsIgnoreCase(e.dni);
		}
		return res;
	}
	
	// devuelve el hashCode del empleado
	public int hashCode() {
		return dni.toLowerCase().hashCode();
	}
	
	/*
	 *  devuelve una cadena de caracteres con la
	 *  representacion de un empleado
	 */
	public String toString() {
		return dni;
	}
}
