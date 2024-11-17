import prColeccion.Coleccion;

public class PruebaColeccion {
	public static void main(String[] args) {
		Coleccion c=new Coleccion();
		
		if(!c.sinElementos()) {
			System.out.println("Error, colección no vacía");
		}
		
		c.anyade(1);
		c.anyade(2);
		c.anyade(3);
		c.anyade(4);
		c.anyade(5);
		c.anyade(6);
		c.anyade(7);
		c.anyade(8);
		c.anyade(9);
		c.anyade(10);
		c.anyade(11);
		c.anyade(12);
		c.eliminar(12);
		c.eliminar(1);
		c.eliminar(4);
		c.eliminar(7);
		c.eliminar(4);
		c.eliminar(20);
		
		if(c.contiene(1)) {
			System.out.println("Error, encontrado elemento eliminado " + 1);
		}
		if(!c.contiene(2)) {
			System.out.println("Error, elemento no encontrado " + 2);
		}
		
		if(c.contiene(4)) {
			System.out.println("Error, encontrado elemento eliminado " + 4);
		}
		
		if(!c.contiene(6)) {
			System.out.println("Error, elemento no encontrado " + 6);
		}
		
		if(c.contiene(7)) {
			System.out.println("Error, encontrado elemento eliminado " + 7);
		}
		
		if(!c.contiene(11)) {
			System.out.println("Error, elemento no encontrado " + 11);
		}
		
		if(c.contiene(12)) {
			System.out.println("Error, encontrado elemento eliminado " + 12);
		}
		
		String s = c.toString();
		
		if(!s.equals("[ 11, 2, 3, 10, 5, 6, 9, 8 ]")) {
			System.out.println("Error, elementos no adecuados " + s);
		}
		
		System.out.println("Colección: " + s);
	}
}
