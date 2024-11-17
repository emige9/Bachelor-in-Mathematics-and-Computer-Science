package prIndicePalabras;
import java.io.*;
import java.util.*;


public class IndiceLineas extends IndiceAbstracto{
	
	private SortedMap<String,SortedSet<Integer>> indice;
	
	public IndiceLineas() {
		super();
		indice=new TreeMap<>();
	}
	
	public void resolver (String delimitadores) {
		indice.clear();
		int k=1;
		for (String fr : frases) {
			try(Scanner sc = new Scanner(fr)){
				sc.useDelimiter(delimitadores);
				while(sc.hasNext()) {
					String palabra = sc.next().toLowerCase();
					agregarPalabra(palabra, k);
				}
			}
			k++;
		}
	}
	
	private void agregarPalabra(String pal, int k) {
		SortedSet<Integer> ss = (SortedSet<Integer>)indice.getOrDefault(pal, new TreeSet<>());
		ss.add(k);
		indice.put(pal,ss);
	}
	
	public void presentarIndice(PrintWriter pw) {
		for (String pal : indice.keySet()) {
			pw.printf("%-15s", pal);
			pw.println("\t" + indice.get(pal));
		}
		pw.close();
	}
}
