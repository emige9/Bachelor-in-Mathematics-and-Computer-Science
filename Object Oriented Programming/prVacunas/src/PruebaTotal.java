import paqVacunas.Comprador;
import paqVacunas.CompradorVIP;

public class PruebaTotal {
	static public void main(String[] args) {
		try { 
			Comprador c = new Comprador("comprador",3);
			
			System.out.println("Comprador creado");
			c.comprar("V1", 100.0, 4);
			System.out.println("Vacuna V1 comprada");
			c.comprar("V2", 76.0, 6);
			System.out.println("Vacuna V2 comprada");
			c.comprar("V3", 154.0, 5);
			System.out.println("Vacuna V3 comprada");
			System.out.println(c);
			
			c.comprar("V2", 0.0, 3);
			System.out.println("Añadidas 3 unidades más a V2");
			System.out.println(c);
			
			c.comprar("V4", 78.0, 12);
			System.out.println("Vacuna V4 comprada");
			System.out.println(c);
			
			System.out.println("Precio Total Compra: " 
								+ c.precioTotal());
			
			CompradorVIP cVIP = 
					new CompradorVIP("compradorVIP",10);
			
			System.out.println("\n\nComprador VIP creado");
			cVIP.comprar("V1", 100.0, 4);
			System.out.println("Vacuna V1 comprada");
			cVIP.comprar("V2", 76.0, 6);
			System.out.println("Vacuna V2 comprada");
			cVIP.comprar("V3", 154.0, 5);
			System.out.println("Vacuna V3 comprada");
			System.out.println(cVIP);
			
			cVIP.comprar("V2", .0, 3);
			System.out.println("Añadidas 3 unidades más a V2");
			System.out.println(cVIP);
			
			cVIP.comprar("V4", 78.0, 12);
			System.out.println("Vacuna V4 comprada");
			System.out.println(cVIP);
			
			System.out.println("Precio Total Compra: " 
								+ cVIP.precioTotal());
		} catch (RuntimeException  e) {
			System.err.println("ERROR. " + e.getMessage());
		}
	}
}
