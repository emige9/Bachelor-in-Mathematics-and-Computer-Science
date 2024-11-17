
public class MaximoSub {
	
	public boolean[] resolver(int[] S, int d) {
		boolean[] solIni = new boolean[S.length];
		return resolverVA(S,d,solIni,0,null);
	}
	
	public boolean[] resolverVA(int[] S, int d, boolean[] sol, int etapa, boolean[] mejor){
		
		if(esSolucionCompleta(sol,etapa)) {
			if(suma(sol,S,sol.length) > suma(mejor,S, mejor.length)) {
				mejor = sol;
			}
		} else {
			int i=1;
			
			while (i>=0) {
				
				boolean valor = (i==1);
				int aux = suma(sol,S,etapa) + i*S[etapa];
				
				if(aux <= d) {
					sol[etapa] = valor;
					boolean[] otra = resolverVA(S,d,sol,etapa+1,mejor);
					
					if(suma(otra,S,otra.length) > suma(mejor,S,mejor.length)) {
						mejor = otra;
					}
					//Vuelta atrás
					sol[etapa] = false;
				}
				i--;
			}
		}
		
		return mejor;
	}
	
	private int suma(boolean[] sol, int[] S, int k) {
		int suma = 0;
		
		if(sol == null) {
			return Integer.MIN_VALUE;
		}
		
		for (int i=0; i<k; i++) {
			if(sol[i] == true) {
				suma += S[i];
			}
		}
		
		return suma;
	}
	
	private boolean esSolucionCompleta(boolean[] sol, int etapa) {
		return etapa == sol.length;
	}
}
