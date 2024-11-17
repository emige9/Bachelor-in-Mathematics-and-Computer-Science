import prIndicePalabras.*;

public class EjIndice1 {
	
		public static void main(String args[]) {
		// String delimitadores = "[àa-zA-Z0-9·ÈÌÛ˙¸¡…Õ”⁄‹]+";
		String delimitadores = "[ .,:;\\-\\!\\°\\ø\\?]+";
		//Indice cp = new IndiceContador(); // seleccione la clase a utilizar
		 Indice cp = new IndiceLineas(); // seleccione la clase a utilizar
		 //Indice cp = new IndicePosicionesEnLinea(); // seleccione la clase a utilizar
		cp.agregarFrase("Guerra tenia una jarra y Parra tenia una perra, "
		+ "pero la perra de Parra rompio la jarra de Guerra.");
		cp.agregarFrase("Guerra pego con la porra a la perra de Parra. "
		+ "°Oiga usted buen hombre de Parra! "
		+ "Por que ha pegado con la porra a la perra de Parra.");
		cp.agregarFrase("Porque si la perra de Parra no hubiera roto "
		+ "la jarra de Guerra, "
		+ "Guerra no hubiera pegado con la porra "
		+ "a la perra de Parra.");
		cp.resolver(delimitadores);
		cp.presentarIndiceConsola();
		}
	
}
