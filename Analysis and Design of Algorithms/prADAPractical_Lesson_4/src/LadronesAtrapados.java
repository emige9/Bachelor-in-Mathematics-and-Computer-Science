import java.util.Arrays;

public class LadronesAtrapados {
	
	public static int LadronesAtrapadosEstrategia1(char[] a, int k) {
		int resultado=0;
		
		for (int i=0; i<a.length; i++) {
			if(a[i] == 'P') {
				int j = i-k;
				boolean encontrado = false;
				while (j<=i+k && !encontrado) {
					if(a[j] == 'T') {
						
					}
					j++;
				}
			}
		}
		
		return resultado;
	}
	
	public static int LadronesAtrapadosEstrategia2(char[] a, int k){
		return 0;
	}
	
	public static int LadronesAtrapadosEstrategia3(char[] a, int k) {
		return 0;
	}
	
	public static void main (String[] args) {
		
		char[] datos1 = {'P', 'T', 'T', 'P', 'T'};
		int k1=1;
		
		char[] datos2 = {'T', 'T', 'P', 'P', 'T', 'P'};
		int k2 = 2;
		
		char[] datos3 = {'P', 'T', 'P', 'T', 'T', 'P'};
		int k3 = 3;
		
		System.out.println("Resultado Estrategia1-Ejmplo1: " + LadronesAtrapadosEstrategia1(datos1,k1));
		System.out.println(Arrays.toString(datos1));
		System.out.println("Resultado Estrategia1-Ejmplo2: " + LadronesAtrapadosEstrategia1(datos2,k2));
		System.out.println(Arrays.toString(datos2));
		System.out.println("Resultado Estrategia1-Ejmplo3: " + LadronesAtrapadosEstrategia1(datos3,k3));
		System.out.println(Arrays.toString(datos3));

	}
}
