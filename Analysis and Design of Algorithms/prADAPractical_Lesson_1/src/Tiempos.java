import java.util.*;
import java.io.*;


public class Tiempos {
	
	private static int algoritmo1( int n){
	//n >0
		if (n<=2) {
			return 1;
		}
		else {
			return algoritmo1(n-1) + algoritmo1(n-2);
		}
	}
	
	private static void algoritmo2(int [] a) {
		for(int i = 0; i < a.length; i ++) {
			int suma = 0;
		for (int j = 0; j < a.length; j++ ) {
			suma += a[j];
			}
			a[i] = suma;
		}
	}
	
	public static void main(String[] args) {
		Temporizador t = new Temporizador();
		
		int [] n = {1,2,3,4,5,10,20,30};
		long [] tiempos1 = new long[n.length];
		long [] tiempos2 = new long[n.length];
		
		try(PrintWriter fich1 = new PrintWriter("algoritmo1.csv");
				PrintWriter fich2 = new PrintWriter("algoritmo2.csv")) {
			
			fich1.println("Tam,Tiempo");
			fich2.println("Tam,Tiempo");
			
			for(int indPrueba =0; indPrueba<n.length; indPrueba++) {
				t.reiniciar();
				t.iniciar();
				algoritmo1(n[indPrueba]);
				t.parar();
				tiempos1[indPrueba] = t.tiempoPasado();
				
				fich1.print(n[indPrueba]);
				fich1.print(",");
				fich1.println(tiempos1[indPrueba]);
				
				int [] datos = new int[n[indPrueba]];
				Random r = new Random();
				
				for(int j=0; j<datos.length; j++) {
					datos[j]= r.nextInt();
				}
				System.out.println(Arrays.toString(datos));
				
				t.reiniciar();
				t.iniciar();
				algoritmo2(datos);
				t.parar();
				tiempos2[indPrueba] = t.tiempoPasado();
				
				fich2.print(n[indPrueba]);
				fich2.print(",");
				fich2.println(tiempos2[indPrueba]);
			}
			System.out.println(Arrays.toString(tiempos1));
			System.out.println(Arrays.toString(tiempos2));
		}catch (FileNotFoundException e) {
			System.out.println("Fichero no se puede crear");
		}
	}
}
