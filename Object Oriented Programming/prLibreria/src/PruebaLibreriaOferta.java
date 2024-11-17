import prLibreria.LibreriaOferta;


public class PruebaLibreriaOferta {

	public static void main (String[] args) {
		
		LibreriaOferta miLibOferta;
		String autoresOferta[]=new String[2];
		autoresOferta[0]="George Orwell";
		autoresOferta[1]="Isaac Asimov";
		miLibOferta= new LibreriaOferta(20,autoresOferta);
		
		miLibOferta.addLibro("george orwell", "1984", 8.20);
		miLibOferta.addLibro("Philip K. Dick", "�Sue�an los androides con ovejas el�ctricas?", 3.50);
		miLibOferta.addLibro("Isaac Asimov", "Fundaci�n e Imperio", 9.40);
		miLibOferta.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
		miLibOferta.addLibro("Aldous Huxley", "Un mundo Feliz", 6.50);
		miLibOferta.addLibro("Isaac Asimov", "La Fundaci�n", 7.30);
		miLibOferta.addLibro("William Gibson", "Neuromante", 8.30);
		miLibOferta.addLibro("Isaac Asimov", "Segunda Fundaci�n", 8.10);
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
		System.out.println("PrecioFinal(Philip K. Dick, �Sue�an los androides con ovejas el�ctricas?): " + miLibOferta.getPrecioFinal("Philip K. Dick", "�Sue�an los androides con ovejas el�ctricas?"));
		System.out.println("PrecioFinal(isaac asimov, fundaci�n e imperio): " + miLibOferta.getPrecioFinal("Isaac Asimov", "fundaci�n e imperio"));
		System.out.println("PrecioFinal(Ray Bradbury, Fahrenheit 451): " + miLibOferta.getPrecioFinal("Ray Bradbury", "Fahrenheit 451"));
		System.out.println("PrecioFinal(Aldous Huxley, Un mundo Feliz): " + miLibOferta.getPrecioFinal("Aldous Huxley", "Un mundo Feliz"));
		System.out.println("PrecioFinal(Isaac Asimov, La Fundaci�n): " + miLibOferta.getPrecioFinal("Isaac Asimov", "La Fundaci�n"));
		System.out.println("PrecioFinal(william gibson, neuromante): " + miLibOferta.getPrecioFinal("william gibson", "neuromante"));
		System.out.println("PrecioFinal(Isaac Asimov, Segunda Fundaci�n): " + miLibOferta.getPrecioFinal("Isaac Asimov", "Segunda Fundaci�n"));
		System.out.println("PrecioFinal(Isaac Newton, Arithmetica Universalis): "  + miLibOferta.getPrecioFinal("Isaac Newton", "Arithmetica Universalis"));
	}
}
