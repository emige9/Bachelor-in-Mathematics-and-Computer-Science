package prLibreria;

public class OfertaAutor implements OfertaFlex {

	private double porcDescuento;
	private String autoresOferta[];
	
	public OfertaAutor (double desc, String autoresOferta[]) {
		porcDescuento=desc;
		this.autoresOferta=autoresOferta.clone();
	}
	
	@Override
	public double getDescuento(Libro l) {
		// TODO Auto-generated method stub
		double descuento=0;
		if(buscarAutorOferta(l.getAutor())>=0) {
			descuento=porcDescuento;
		}
		return descuento;
	}
	
	public String toString(){
		String res="";
		res+= porcDescuento + "% [";
		for (int i=0; i<autoresOferta.length-1; i++) {
			res+=autoresOferta[i] + ", ";
		}
		if (autoresOferta.length>0) {
			res+=autoresOferta[autoresOferta.length-1];
		}
		res+="]\n";
		return res;
	}
	
	private int buscarAutorOferta (String autor) {
		int i=0;
		
		while ((i<autoresOferta.length)&&(!autoresOferta[i].equalsIgnoreCase(autor))) {
			i++;
		}
		if (i==autoresOferta.length) {
			i=-1;
		}
		
		return i;
	}

}
