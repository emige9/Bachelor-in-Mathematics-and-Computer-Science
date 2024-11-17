////////////////////////////////////////////////////////////////////////////////////////////
// ALUMNO: Emilio Gómez Esteban
// GRUPO: 2ºD (Doble Grado Mat. + Ing. Inf.)
////////////////////////////////////////////////////////////////////////////////////////////

import java.util.Scanner;

public final class BuscaElem{
	
	public static <T extends Comparable<? super T>> T kesimo(T v[], int k) {
		return kesimoRec(v,0,v.length-1,k);
	}

	public static <T extends Comparable<? super T>> T kesimoRec(T v[], int izq, int der, int k) {
		
		if(izq < der) {
			int p = OrdenacionRapida.partir(v,v[izq], izq, der);
			if(p == k) {
				return v[k];
			}else if(k < p) {
				return kesimoRec(v, izq, p-1, k);
			}else {
				return kesimoRec(v, p+1, der, k);
			}
		}
		return v[k];
    }
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int maxvector;
		int i,k;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce el numero de posiciones del vector: ");
		maxvector=sc.nextInt();
		Integer v[]=new Integer[maxvector];

		System.out.print("Introduce "+maxvector+" enteros separados por espacios: ");
		for (i=0;i<maxvector;i++) { v[i]=sc.nextInt();}
		System.out.print("Introduce la posicion k deseada (de 1-"+maxvector+"): ");
		k=sc.nextInt();
		Integer elem=kesimo(v,k-1);
		System.out.print("El elemento "+k+"-esimo es: "+elem);

	}

}
