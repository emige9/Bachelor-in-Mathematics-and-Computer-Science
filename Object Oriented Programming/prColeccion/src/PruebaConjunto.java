import prColeccion.Conjunto;

public class PruebaConjunto {
	public static void main(String[] args) {
		Conjunto c1= new Conjunto();
		c1.anyade(1);
		c1.anyade(3);
		c1.anyade(5);
		c1.anyade(1);
		c1.anyade(3);
		c1.anyade(5);
		
		Conjunto c2= new Conjunto();
		
		c2.anyade(2);
		c2.anyade(4);
		c2.anyade(6);
		
		Conjunto c3=c1.union(c2);
		Conjunto c4=c3.interseccion(c1);
		Conjunto c5=c3.interseccion(c2);
		System.out.println("C1: " + c1);
		System.out.println("C2: " + c2);
		System.out.println("C1 U C2: " + c3);
		System.out.println("(C1 U C2) n C1: " + c4);
		System.out.println("(C1 U C2) n C2: " + c5);
	}
}
