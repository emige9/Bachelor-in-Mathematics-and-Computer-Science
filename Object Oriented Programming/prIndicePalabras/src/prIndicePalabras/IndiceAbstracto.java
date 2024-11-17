package prIndicePalabras;
import java.io.*;
import java.util.*;

public abstract class IndiceAbstracto implements Indice{
	
	protected List<String> frases;
	
	public IndiceAbstracto() {
		frases = new ArrayList<>();
	}
	
	public void agregarFrase(String frase) {
		frases.add(frase);
	}
	
	public void presentarIndiceConsola() {
		PrintWriter pw= new PrintWriter(System.out);
		this.presentarIndice(pw);
	}
}
