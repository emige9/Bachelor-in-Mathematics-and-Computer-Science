package prCuentaPalabrasSimpleFicheros;
import java.io.*;
import java.util.*;

public class ContadorPalabrasSig extends ContadorPalabras{
	private String[] noSignificativas;
	private static final int TAM=10;
	private int numPalNoSig;
	
	public ContadorPalabrasSig(String[] palsNS) {
		super();
		noSignificativas=palsNS;
		numPalNoSig=palsNS.length;
	}
	
	public ContadorPalabrasSig(int n, String[] palsNS) {
		super(n);
		noSignificativas=palsNS;
		numPalNoSig=palsNS.length;
	}
	
	public ContadorPalabrasSig(String filNoSig, String del) throws FileNotFoundException{
		super();
		noSignificativas=new String[TAM];
		numPalNoSig=0;
		leerFicheroNoSig(filNoSig, del);
	}
	
	public ContadorPalabrasSig(int n, String filNoSig, String del) throws FileNotFoundException {
		super(n);
		noSignificativas=new String[TAM];
		numPalNoSig=0;
		leerFicheroNoSig(filNoSig, del);
	}
	
	private void leerFicheroNoSig(String filNoSig, String del)throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filNoSig));
		leerPalabrasNoSignificativas(sc,del);
	}
	
	private void leerPalabrasNoSignificativas (String linea, String del) {
 	   Scanner sc = new Scanner(linea);
 	   sc.useDelimiter(del);
 	   while (sc.hasNext()) {
 		   incluyeNoSig(sc.next());
 	   }
 	   sc.close();
    }
	
	private void leerPalabrasNoSignificativas(Scanner sc, String del) {
		while(sc.hasNextLine()) {
			leerPalabrasNoSignificativas(sc.nextLine(), del);
		}
		sc.close();
	}

	protected void incluye (String pal) {
 	   if (esta2(pal)==-1) {
 		   super.incluye(pal);
 	   }
    }
    protected void incluyeNoSig(String pal) {
 	   int k = esta2(pal);
 	   if (k==-1) {
 		   if (noSignificativas.length <= numPalNoSig) {
 			   noSignificativas = Arrays.copyOf(noSignificativas, numPalNoSig + 1);
 		   }   
 		   noSignificativas[numPalNoSig] = pal;
 		   numPalNoSig++;
 	   }
    }
    
    private int esta2 (String pal) {
    	int pos=-1;
    	int i=0;
    	
    	while ((i<numPalNoSig) && (!noSignificativas[i].equalsIgnoreCase(pal))) {
    		i++;
    	}
    	
    	if(i!=numPalNoSig) {
    		pos=i;
    	}
    	
    	return pos;
    }
    
}
