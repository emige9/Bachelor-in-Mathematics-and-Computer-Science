import prLibreria.LibreriaOferta;


public class PruebaLibreriaOferta {

	public static void main (String[] args) {
		
		LibreriaOferta miLibOferta;
		String autoresOferta[]=new String[2];
		autoresOferta[0]="George Orwell";
		autoresOferta[1]="Isaac Asimov";
		miLibOferta= new LibreriaOferta(20,autoresOferta);
		
		miLibOferta.addLibro("george orwell", "1984", 8.20);
		miLibOferta.addLibro("Philip K. Dick", "¿Sueñan los androides con ovejas eléctricas?", 3.50);
		miLibOferta.addLibro("Isaac Asimov", "Fundación e Imperio", 9.40);
		miLibOferta.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
		miLibOferta.addLibro("Aldous Huxley", "Un mundo Feliz", 6.50);
		miLibOferta.addLibro("Isaac Asimov", "La Fundación", 7.30);
		miLibOferta.addLibro("William Gibson", "Neuromante", 8.30);
		miLibOferta.addLibro("Isaac Asimov", "Segunda Fundación", 8.10);
		miLibOferta.addLibro("Isaac Newton", "arithmetica universalis", 7.50);
		miLibOferta.addLibro("George Orwell", "1984", 6.20);
		miLibOferta.addLibro("Isaac Newton", "Arithmetica Universalis", 10.50);
		
		System.out.println(miLibOferta.toString());
		
		miLibOferta.remLibro("George Orwell", "1984");
		miLibOferta.remLibro("Aldous Huxley", "Un Mundo Feliz");
		miLibOferta.remLibro("Isaac Newton", "Arithmetica Universalis");
		miLibOferta.remLibro("James Gosling", "The Java Language Specification");
		
		System.out.println(miLibOferta.toString());
		
		System.out.println("PrecioFinal(George Orwell, 1984): " + miLibOferta.getPrecioFinal("George Orwell","1984"));
		System.out.println("PrecioFinal(Philip K. Dick, ¿Sueñan los androides con ovejas eléctricas?): " + miLibOferta.getPrecioFinal("Philip K. Dick", "¿Sueñan los androides con ovejas eléctricas?"));
		System.out.println("PrecioFinal(isaac asimov, fundación e imperio): " + miLibOferta.getPrecioFinal("Isaac Asimov", "fundación e imperio"));
		System.out.println("PrecioFinal(Ray Bradbury, Fahrenheit 451): " + miLibOferta.getPrecioFinal("Ray Bradbury", "Fahrenheit 451"));
		System.out.println("PrecioFinal(Aldous Huxley, Un mundo Feliz): " + miLibOferta.getPrecioFinal("Aldous Huxley", "Un mundo Feliz"));
		System.out.println("PrecioFinal(Isaac Asimov, La Fundación): " + miLibOferta.getPrecioFinal("Isaac Asimov", "La Fundación"));
		System.out.println("PrecioFinal(william gibson, neuromante): " + miLibOferta.getPrecioFinal("william gibson", "neuromante"));
		System.out.println("PrecioFinal(Isaac Asimov, Segunda Fundación): " + miLibOferta.getPrecioFinal("Isaac Asimov", "Segunda Fundación"));
		System.out.println("PrecioFinal(Isaac Newton, Arithmetica Universalis): "  + miLibOferta.getPrecioFinal("Isaac Newton", "Arithmetica Universalis"));
	}
}
