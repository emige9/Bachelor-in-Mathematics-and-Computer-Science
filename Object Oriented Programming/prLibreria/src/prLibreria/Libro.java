package prLibreria;

public class Libro {
	protected static double porcIVA=10.0;
	private String autor;
	private String titulo;
	private double precioBase;
	
	public Libro(String aut, String titu, double precioB) {
		if (precioB<0) {
			throw new RuntimeException("No puede tener un precio negativo");
		}
		autor=aut;
		titulo=titu;
		precioBase=precioB;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public double getPrecioBase() {
		return precioBase;
	}
	
	public double getPrecioFinal() {
		double precioFinal=0;
		
		precioFinal= precioBase + precioBase*(porcIVA/100);
		
		return precioFinal;
	}
	
	public String toString() {
		return "(" + autor + "; " + titulo + "; " + precioBase + 
				"; " + porcIVA + "%; " + getPrecioFinal() + ")";
	}
	
	public static double getIVA() {
		return porcIVA;
	}
	
	public static void setIVA(double porcNuevo) {
		porcIVA=porcNuevo;
	}
	
}
