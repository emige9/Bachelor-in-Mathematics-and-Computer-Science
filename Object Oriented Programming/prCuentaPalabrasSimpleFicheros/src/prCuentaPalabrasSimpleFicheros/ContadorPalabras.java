package prCuentaPalabrasSimpleFicheros;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.util.NoSuchElementException;

public class ContadorPalabras {
	private int numPalabras;
	private static final int TAM_INICIAL=10;
	private PalabraEnTexto[] palabras;
	
	
	public ContadorPalabras() {
		numPalabras=0;
		palabras=new PalabraEnTexto[TAM_INICIAL];
	}
	
	public ContadorPalabras(int tam) {
		numPalabras=0;
		palabras=new PalabraEnTexto[tam];
	}
	
	private int esta(String pal) {
		int pos=-1;
		int i=0;
		PalabraEnTexto p=new PalabraEnTexto(pal);
		
		while(i<numPalabras && !palabras[i].equals(p)) {
			i++;
		}
		if(i!=numPalabras) {
			pos=i;
		}
		
		return pos;
	}
	
	protected void incluye (String pal) {
		int indice=esta(pal);
		if(indice!=-1) {
			palabras[indice].increments();
		} else {
			if(numPalabras==palabras.length) {
				palabras=Arrays.copyOf(palabras, palabras.length*2);
			}
			
			palabras[numPalabras]= new PalabraEnTexto(pal);
			numPalabras++;
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
			incluyeTodas(lin, del);
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
		int pos = esta(pal);
 	   if (pos != -1) {
 		   return palabras[pos];
 	   } else {
 		   throw new NoSuchElementException("No existe la palabra " + pal);
 	   }
	}
	
	public String toString() {
		StringBuilder sb= new StringBuilder("[");
		for(int i=0; i<numPalabras; i++) {
			sb.append(palabras[i].toString());
			if(i<numPalabras-1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	public void presentaPalabras(String fichero) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fichero);
		presentaPalabras(pw);
	}
	
	public void presentaPalabras(PrintWriter pw) {
		for(int i=0; i<numPalabras; i++) {
			pw.println(palabras[i].toString());
		}
		pw.close();
	}
	
	
}
