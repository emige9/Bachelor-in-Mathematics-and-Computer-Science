import paqVacunas.Vacuna;


public class PruebaVacuna {

	public static void main (String[] args) {
		Vacuna V1 = new Vacuna("V1",100.0,2);
		Vacuna V2 = new Vacuna("V2",50.0,4);
		
		System.out.println("Vacuna 1 = " + V1.toString());
		System.out.println("Vacuna 2 = " + V2.toString());
		
		V2.setCantidad(V2.getCantidad()+3);
		
		System.out.println("Vacuna 1 = " + V1.toString());
		System.out.println("Vacuna 2 = " + V2.toString());
		
		Vacuna v2 = new Vacuna ("v2",75.0,5);
		
		System.out.println("Vacuna 3 = " + v2.toString());
		
		if(V2.getCodigo().equalsIgnoreCase(v2.getCodigo())) {
			System.out.println("Las vacunas 2 y 3 son iguales");
		}
	}
}
