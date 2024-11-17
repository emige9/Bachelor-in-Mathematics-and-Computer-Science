import cartelera.*;
import java.io.*;

public class PruebaCartelera {
	public static void main(String[] args) throws FileNotFoundException {
		// Se crea un objeto Cartelera o CarteleraPorPelicula
		Cartelera cartelera;
		cartelera = new Cartelera();
		//cartelera = new CarteleraPorPelicula();
		cartelera.leerCartelera("cartelera.txt");
		
		// Se muestran en consola las sesiones de la película Sinsajo
		System.out.println(cartelera.todasSesiones("Los juegos del hambre: SINSAJO"));
		
		// Se muestra la información en la consola de salida
		cartelera.mostrarCartelera(new PrintWriter(System.out,true));
		// Se consulta la consistencia de la cartelera, que debe ser true
		System.out.println(cartelera.esConsistente());
	}
}
