import paqVacunas.Comprador;

public class PruebaComprador {
	
	public static void main(String[] args) {
		try {
			Comprador c = new Comprador("comprador1",2);
			c.comprar("V1", 100.0, 2);
			c.comprar("V2", 50.0, 4);
			c.comprar("V3", 150.0, 1);
			System.out.println(c);
			
			c.comprar("V2", 0.0, 2);
			System.out.println(c);
			
			c.eliminar("V1");
			System.out.println(c);
			
			System.out.println("Precio Total: " + c.precioTotal());
			
			c.comprar("V4", -4, 9);
			System.out.println(c);
		} catch (RuntimeException e) {
			System.err.println("ERROR al comprar con precio negativo");
		}
	}
}
