package prPatinetes;

public class Posicion {
	
	// variables de instancia: latitud y longitud
	private double latitud, longitud;
	
	/*
	 *  constructor: crea un objeto Posicion
	 *  a partir de la latitud (lat) y longitud (lon)
	 *  recibidas por parametros
	 */
	public Posicion(double lat, double lon) {
		latitud = lat;
		longitud = lon;
	}
	
	// devuelve la latitud
	public double getLatitud() {
		return latitud;
	}

	// devuelve la longitud
	public double getLongitud() {
		return longitud;
	}

	// modifica la latitud
	public void setLatitud(double lat) {
		latitud = lat;
	}

	// modifica la longitud
	public void setLongitud(double lon) {
		longitud = lon;
	}

	/* calcula y devuelve la distancia entre
	 * la posicion receptora del mensaje (this)
	 * y la posicion recibida por parametro (p)
	 */
	public double distancia(Posicion p) {
		return Math.sqrt(Math.pow(this.latitud - p.latitud, 2) +
				  Math.pow(this.longitud - p.longitud, 2)); 
	}
	
	/*
	 *  devuelve una cadena de caracteres con la
	 *  representacion de una posicion
	 */
	public String toString() {
		return "Lat: " + latitud + "; Lon: " + longitud;
	}
}
