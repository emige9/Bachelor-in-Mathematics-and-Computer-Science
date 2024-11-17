package cartelera;

public class Sala implements Comparable<Sala>{
	private String nombre;
	private String pelicula;
	private int duracion;
	
	public Sala (String sala, String titulo, int duracion) {
		if(duracion<0) {
			throw new CarteleraException ("La duracion de la pelicula no puede ser negativa");
		}
		nombre=sala;
		pelicula=titulo;
		this.duracion=duracion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getPelicula() {
		return pelicula;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setPelicula(String nuevaPelicula) {
		pelicula=nuevaPelicula;
	}
	
	public void setDuracion(int nuevaDur) {
		if(nuevaDur<0) {
			throw new CarteleraException ("La duracion de la pelicula no puede ser negativa");
		}
		duracion=nuevaDur;
	}
	
	public boolean equals(Object obj) {
		boolean res=obj instanceof Sala;
		Sala s = res ? (Sala)obj : null;
		return res && nombre.equalsIgnoreCase(s.getNombre()) && pelicula.equalsIgnoreCase(s.getPelicula());
	}
	
	public int hashCode() {
		return nombre.toLowerCase().hashCode() + pelicula.toLowerCase().hashCode();
	}
	
	public int compareTo(Sala sala) {
		int res = nombre.compareToIgnoreCase(sala.getNombre());
		if (res == 0) {
			res = pelicula.compareToIgnoreCase(sala.getPelicula());
		}
		return res;
	}
	
	public String toString () {
		return nombre + " > " + pelicula + " (" + duracion + " min)";
	}
}
