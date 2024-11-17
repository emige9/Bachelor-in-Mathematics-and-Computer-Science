package prLibreria;

public class LibreriaOferta extends Libreria {
	private String autoresOferta[];
	private double porcDescuento;
	
	public LibreriaOferta (double descuento, String autoresOferta[]) {
		super();
		porcDescuento=descuento;
		this.autoresOferta=autoresOferta.clone();
	}
	
	public LibreriaOferta (int capacidad, double descuento, String autoresOferta[]) {
		super(capacidad);
		porcDescuento=descuento;
		this.autoresOferta=autoresOferta;
	}
	
	public void setOferta (double porcDescuento, String autoresOferta[]) {
		this.porcDescuento=porcDescuento;
		this.autoresOferta=autoresOferta.clone();
	}
	
	public String[] getOferta () {
		return autoresOferta;
	}
	
	public double getDescuento() {
		return porcDescuento;
	}
	
	public void addLibro (String autor, String titulo, double precioBase) {
		if (buscarAutorOferta(autor)>=0) {
			anyadirLibro (new LibroOferta(autor,titulo,precioBase,porcDescuento));
		} else {
			anyadirLibro (new Libro(autor,titulo,precioBase));
		}
	}
	
	public String toString () {
		String res="";
		
		res+=getDescuento() + "% [";
		
		for (int i=0; i<autoresOferta.length-1; i++) {
			res+=autoresOferta[i] + ", ";
		}
		
		if (autoresOferta.length>0) {
			res+=autoresOferta[autoresOferta.length-1];
		}
		res+="]\n";
		res+=super.toString();
		
		return res;
	}
	
	private int buscarAutorOferta (String autor) {
		int i=0;
		
		while ((i<autoresOferta.length) && (!autoresOferta[i].equalsIgnoreCase(autor))) {
			i++;
		}
		
		if (i==autoresOferta.length) {
			i=-1;
		}
		
		return i;
	}
}
