package prLibreria;

public class LibroOferta extends Libro {
	private double porcDescuento;
	
	public LibroOferta (String autor, String titulo, double precioB, double descuento) {
		super(autor,titulo,precioB);
		porcDescuento=descuento;
	}
	
	public double getDescuento() {
		return porcDescuento;
	}
	
	public double getPrecioFinal() {
		double precioFinal = getPrecioBase() - getPrecioBase()*getDescuento()/100;
		return precioFinal += precioFinal*getIVA()/100;
	}
	
	public String toString() {
		double precioDescuento = getPrecioBase() - getPrecioBase()*getDescuento()/100;
		
		return "(" + getAutor() + "; " + getTitulo() + "; " + getPrecioBase() + "; " + getDescuento() + "%; " + precioDescuento + "; " + getIVA() + "%; " + getPrecioFinal() + ")";
	}
}
