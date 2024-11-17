package prCuentaPalabrasSimpleColecciones;

import java.io.*;
import java.util.*;


public class ContadorPalabras {
	private SortedSet<PalabraEnTexto> palabras;   //Conjunto ordenado
	
	
	public ContadorPalabras() {
		palabras = new TreeSet<>();
	}
	
	
	protected void incluye (String pal) {
		try {
			PalabraEnTexto palabra = this.encuentra(pal);
			palabra.increments();
		} catch(NoSuchElementException ne) {
			palabras.add(new PalabraEnTexto(pal));
		}
	}
	
	private void incluyeTodas(String linea, String del) {
		try(Scanner sc=new Scanner(linea)){
			sc.useDelimiter(del);
		
			while(sc.hasNext()) {
				incluye(sc.next());
			}
		
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void incluyeTodas(String[] texto, String del) {
		
		for(String lin : texto) {
			this.incluyeTodas(lin, del);
		}
	}
	
	public void incluyeTodasFichero(String nomFich, String del) {
		try(Scanner sc = new Scanner (new File(nomFich))){
			leerFichero(sc,del);
		} catch(FileNotFoundException e) {
			System.out.println(e);
		}
		
	}
	
	private void leerFichero(Scanner sc, String del) {
		while(sc.hasNextLine()) {
			incluyeTodas(sc.nextLine(),del);
		}
	}
	
	public PalabraEnTexto encuentra(String pal) {
		PalabraEnTexto p = new PalabraEnTexto(pal);
		if(!palabras.contains(p)) {
			throw new NoSuchElementException("La palabra " + pal + "no se encuentra en el conjunto");
		}
		Iterator<PalabraEnTexto> iter=palabras.iterator();
		PalabraEnTexto res=null;
		
		while(iter.hasNext() && !p.equals(res)) {
			res=iter.next();
		}
		
		return res;
	}
	
	public String toString() {
		return palabras.toString();
	}
	
	public void presentaPalabras(String fichero) throws FileNotFoundException {
		try {
    		PrintWriter pw = new PrintWriter(fichero);
        	presentaPalabras(pw);
        	pw.close();
    	} catch (IOException e) {
    		throw new FileNotFoundException();
    	}
	}
	
	public void presentaPalabras(PrintWriter pw) {
		for(PalabraEnTexto palabra : palabras) {
			pw.println(palabra);
		}
	}
	
	
}

