package prIndicePalabras;
import java.io.*;


public interface Indice {
	
	void agregarFrase(String frase);
	void resolver(String delimitadores);
	void presentarIndice(PrintWriter pw);
	void presentarIndiceConsola();
}
