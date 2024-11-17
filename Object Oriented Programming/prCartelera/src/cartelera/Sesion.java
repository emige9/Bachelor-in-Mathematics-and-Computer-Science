package cartelera;

public class Sesion implements Comparable<Sesion>{
	
	/**
	 * Atributo para representar la hora (debe ser un valor entero entre 0 y 23)
	 */
	private int hh;
	
	/**
	 * Atributo para representar los minutos (debe ser un valor entero entre 0 y 59)
	 */
	private int mm;
	
	/**
	 * Constructor para crear objetos de la clase, a partir de dos enteros. En caso de no estar en el rango adecuado,
	 * se lanza una excepción ProgramacionTVException.
	 * 
	 * @param h	Hora (entre 0 y 23)
	 * @param m	Minuto (entre 0 y 59)
	 */
	public Sesion(int h, int m) {
		if (h<0 || h>23 || m<0 || m>59) throw new CarteleraException("La hora o los minutos no están en el rango adecuado");
		hh = h;
		mm =m;
	}
	
	/**
	 * Devuelve la hora
	 * 
	 * @return	Hora
	 */
	public int getHora() {
		return hh;
	}
	
	/** 
	 * Devuelve los minutos
	 * 	
	 * @return	Minutos
	 */
	public int getMinuto() {
		return mm;
	}
	
	/** 
	 * Dos instantes temporales se consideran iguales cuando coinciden hora y minutos.
	 */
	public boolean equals(Object o) {
		boolean res = o instanceof Sesion;
		Sesion hora = res ? (Sesion) o : null;
		return res && hh == hora.hh && mm == hora.mm;
	}
	
	/**
	 * Redefinición del método hashCode
	 */
	public int hashCode() {
		return hh + mm;
	}

	/**
	 * Orden natural por el que una hora es menor que otra cuando es anterior cronológicamente.
	 */
	public int compareTo(Sesion hora) {
		int res = hh - hora.hh;
		if (res == 0) res = mm - hora.mm;
		return res;
	}
	
	/**
	 * Devuelve la diferencia en minutos entre el objeto receptor y la hora que se pasa como argumento.
	 * 
	 * @param hora	Hora
	 * @return	Diferencia en mintos
	 */
	public int diferenciaMinutos(Sesion hora) {
		int minutos = hh * 60 + mm;
		int minutosHora = hora.hh * 60 + hora.mm;
		return Math.abs(minutos-minutosHora);
	}
	
	/**
	 * Representación textual del objeto con el formato:
	 * 		[hh:mm]
	 */
	public String toString() {
		String h = (hh<10 ? "0" : "") + hh;
		String m = (mm<10 ? "0" : "") + mm;
		return "[" + h + ":" + m + "]";
	}
}
