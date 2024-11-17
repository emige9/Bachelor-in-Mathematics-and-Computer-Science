package cartelera;

public class Sala implements Comparable<Sala> {
	private String nombre;
	private String película;
	private int duración;
	
	public Sala(String sala, String título, int dur) {
		if (dur<0) throw new CarteleraException("La duración de una película no puede ser negativa");
		nombre = sala;
		película = título;
		duración = dur;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getPelicula() {
		return película;
	}
	
	public int getDuracion() {
		return duración;
	}
	
	public void setPelícula(String nuevaPel) {
		película = nuevaPel;
	}
	
	public void setDuracion(int nuevaDur) {
		duración = nuevaDur;
	}
	
	public boolean equals(Object obj) {
		boolean res = obj instanceof Sala;
		Sala s = res ? (Sala) obj : null;
		return res 	&& nombre.equalsIgnoreCase(s.nombre)
					&& película.equalsIgnoreCase(s.película);
	}
	
	public int hashCode() {
		return nombre.toLowerCase().hashCode() + película.toLowerCase().hashCode();
	}
	
	public int compareTo(Sala sala) {
		int res = nombre.compareToIgnoreCase(sala.nombre);
		if (res == 0) res = película.compareToIgnoreCase(sala.película);
		return res;
	}
	
	public String toString() {
		return nombre + " > " + película + " (" + duración + " min)";
	}
}
