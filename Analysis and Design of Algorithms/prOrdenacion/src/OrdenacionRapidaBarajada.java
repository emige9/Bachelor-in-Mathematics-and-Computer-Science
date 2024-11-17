////////////////////////////////////////////////////////////////////////////////////////////
// ALUMNO: Emilio Gómez Esteban
// GRUPO: 2ºD (Doble Grado Mat. + Ing. Inf.)
////////////////////////////////////////////////////////////////////////////////////////////

public class OrdenacionRapidaBarajada extends OrdenacionRapida {
	
	// Implementación de QuickSort con reordenación aleatoria inicial (para comparar tiempos experimentalmente)
	public static <T extends Comparable<? super T>> void ordenar(T v[]) {
		barajar(v);
		ordRapidaRec(v,0,v.length-1);
    }

	// reordena aleatoriamente los datos de un vector
    private static <T> void barajar(T v[]) {
    	for(int ind=0; ind<v.length; ind++) {
    		int k = aleat.nextInt(v.length);
    		//nextInt(int n)  Devuelve un pseudoaleatorio de tipo int comprendido entre cero (incluido) y el valor especificado (excluido).
    		Ordenacion.intercambiar(v, ind, k);
    	}
    }	
	
    public static void main (String args[]) {
    	
		// Un vector de enteros
		Integer vEnt[] = {3,8,6,5,2,9,1,1,4};
		System.out.println(vectorAString(vEnt));
		barajar(vEnt);
		System.out.println(vectorAString(vEnt));
		ordenar(vEnt);
		System.out.println(vectorAString(vEnt));

		// Un vector de caracteres
		Character vCar[] = {'d','c','v','b'};
		ordenar(vCar);
		System.out.println(vectorAString(vCar));

	}

}
