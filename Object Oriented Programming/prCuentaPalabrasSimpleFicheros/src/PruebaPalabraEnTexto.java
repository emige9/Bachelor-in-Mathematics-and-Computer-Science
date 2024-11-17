import prCuentaPalabrasSimpleFicheros.PalabraEnTexto;

public class PruebaPalabraEnTexto {
	public static void main(String[] args) {
		PalabraEnTexto palabra1= new PalabraEnTexto("gorra");
		PalabraEnTexto palabra2= new PalabraEnTexto("Gorra");
		palabra1.increments();
		System.out.println(palabra1);
		System.out.println(palabra2);
		if(palabra1.equals(palabra2)) {
			System.out.println("Las palabras son iguales");
		} else {
			System.out.println("Las palabras no son iguales");
		}
	}
}
