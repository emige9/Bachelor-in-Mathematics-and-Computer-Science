import java.util.Arrays;

////////////////////////////////////////////////////////////////////////////////////////////
// ALUMNO: Emilio Gómez Esteban
// GRUPO: 2ºD (Doble Grado Mat. + Ing. Inf.)
////////////////////////////////////////////////////////////////////////////////////////////

public class OrdenacionJava extends Ordenacion {
    
	
   public static <T extends Comparable<? super T>> void ordenar(T v[]) {
        Arrays.sort(v);
   }


	// Pequeños ejemplos para pruebas iniciales.
	public static void main (String args[]) {
	
		// Un vector de enteros
		Integer vEnt[] = {3,8,6,5,2,9,1,1,4};
		ordenar(vEnt);
		System.out.println(vectorAString(vEnt));

		// Un vector de caracteres
		Character vCar[] = {'d','c','v','b'};
		ordenar(vCar);
		System.out.println(vectorAString(vCar));

	}	
    
}
