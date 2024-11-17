import java.util.*;

public class Ranas {
	private char[] estadoInicial, estadoFinal;
	
	public Ranas(char[] ei, char[] ef) {
		estadoInicial = ei;
		estadoFinal = ef;
	}
	
	public List<Integer> resolver() {
		List<Integer> listaPosHueco = new ArrayList<>();
		char[] tablero = Arrays.copyOf(estadoInicial, estadoInicial.length);
		return resolverVA(tablero, listaPosHueco) ? listaPosHueco : null;
	}
	
	public boolean resolverVA(char[] estadoTablero, List<Integer> estadoActSol){
		
		boolean haySol = false;
		int i=0;
		
		if (iguales(estadoTablero, estadoFinal)) {
			haySol=true;
		} else {
			int[] candidatos = siguientes (estadoActSol.get(estadoActSol.size() - 1));
			while (i<candidatos.length && !haySol){
				if(valida(i, estadoTablero)) {
					estadoActSol.add(i);
					char[] estadoTableroNuevo = cambio(i, estadoTablero);
					haySol = resolverVA(estadoTableroNuevo, estadoActSol);					
				}
			
				if(!haySol) {
					estadoActSol.remove(i);
				}
			}
		}
		
		return haySol;
	}
	
	private int[] siguientes (int hueco) {
		int [] siguientes = {hueco-2, hueco-1, hueco+1, hueco+2};
		return siguientes;
	}
	
	private boolean iguales(char[] tablero1, char[] tablero2) {
		
		boolean resultado= true;
		
		if(tablero1.length == tablero2.length) {
			for(int i=0; i< tablero1.length; i++) {
				if(tablero1[i] != tablero2[i])
					resultado = false;
			}
		}
		
		return resultado;
	}
	
	private boolean valida(int pos, char[] estadoTablero) {
		boolean esValido = true;
		
		if (pos<0 || pos>=estadoTablero.length) {
			esValido = false;
		}
		
		if((estadoTablero[pos] == 'M' && pos>posHueco(estadoTablero)) || (estadoTablero[pos] == 'H' && pos<posHueco(estadoTablero))) {
			esValido=false;
		}
		
		return esValido;
	}
	
	private int posHueco(char[] estadoTablero) {
		int posHueco=0;
		int j=0;
		boolean encontrado = false;
		
		while (j<estadoTablero.length && !encontrado) {
			if(estadoTablero[j] == ' ') {
				posHueco = j;
			}
		}
		
		return posHueco;

	}
	
	private char[] cambio (int posicion, char[] estadoTablero) {
		
		estadoTablero[posHueco(estadoTablero)] = estadoTablero[posicion];
		estadoTablero[posicion] = ' ';
		
		return estadoTablero;
	}
	
	public static void main (String[] args) {
		char[] ei = {'M', 'M', ' ', 'H', 'H'};
		char[] ef = {'H', 'H', ' ', 'M', 'M'};
		Ranas problema = new Ranas (ei, ef);
		problema.resolver();
		System.out.println(problema.resolver().toString());
	}
}
