package prPlano;

public class Punto {
	double x;
	double y;
	
	public Punto() {
		x=0;
		y=0;
	}
	
	public Punto(double abscisa, double ordenada) {
		x=abscisa;
		y=ordenada;
	}
	
	public double getAbscisa() {
		return x;
	}
	
	public double getOrdenada() {
		return y;
	}
	
	public void setAbscisa(double a) {
		x=a;
	}
	
	public void setOrdenada(double b) {
		y=b;
	}
	
	public void desplazar (double a, double b) {
		x+=a;
		y+=b;
	}
	
	public double distancia(Punto pto) {
		return Math.sqrt(Math.pow(x-pto.getAbscisa(), 2)+Math.pow(y-pto.getOrdenada(),2));
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
