import prPlano.Punto;

public class PruebaPuntos {

	public static void main (String[] args) {
		Punto punto1=new Punto(1,4);
		Punto punto2=new Punto(2,3);
		
		System.out.println(punto1.distancia(punto2)); 
		
		punto1.desplazar(2, 2);
		System.out.println(punto1.toString());
		
		System.out.println(punto1.distancia(punto2)); 
	}
}
