package paqVacunas;

public class Vacuna {
	private String codigo;
	private double precioUnidad;
	private int cantidad;
	
	public Vacuna(String codigo, double precio, int cantidad) {
		
		if(precio<=0){
			throw new RuntimeException ("El precio introducido es err�neo");
		}
		
		if(cantidad<=0) {
			throw new RuntimeException ("La cantidad introducida es err�nea");
		}
		
		this.codigo=codigo;
		this.precioUnidad=precio;
		this.cantidad=cantidad;
	}
	
	public Vacuna(String codigo, double precio) {
		
		if(precio<=0){
			throw new RuntimeException ("El precio introducido es err�neo");
		}
		
		this.codigo=codigo;
		this.precioUnidad=precio;
		cantidad=1;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public double getPrecio() {
		return this.precioUnidad;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public void setPrecio(double precioNuevo) {
		if(precioNuevo<=0) {
			throw new RuntimeException ("El precio introducido es err�neo");
		}
		
		precioUnidad=precioNuevo;
	}
	
	public void setCantidad(int cantidadNueva) {
		
		if(cantidadNueva<=0) {
			throw new RuntimeException ("La cantidad introducida es err�nea");
		}
		
		cantidad=cantidadNueva;
	}
	
	public double precioTotal() {
		return precioUnidad*cantidad;
	}
	
	public String toString() {
		return "(" + codigo + ": " + precioUnidad + " " + cantidad + ")";
	}
	
}
