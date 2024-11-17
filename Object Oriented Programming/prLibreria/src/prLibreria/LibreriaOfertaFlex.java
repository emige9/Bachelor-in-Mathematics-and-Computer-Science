package prLibreria;

public class LibreriaOfertaFlex extends Libreria{
	private OfertaFlex oferta;
	
	public LibreriaOfertaFlex(OfertaFlex oferta) {
		super();
		this.oferta=oferta;
	}
	
	public LibreriaOfertaFlex(int capacidad, OfertaFlex oferta) {
		super(capacidad);
		this.oferta=oferta;
	}
	
	public void setOferta (OfertaFlex nuevaOferta) {
		this.oferta=nuevaOferta;
	}
	
	public OfertaFlex getOferta() {
		return oferta;
	}
	
	public void addLibro (String autor, String titulo, double precio) {
		Libro l=new Libro(autor,titulo,precio);
		double descuento=oferta.getDescuento(l);
		
		if (descuento!=0) {
			anyadirLibro(new LibroOferta(autor,titulo,precio,descuento));
		} else {
			anyadirLibro(l);
		}
	}
	
	public String toString() {
		String res="";
		res+=oferta.toString();
		res+=super.toString();
		return res;
	}
}
