import java.util.HashMap;

import java.util.Map;

import prGasolinera.*;

public class Main1 {

	public static void main(String[] args) {
		try {
			//Creamos aplicación con los precios de cada tipo de combustible
			Map<String,Double> precios = new HashMap<>();
			precios.put(Gasolinera.GASOLINA95, 1.28);
			precios.put(Gasolinera.GASOLINA98, 1.38);
			precios.put(Gasolinera.DIESEL, 1.25);
			precios.put(Gasolinera.DIESELPLUS, 1.30);
			
			//Estacion 1: orden natural y sin promocion
			System.out.println ("Orden natural y sin promocion");
			System.out.println("------------------------------");
			Gasolinera estacion = 
				new Gasolinera("Teatinos",precios,"surtidores.txt");
			
			System.out.println("Estado inicial: " + estacion);
			estacion.repostar("1111AAA",Gasolinera.GASOLINA95,1,20);
			estacion.repostar("1111AAA",Gasolinera.GASOLINA95,2,60);
			estacion.repostar("1111AAA",Gasolinera.GASOLINA95,2,60);
			estacion.repostar("2222BBB",Gasolinera.DIESEL,1,45);
			estacion.repostar("2222BBB",Gasolinera.DIESELPLUS,1,45);
			estacion.facturar("1111AAA");
			estacion.facturar("2222BBB");
			System.out.println("Despues de repostar: " + estacion);
			
			System.out.println("\nOrden alternativo y sin promocion");
			System.out.println("------------------------------");
			//Estacion 2: orden alternativo y sin promocion
			Gasolinera estacion2 = 
					new Gasolinera("Ampliacion",precios,"surtidores.txt",
							new TicketOrdenAlternativo());
			estacion2.repostar("1111AAA",Gasolinera.GASOLINA95,1,20);
			estacion2.repostar("1111AAA",Gasolinera.GASOLINA95,2,60);
			estacion2.repostar("1111AAA",Gasolinera.GASOLINA95,2,60);
			estacion2.repostar("2222BBB",Gasolinera.DIESEL,1,45);
			estacion2.repostar("2222BBB",Gasolinera.DIESELPLUS,1,45);
			estacion2.facturar("1111AAA");
			estacion2.facturar("2222BBB");
			System.out.println("Despues de repostar: " + estacion2);
		}catch(Exception e) {
			System.err.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
