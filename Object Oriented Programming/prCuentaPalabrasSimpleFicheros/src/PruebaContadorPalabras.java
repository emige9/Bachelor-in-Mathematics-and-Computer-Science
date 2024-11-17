import prCuentaPalabrasSimpleFicheros.ContadorPalabras;


public class PruebaContadorPalabras {
	public static void main(String[] args) {
		ContadorPalabras pa = new ContadorPalabras(5);
		String[] datos = {
				"Esta es la primera frase del ejemplo", 
				"y esta es la segunda frase"
		};
		
		pa.incluyeTodas(datos, "[ ]");
		System.out.println(pa.toString());
	}
}
