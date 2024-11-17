// ALUMNO: Emilio Gómez Esteban
// GRUPO y TITULACION: 2ºD, Doble Grado Matemáticas + Ing. Informática

/**
 *
 * Clase para resolucion del problema de la
 * Supersecuencia Comun mas Corta para dos cadenas
 * 
 */

import java.util.Random;

public class SCMC {
	
	private String r, t; // Las dos cadenas de la instancia
	private String sigma; // El alfabeto de la instancia
	private int m[][]; // la matriz para resolucion por Prog. Dinamica
	private static Random rnd = new Random(); // generador de aleatorio
	private static Direccion[][] b;
	
	/**
	 * Crea una instancia del problema
	 * @param sigma : alfabeto para la instancia
	 * @param r     : primera cadena
	 * @param t     : segunda cadena
	 */
	public SCMC(String sigma, String r, String t) {
		this.r = r;
		this.t = t;
		this.sigma = sigma;
		m = new int[1+r.length()][1+t.length()];
		b = new Direccion[r.length()+1][t.length()+1];
	}
		
	/**
	 * Crea una instancia aleatoria del problema
	 * @param longMax  : longitud maxima de las cadenas
	 * @param tamSigma : tamaño del alfabeto
	 */
	public SCMC(int longMax, int tamSigma) {		
		this.sigma = Utils.alfabeto(tamSigma);
		r = Utils.cadenaAleatoria(rnd.nextInt(1+longMax),sigma);
		t = Utils.cadenaAleatoria(rnd.nextInt(1+longMax),sigma);
		m = new int[1+r.length()][1+t.length()];
		b = new Direccion[r.length()+1][t.length()+1];
	}

	public String r(){
		return r;
	}

	public String t(){
		return t;
	}
	
	public String sigma(){
		return sigma;
	}

	public int m(int i, int j){
		if (i<m.length && j<m[0].length) {
			return m[i][j];
		} else {
			return -1;
		}
	}

		
	/**
	 * Soluciona la instancia por Prog Dinamica, es decir, rellena
	 * la tabla @m
	 */
	public void solucionaPD(){
		// A Implementar por el alumno
		m[0][0] = 0;
		b[0][0] = Direccion.ARRIBA;
		for(int i=1; i<r.length()+1; i++) {
			m[i][0] = i;
			b[i][0] = Direccion.ARRIBA;
		}
		
		for(int j=0; j<t.length()+1; j++) {
			m[0][j] = j;
			b[0][j]=Direccion.IZDA;
		}
		
		for(int i=1; i<r.length()+1; i++) {
			for(int j=1; j<t.length()+1; j++) {
				if (r.charAt(i-1)==t.charAt(j-1)) {
					m[i][j] = 1 + m[i-1][j-1];
					b[i-1][j-1] = Direccion.DIAG;
				} else if (m[i-1][j] >= m[i][j-1]){
					m[i][j] = 1 + m[i][j-1];
					b[i-1][j-1] = Direccion.IZDA;
				} else {
					m[i][j] = 1 + m[i-1][j];
					b[i-1][j-1] = Direccion.ARRIBA;
				}
			}
		}
	}
	
	public enum Direccion{
		ARRIBA,IZDA,DIAG;
	}

	/**
	 * @return : devuelve la longitud de la solucion
	 *           a la instancia, es decir, la longitud 
	 *           de la supersecuencia comun más corta de @r y @t
	 *           a partir de la tabla obtenida por Prog Dinamica
	 */
	public int longitudDeSolucionPD(){
		// A Implementar por el alumno	
		return m[r.length()][t.length()];
	}

	/**
	 * @return Devuelve una solucion optima de la instancia, es decir
	 *         una supersecuencia comun mas corta de @r y @t
	 */
	public String unaSolucionPD(){
		// A Implementar por el alumno
		return construirSCMC(r, r.length(), t, t.length());
	}
	
	public static String construirSCMC(String r, int i, String t, int j) {
		if(i==0 && j==0) {
			return "";
		} else if(i==0) {
			return construirSCMC(r,i,t,j-1).concat(String.valueOf(t.charAt(j-1)));
		} else if(j==0) {
			return construirSCMC(r,i-1,t,j).concat(String.valueOf(r.charAt(i-1)));
		}else {
			if(b[i-1][j-1].equals(Direccion.DIAG)) {
				return construirSCMC(r,i-1,t,j-1).concat(String.valueOf(r.charAt(i-1)));
			} else if (b[i-1][j-1].equals(Direccion.ARRIBA)) {
				return construirSCMC(r,i-1,t,j).concat(String.valueOf(r.charAt(i-1)));
			} else {
				return construirSCMC(r,i,t,j-1).concat(String.valueOf(t.charAt(j-1)));
			}
		}	
	}
	

		
	// representacion como String de la instancia
	public String toString(){
		return "Sigma="+Utils.entreComillas(sigma)
		        +", r="+Utils.entreComillas(r)
		        +", s="+Utils.entreComillas(t);
	}



	// Obtiene una solucion al problema por "fuerza bruta"
	public String unaSolucionFB() {
		int l = Math.max(r.length(),t.length());
		String res = null;
		for(l=Math.max(r.length(),t.length()); res==null; l++)
		  res = unaSolucionFB("",l);
		return res;
	}

	// método auxiliar recursivo
	private String unaSolucionFB(String s, int l) {
		String str = null;
		if(l==0) {
			if(Utils.esSupersecuencia(s,r) && Utils.esSupersecuencia(s,t))
				str = s;
		}	
		else
			for(int i=0; i<sigma.length(); i++) {
				str = unaSolucionFB(s+sigma.charAt(i),l-1);
				if(str!=null) break;
			}
		return str;
	}
	
	
}
