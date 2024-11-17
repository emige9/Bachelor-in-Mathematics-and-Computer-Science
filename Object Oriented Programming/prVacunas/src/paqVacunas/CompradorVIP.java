package paqVacunas;

public class CompradorVIP extends Comprador{
	private double descuento;
	Vacuna listaVacunas[];
	
	public CompradorVIP(String nombre, int numVacunas, double porcDescuento) {
		super(nombre,numVacunas);
		descuento=porcDescuento;
	}
	
	public CompradorVIP(String nombre, double porcDescuento) {
		super(nombre);
		descuento=porcDescuento;
	}
	
	public double precioTotal() {
		double desc=super.precioTotal()*descuento/100;
		return super.precioTotal()-desc;
	}
}
