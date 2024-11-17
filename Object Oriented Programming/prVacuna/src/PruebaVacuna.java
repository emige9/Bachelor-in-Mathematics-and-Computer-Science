import vacunas.Vacuna;

public class PruebaVacuna {
	public static void main(String[] args) {
		Vacuna v1=new Vacuna("Moderna-001",100,5);
		Vacuna v2=new Vacuna("AstraZeneca-001",200,8);
		Vacuna v3=new Vacuna("astrazeneca-001",160,10);
		
		System.out.println("El numero total de dosis de la vacuna " + v1.getCodigo() + " es " + v1.dosisTotales());
		System.out.println("El numero total de dosis de la vacuna " + v2.getCodigo() + " es " + v2.dosisTotales());
		System.out.println("El numero total de dosis de la vacuna " + v3.getCodigo() + " es " + v3.dosisTotales());
		
		if(v2.getCodigo().equalsIgnoreCase(v3.getCodigo())) {
			System.out.println("Las vacunas " + v2.toString() + " y " + v3.toString() + " son iguales");
		}
		
		
		if(!v1.getCodigo().equalsIgnoreCase(v2.getCodigo())) {
			System.out.println("Las vacunas " + v1.toString() + " y " + v2.toString() + " no son iguales");
		}
	}
}
