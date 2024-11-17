import prCuentaPalabrasSimpleColecciones.PalabraEnTexto;

public class TestPalabraEnTexto {
	
	public static void main(String[] args) {
		PalabraEnTexto a = new PalabraEnTexto("Zapato");
        PalabraEnTexto b = new PalabraEnTexto("Sopa");
        a.increments();
        a.increments();
        a.increments();
        b.increments();
        b.increments();
        if (a.compareTo(b)>0) {
        	System.out.println("La palabra " + a.getPalabra() + " es la mayor");
        } else if (a.compareTo(b)==0) {
        	System.out.println("La palabras " + a.getPalabra() + " y " + b.getPalabra() + " son iguales");
        } else {
        	System.out.println("La palabra " + b.getPalabra() + " es la mayor");
        }
        PalabraEnTexto c = new PalabraEnTexto("gorra");
        PalabraEnTexto d = new PalabraEnTexto("Gorra");
        a.increments();
        a.increments();
        a.increments();
        b.increments();
        b.increments();
        if (c.compareTo(d)>0) {
        	System.out.println("La palabra " + c.getPalabra() + " es la mayor");
        } else if (c.compareTo(d)==0) {
        	System.out.println("La palabras " + c.getPalabra() + " y " + d.getPalabra() + " son iguales");
        } else {
        	System.out.println("La palabra " + d.getPalabra() + " es la mayor");
        }
	}
}
