package alturas;

public class Pais implements Comparable<Pais>{
	private String nombre;
	private String continente;
	private double altura;
	
	public Pais(String nombre, String continente, double altMedia) {
		this.nombre= nombre;
		this.continente=continente;
		this.altura=altMedia;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getContinente() {
		return continente;
	}
	
	public double getAltura() {
		return altura;
	}
	
	public boolean equals(Object obj) {
		boolean res = obj instanceof Pais;
		Pais p = res ? (Pais)obj : null;
		return res && (this.nombre.equals(p.nombre));
	}
	
	public int hashCode() {
		return this.nombre.hashCode();
	}
	
	public int compareTo(Pais p) {
		return this.nombre.compareTo(p.getNombre());
	}
	
	public String toString() {
		return "Pais " + "(" + this.nombre + ", " + this.continente + ", " + this.altura + ")"; 
	}
}
