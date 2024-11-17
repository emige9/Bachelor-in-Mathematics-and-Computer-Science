import prCuentaPalabrasSimpleColecciones.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.io.PrintWriter;
import java.io.IOException;
public class Main {
	public static void main(String [] args) {
		String [] datos = {
			"Guerra tenia una jarra y Parra tenia una perra, ",
			"pero la perra de Parra rompio la jarra de Guerra.",
			"Guerra pego con la porra a la perra de Parra. ",
			"!Oiga usted buen hombre de Parra! ",
			"Por que ha pegado con la porra a la perra de Parra.",
			"Porque si la perra de Parra no hubiera roto la jarra de Guerra,",
			"Guerra no hubiera pegado con la porra a la perra de Parra."};
		String delimitadores = "[ .,:;\\-\\!\\!\\?\\?]+";
		System.out.println("Creamos un contador de palabras");
		ContadorPalabras contador = new ContadorPalabras();
		// Incluimos todas las palabras que hay en datos teniendo en cuenta los delimitadores
		contador.incluyeTodas(datos, delimitadores);
		System.out.println(contador + "\n");
		try {
			System.out.println(contador.encuentra("parra"));
			System.out.println(contador.encuentra("Gorra"));
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage()+"\n");
		}
		//Repetimos la salida con entrada desde fichero ................
		System.out.println("Repetimos la ejecucion tomando la entrada desde fichero");
		contador = new ContadorPalabras();
		// Incluimos todas las palabras que hay en datos.txt teniendo en cuenta los separadores
		try{
			contador.incluyeTodasFichero("datos.txt", delimitadores);
			System.out.println(contador + "\n");
			//metodos para presentar por pantalla
			System.out.println("Salida a pantalla: ");
			PrintWriter pw = new PrintWriter(System.out, true);
			contador.presentaPalabras(pw);
			//salida a fichero
			System.out.println("\nSalida a fichero: salida.txt\n");
			contador.presentaPalabras("salida.txt");
		}catch (IOException e){
			System.out.println("ERROR:"+ e.getMessage());
		}
		// Creamos un contador de palabras significativas .....................................
		String [] noSig = {"A", "Con", "De", "La", "NO", "SI", "una", "y"};
		Collection<String> palNS = new HashSet<String>();
		for (String p : noSig){ palNS.add(p); }
		System.out.println("Creamos un fichero de palabras significativas: ");
		ContadorPalabrasSig contadorSig = new ContadorPalabrasSig(palNS);
		contadorSig.incluyeTodas(datos, delimitadores);
		System.out.println(contadorSig + "\n");
		//Repetimos la salida con entrada desde fichero ................
		System.out.println("Repetimos la ejecucion tomando las entradas desde fichero");
		// Incluimos todas las palabras que hay en datos.txt y las no significativas de fichNoSig
		try{
			contadorSig = new ContadorPalabrasSig("fichNoSig.txt", delimitadores);
			contadorSig.incluyeTodasFichero("datos.txt", delimitadores);
			System.out.println(contadorSig + "\n");
			//metodos para presentar por pantalla
			System.out.println("Salida a pantalla:");
			PrintWriter pw = new PrintWriter(System.out, true);
			contadorSig.presentaPalabras(pw);
			//salida a fichero
			System.out.println("\nSalida a fichero: salidaSig.txt");
			contadorSig.presentaPalabras("salidaSig.txt");
		}catch (IOException e){
			System.out.println("ERROR:"+ e.getMessage());
		}
	}
}
