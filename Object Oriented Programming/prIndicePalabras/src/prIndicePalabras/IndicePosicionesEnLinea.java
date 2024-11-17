package prIndicePalabras;
import java.io.*;
import java.util.*;

public class IndicePosicionesEnLinea extends IndiceAbstracto{
	
	private SortedMap<String, SortedMap<Integer, SortedSet<Integer>>> indice;
	
	public IndicePosicionesEnLinea() {
		super();
		indice = new TreeMap<>();
	}
	
	public void resolver(String delimitadores) {
		indice.clear();
		int numLin=1;
		int numPos=1;
		for (String fr : frases) {
			try(Scanner sc = new Scanner(fr)){
				sc.useDelimiter(delimitadores);
				while(sc.hasNext()) {
					String palabra = sc.next().toLowerCase();
					agregarPalabra(palabra, numLin, numPos);
					numPos++;
				}
				numPos=1;
			}
			numLin++;
			
		}
	}
	
	private void agregarPalabra(String pal, int numLin, int numPos) {
		SortedMap<Integer, SortedSet<Integer>> lin = indice.getOrDefault(pal, new TreeMap<>());
		SortedSet<Integer> pos = lin.getOrDefault(numLin, new TreeSet<>());
		pos.add(numPos);
		lin.put(numLin, pos);
		indice.put(pal, lin);
	}
	
	public void presentarIndice(PrintWriter pw) {
		for (String pal : indice.keySet()) {
    		pw.printf("%-15s%n", pal);
    		for (int k : indice.get(pal).keySet()) {
    			pw.printf(" %4d", k);
    			pw.println("\t" + indice.get(pal).get(k));
    		}
    		
    	 }
		pw.close();
	}
}
