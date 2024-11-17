package prLibreria;

public class OfertaPrecio implements OfertaFlex {

	private double porcDescuento;
	private double umbralPrecio;
	
	public OfertaPrecio (double desc, double precio) {
		porcDescuento=desc;
		umbralPrecio=precio;
	}
	
	@Override
	public double getDescuento(Libro l) {
		// TODO Auto-generated method stub
		double descuento=0;
		
		if (l.getPrecioBase()>=umbralPrecio) {
			descuento=porcDescuento;
		}
		return descuento;
	}
	
	public String toString() {
		return porcDescuento + "%(" + umbralPrecio + ")";
	}

}
