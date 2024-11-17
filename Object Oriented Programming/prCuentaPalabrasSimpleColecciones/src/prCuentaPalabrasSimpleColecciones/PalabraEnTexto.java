package prCuentaPalabrasSimpleColecciones;

public class PalabraEnTexto  implements Comparable <PalabraEnTexto>{
	private String palabra;
	private int veces;
	
	public PalabraEnTexto(String palabra) {
		this.palabra= palabra.toUpperCase();
		veces=1;
	}
	
	public void increments() {
		veces++;
	}
	
	public boolean equals (Object obj) {
		boolean res = obj instanceof PalabraEnTexto;
		PalabraEnTexto pal = res ? (PalabraEnTexto)obj : null;
		return res && palabra.equals(pal.palabra);
	}
	
	public String getPalabra() {
		return this.palabra;
	}
	
	public int hashCode() {
		return palabra.hashCode();
	}
	
	public String toString() {
		return palabra + ": " + veces;
	}
	
	public int compareTo (PalabraEnTexto pal) {
		return this.palabra.compareToIgnoreCase(pal.getPalabra());
	}
}
