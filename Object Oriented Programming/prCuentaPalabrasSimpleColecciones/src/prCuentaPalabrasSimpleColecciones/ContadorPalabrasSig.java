package prCuentaPalabrasSimpleColecciones;
import java.util.*;
import java.io.*;

public class ContadorPalabrasSig extends ContadorPalabras{
	private Set<String> noSignificativas;
	
	public ContadorPalabrasSig(Collection<String> palsNS) {
		super();
		noSignificativas = new HashSet<>();
		for(String p : palsNS) {
			noSignificativas.add(p.toUpperCase());
		}
	}
	
	public ContadorPalabrasSig(String fichNoSig, String del) throws FileNotFoundException {
		super();
		noSignificativas = new HashSet<>();
		try {
			leerFicheroNoSig(fichNoSig,del);
		}catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	private void leerFicheroNoSig(String filNoSig, String del) throws FileNotFoundException{
		try(Scanner sc = new Scanner (new File(filNoSig))){
			leerPalabrasNoSignificativas(sc,del);
		}catch (FileNotFoundException e) {
			e.getMessage();
		}
	}
	
	private void leerPalabrasNoSignificativas(Scanner sc, String del) {
		sc.useDelimiter(del);
		while(sc.hasNext()) {
			leerPalabrasNoSignificativas(sc.next(),del);
		}
	}
	
	private void leerPalabrasNoSignificativas (String linea, String del) {
	 	   try (Scanner sc = new Scanner(linea)){
	 		  while (sc.hasNext()) {
		 		   noSignificativas.add(sc.next().toUpperCase());
		 	   }
	 	   }
	    }
	
	protected void incluye(String pal) {
		if (!noSignificativas.contains(pal.toUpperCase())) {
     		super.incluye(pal);
     	}
	}
}
