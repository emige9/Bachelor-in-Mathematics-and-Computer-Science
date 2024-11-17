import prLibreria.Libreria;
import prLibreria.Libro;

public class PruebaLibreria {

	public static void main (String[] args) {
		Libreria libreria=new Libreria();
		
		
		Libro[] librosParaAnyadir=
			{
				new Libro ("george orwell", "1984", 8.20),
				new Libro ("Philip K. Dick", "¿Sueñan los androides con ovejas eléctricas?", 3.50),
				new Libro ("Isaac Asimov", "Fundación e Imperio", 9.40),
				new Libro ("Ray Bradbury", "Fahrenheit 451", 7.40),
				new Libro ("Aldous Huxley", "Un mundo Feliz", 6.50),
				new Libro ("Isaac Asimov", "La Fundación", 7.30),
				new Libro ("William Gibson", "Neuromante", 8.30),
				new Libro ("Isaac Asimov", "Segunda Fundación", 8.10),
				new Libro ("Isaac Newton", "arithmetica universalis", 7.50),
				new Libro ("George Orwell", "1984", 6.20),
				new Libro ("Isaac Newton", "Arithmetica Universalis", 10.50),					
			};
		
		for (int i=0; i<librosParaAnyadir.length; i++) {
			libreria.addLibro(librosParaAnyadir[i].getAutor(),librosParaAnyadir[i].getTitulo(), librosParaAnyadir[i].getPrecioBase());
		}
		
		System.out.println( "Representación textual librería:" + libreria.toString());
		System.out.println();
		
		Libro[] librosParaEliminar=
			{
					new Libro("George Orwell","1984", 0),
					new Libro ("Aldous Huxley", "Un Mundo Feliz",0),
					new Libro ("Isaac Newton", "Arithmetica Universalis",0),
					new Libro ("James Gosling", "The Java Language Specification",0),
			};
		
		for (int j=0; j<librosParaEliminar.length; j++) {
			libreria.remLibro(librosParaEliminar[j].getAutor(),librosParaEliminar[j].getTitulo());
		}
		
		System.out.println("Rrepresentación textual librería: " + libreria.toString());
		System.out.println();
		
		System.out.println("PrecioFinal(George Orwell, 1984): " + libreria.getPrecioFinal("George Orwell","1984"));
		System.out.println("PrecioFinal(Philip K. Dick, ¿Sueñan los androides con ovejas eléctricas?): " + libreria.getPrecioFinal("Philip K. Dick", "¿Sueñan los androides con ovejas eléctricas?"));
		System.out.println("PrecioFinal(isaac asimov, fundación e imperio): " + libreria.getPrecioFinal("Isaac Asimov", "fundación e imperio"));
		System.out.println("PrecioFinal(Ray Bradbury, Fahrenheit 451): " + libreria.getPrecioFinal("Ray Bradbury", "Fahrenheit 451"));
		System.out.println("PrecioFinal(Aldous Huxley, Un mundo Feliz): " + libreria.getPrecioFinal("Aldous Huxley", "Un mundo Feliz"));
		System.out.println("PrecioFinal(Isaac Asimov, La Fundación): " + libreria.getPrecioFinal("Isaac Asimov", "La Fundación"));
		System.out.println("PrecioFinal(william gibson, neuromante): " + libreria.getPrecioFinal("william gibson", "neuromante"));
		System.out.println("PrecioFinal(Isaac Asimov, Segunda Fundación): " + libreria.getPrecioFinal("Isaac Asimov", "Segunda Fundación"));
		System.out.println("PrecioFinal(Isaac Newton, Arithmetica Universalis): "  + libreria.getPrecioFinal("Isaac Newton", "Arithmetica Universalis"));
	}
}
