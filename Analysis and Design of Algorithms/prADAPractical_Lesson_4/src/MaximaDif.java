import java.util.*;

public class MaximaDif {
	
	/**
	 * 
	 * @param a lista de enteros
	 * @param k tamaño de una de las listas de salida
	 * @param l1 l2 listas con los grupos que producen máxima diferencia.
	 * Además devuelve dicha diferencia en el valor de retorno
	 * @return
	 * 
	 * 1.- Ordenar los elementos de a
	 * 2.- Crear paso a paso el conjunto de menor suma o el de mayor suma
	 */
	
	public static int maximaDifFB (int[] a, int k, List<Integer> l1, List<Integer> l2) {
		
		int difMejor = -1;
		int mascaraMejor = -1;
		int sumaL2 = 0;
		int sumaL1 = 0;
		
		for(int m=0; m<Math.pow(2, a.length); m++) {
			// System.out.println(m);
			//Correcta?
			if(numUnos(m,a.length)==k) {
				sumaL2 = 0;
				sumaL1 = 0;
				
				for(int i=0; i < a.length; i++) {
					//Está activo ese bit i?
					if((m & (1<<i))==0) {
						sumaL2 += a[i];
					} else {
						sumaL1 += a[i];
					}
				}
				if(Math.abs(sumaL2-sumaL1)>difMejor) {
					mascaraMejor = m;
					difMejor = Math.abs(sumaL2-sumaL1);
				}
			}
		}
		
		l1.clear();
		l2.clear();
		for(int i=0; i < a.length; i++) {
			//Está activo ese bit i?
			if((mascaraMejor & (1<<i))==0) {
				l2.add(a[i]);
			} else {
				l1.add(a[i]);
			}
		}
		return difMejor;
	}
	
	private static int numUnos(int m, int n) {
		int unos = 0;
		
		for (int i=0; i<n; i++) {
			if((m & (1<<i)) != 0) {
				unos++;
			}
		}
		return unos;
	}
	
	/**
	 * 
	 * @param a lista de enteros
	 * @param k tamaño de una de las listas de salida
	 * @param l1 l2 listas con los grupos que producen máxima diferencia.
	 * Además devuelve dicha diferencia en el valor de retorno
	 * @return
	 * 
	 */
	
	public static int maximaDifVoraz(int[] a, int k, List<Integer> l1, List<Integer> l2) {
		Arrays.sort(a);
		int sumaL1 = 0;
		int sumaL2 = 0;
		l1.clear();
		l2.clear();
		int minLongitud = Math.min(k, a.length-k);
		
		for(int i=0; i<a.length;i++) {
			if(i<minLongitud) {
				sumaL1 += a[i];
				l1.add(a[i]);
			} else {
				sumaL2 += a[i];
				l2.add(a[i]);
			}
		}
		
		return Math.abs(sumaL1-sumaL2);
	}
	
	public static void main (String[] args) {
		int[] datos = {8,5,2,10,4};
		int k=3;
		List<Integer> l1 = new ArrayList<>();
		List<Integer> l2 = new ArrayList<>();
		int difFB = maximaDifFB(datos,k,l1,l2);
		System.out.println("FUERZA BRUTA");
		System.out.println("Grupo 1: " + l1.toString());
		System.out.println("Grupo 2: " + l2.toString());
		System.out.println("Diferencia: " + difFB);
		
		int difEV = maximaDifVoraz(datos,k,l1,l2);
		System.out.println("");
		System.out.println("ENFOQUE VORAZ");
		System.out.println("Grupo 1: " + l1.toString());
		System.out.println("Grupo 2: " + l2.toString());
		System.out.println("Diferencia: " + difEV);
	}
}
