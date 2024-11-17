package prLibreria;
import java.util.Arrays;

public class Libreria {
	protected static final int CAP_INICIAL=15;
	private int numLibs;
	Libro [] listaLibros;
	
	
	public Libreria() {
		numLibs=0;
		listaLibros=new Libro[CAP_INICIAL];
		
		// this(CAP_INICIAL);
	}
	
	public Libreria(int capacidad) {
		if (capacidad<0) {
			throw new RuntimeException ("La capacidad debe ser estrictamente positivo");
		}
		numLibs=0;
		listaLibros=new Libro[capacidad];
	}
	
	public void addLibro(String autor, String titulo, double precioBase) {
		Libro libro=new Libro (autor,titulo,precioBase);
		anyadirLibro(libro);
	}
	
	public void remLibro (String autor, String titulo) {
		int pos=buscarLibro(autor,titulo);
		
		if (pos!=-1) {
			eliminarLibro(pos);
		}
	}
	
	public double getPrecioBase (String autor, String titulo) {
		double precioB=0;
		int pos=buscarLibro(autor,titulo);
		
		if (pos!=-1) {
			precioB=listaLibros[pos].getPrecioBase();
		} else {
			precioB=0;
		}
		
		return precioB;
	}
	
	public double getPrecioFinal(String autor, String titulo) {
		double precioFin=0;
		int pos=buscarLibro(autor,titulo);
		
		if (pos!=-1) {
			precioFin=listaLibros[pos].getPrecioFinal();
		} else {
			precioFin=0;
		}
		 return precioFin;
		
	}
	
	public String toString() {
		 String text= "";
		 for (int i=0; i<numLibs; i++) {
			 text+=listaLibros[i].toString();
			 text+=((1!=numLibs-1) ? ",\n" : "");
		 }
		 
		 return "[" + text + "]";
	}
	
	protected void anyadirLibro(Libro libro) {
		int pos=buscarLibro(libro.getAutor(), libro.getTitulo());
		
		if(pos==-1) {
			if(numLibs>=listaLibros.length) {
				listaLibros=Arrays.copyOf(listaLibros,2*listaLibros.length);
			}
				listaLibros[numLibs]=libro;
				numLibs++;
		} else {
			listaLibros[pos]=libro;
		}
	}
	
	private void eliminarLibro(int pos) {
		listaLibros[pos]=listaLibros[numLibs-1];
		listaLibros[numLibs-1]=null;
		numLibs--;
	}
	
	private int buscarLibro(String autor, String titulo) {
		int resultado=-1;
		int i=0;
		
		while (resultado==-1 && i<numLibs) {
			if(listaLibros[i].getAutor().equalsIgnoreCase(autor) && 
					   listaLibros[i].getTitulo().equalsIgnoreCase(titulo)) {
				resultado=i;
			}
			i++;
		}
		
		return resultado;
	}
}
