import java.util.Arrays;

public class Mayoritario {
	
	public static Elemento mayoritarioFB(Elemento []a) {
		Elemento mayoritario = new Elemento();
		int cont=0;
		boolean encontrado = false;
		
		for (int i=0; i<a.length && !encontrado ; i++) {
			cont=0;
			for (int j=0; j<a.length && !encontrado; j++) {
				
				if(a[i].equals(a[j])) {
					cont++;
					
					if (cont > a.length/2) {
						mayoritario=a[i];
						encontrado = true;
					}
				}
			}
			
			
		}
		
		return mayoritario;
	}
	
	/*public static Elemento mayoritarioFB(Elemento[] a) {
		Elemento m = null;
		int i=0;
		
		while(m==null && i<a.length) {
			if(frecuencia(a,a[i])>a.length/2) {
				m=a[i];
			}
			i++;
		}
		return m;
	}*/
	
	private static int frecuencia(Elemento[] a, Elemento e) {
		int f=0;
		for(int i=0; i<a.length;i++) {
			if(a[i].equals(e)) {
				f++;
			}
		}
		return f;
	}
	
	private static Elemento buscaCandidato(Elemento[] a) {
		Elemento miCandidato = null;
		Elemento[] aux = new Elemento[a.length/2+1];
		int tam=0;
		
		if(a.length > 0) {
			
			if(a.length==1) {
				miCandidato = a[0];
			} else {
				if (a.length%2 == 0) {
					for (int i=0; i<a.length; i=i+2) {
						
						if(a[i].equals(a[i+1])) {
							aux[tam]=a[i];
							tam++;
						}
					}
					aux = Arrays.copyOf(aux, tam);
					miCandidato = buscaCandidato(aux);
				} else {
					for (int i=0; i<a.length-1; i=i+2) {
						
						if(a[i].equals(a[i+1])) {
							aux[tam]=a[i];
							tam++;
						}
					}
					aux[tam] = a[a.length-1];
					tam++;
					aux = Arrays.copyOf(aux, tam);
					miCandidato = buscaCandidato(aux);
			    }
			}
		
		}
		return miCandidato;
	}
	
	public static Elemento mayoritarioDyV (Elemento []a) {
		// buscar candidatos
		
		Elemento candidato = buscaCandidato(a);
		
		//comprobación del mayoritario
		
		return frecuencia(a,candidato)>a.length/2 ? candidato : null;
	}
	
	public static Elemento mayoritarioBoyerMoore(Elemento []a) {
		return null;
	}
	
	public static void main (String[] args) {
		int [] prueba1 = {1,8,7,4,1,2,2,2,2,2,2,2};
		Elemento [] a = new Elemento[prueba1.length];
		
		for (int i=0; i<prueba1.length; i++) {
			a[i] = new Elemento(prueba1[i],0);
		}
		System.out.println("Elem mayoritario FB: " + mayoritarioFB(a));
		System.out.println("Elem mayoritario DyV: " + mayoritarioDyV(a));
		System.out.println("Elem mayoritario Boyer-Moore: " + mayoritarioBoyerMoore(a));

	}
}
