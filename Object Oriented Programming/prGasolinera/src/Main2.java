import java.util.HashMap;

import java.util.Map;

import prGasolinera.*;

public class Main2 {

	public static void main(String[] args) {
		try {
			Ticket t1 = new Ticket(1,"Teatinos","1111AAA",50.0,2.0);
			System.out.println("Ticket sin promocion: \n   " + t1);
			TicketPromocion t2 = new TicketPromocion(1,"Teatinos","1111AAA",50.0,2.0,0.10);
			System.out.println("Ticket con 10% de descuento: \n   " + t2);
			
			System.out.println("----------------------------------");
			//Creamos aplicación con los precios de cada tipo de combustible
			Map<String,Double> precios = new HashMap<>();
			precios.put(Gasolinera.GASOLINA95, 1.28);
			precios.put(Gasolinera.GASOLINA98, 1.38);
			precios.put(Gasolinera.DIESEL, 1.25);
			precios.put(Gasolinera.DIESELPLUS, 1.30);
			
			GasolineraPromocion estacion3 = 
					new GasolineraPromocion("TeatinosPROMO",precios,"surtidores.txt");
			//Estacion 3: orden natural y con promocion
			estacion3.repostar("1111AAA",Gasolinera.GASOLINA95,1,80);
			estacion3.repostar("1111AAA",Gasolinera.GASOLINA95,2,80);
			estacion3.repostar("1111AAA",Gasolinera.GASOLINA95,2,80);
			estacion3.repostar("2222BBB",Gasolinera.DIESEL,1,45);
			estacion3.repostar("2222BBB",Gasolinera.DIESELPLUS,1,45);
			estacion3.facturar("1111AAA");
			estacion3.facturar("2222BBB");
			System.out.println(estacion3);
			estacion3.repostar("1111AAA",Gasolinera.GASOLINA95,1,20);
			estacion3.repostar("1111AAA",Gasolinera.GASOLINA95,2,60);
			estacion3.repostar("1111AAA",Gasolinera.GASOLINA95,2,60);
			estacion3.repostar("2222BBB",Gasolinera.DIESEL,1,45);
			estacion3.repostar("2222BBB",Gasolinera.DIESELPLUS,1,45);
			estacion3.facturar("1111AAA");
			estacion3.facturar("2222BBB");
			System.out.println(estacion3);

		}catch(Exception e) {
			System.err.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
