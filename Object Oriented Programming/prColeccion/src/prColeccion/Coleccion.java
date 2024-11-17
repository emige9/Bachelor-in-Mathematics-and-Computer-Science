package prColeccion;
import java.util.Arrays;

public class Coleccion {
	private static final int TAM=10;
	private int[] elementos;
	private int numElem;
	
	public Coleccion() {
		elementos=new int[TAM];
	}
	
	public Coleccion(int tam) {
		if(tam<=0) {
			throw new RuntimeException("Capacidad errónea");
		} else {
			numElem=0;
			elementos=new int[tam];
		}
	}
	
	public int getNumElem() {
		return numElem;
	}
	
	public int getElem(int indice) {
		if(indice<0 || indice>=numElem) {
			throw new RuntimeException("Índice erróneo");
		} 
		
		return elementos[indice];
	}
	
	public boolean sinElementos() {
		return numElem==0;
	}
	
	public void vaciar() {
		numElem=0;
	}
	
	public void anyade(int elemen) {
		int indice=buscar(elemen);
		
		if(numElem==elementos.length) {
			elementos=Arrays.copyOf(elementos, 2*elementos.length);
		}
		
		if(indice==-1) {
			elementos[numElem]=elemen;
			numElem++;
		}
		
	}
	
	public void eliminar(int elemen) {
		int indice=buscar(elemen);
		
		if(indice>0) {
			for(int i=indice; i<numElem; i++) {
				elementos[i]=elementos[i+1];
			}
			elementos[numElem-1]=0;
			numElem--;
		}
	}
	
	public boolean contiene(int elemen) {
		int indice=buscar(elemen);
		return indice>0;
	}
	
	public String toString() {
		String res="[ ";
		
		for(int i=0; i<numElem-1;i++) {
			res+=elementos[i] + ", ";
		}
		
		if(numElem>0) {
			res+=elementos[numElem-1] + " ]";
		}
		
		return res;
	}
	
	private int buscar(int elemen) {
		int i=0;
		int pos=-1;
		
		while(i<numElem && pos==-1) {
			if(elemen==elementos[i]) {
				pos=i;
			}
			i++;
		}
		
		return pos;
		
		
	}
}
