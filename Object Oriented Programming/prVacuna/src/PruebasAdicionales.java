
import vacunas.AlmacenVacunas;
import vacunas.Vacuna;
import vacunas.VacunaDosisExtra;

/**
 * La clase PruebasAdicinales prueba la clase AlmacenVacunas
 * @author POO
 *
 */
public class PruebasAdicionales {

	public static void main(String[] args) {
		try {
			// Se crea una vacuna
			Vacuna v1, v2;
			v1 = new Vacuna("Pfizer-001", 150, 5);
			v2 = new VacunaDosisExtra("Pfizer-001", 150, 5);
			
			// Se imprimen ambas vacunas
			System.out.println(v1);
			System.out.println(v2);
			
			// Se crean tres almacenes de vacunas
			AlmacenVacunas carlosHaya, materno, clinico;
			carlosHaya = new AlmacenVacunas("Carlos Haya");
			materno = new AlmacenVacunas("Hospital Materno");
			clinico = new AlmacenVacunas("Hospital Cl�nico");

			// Se almacenan vacunas diversas en Carlos Haya
			carlosHaya.almacenar("Moderna-001", 100, 5);
			carlosHaya.almacenar("AstraZeneca-001", 200, 8);
			carlosHaya.almacenar("astrazeneca-001", 160, 10);
			carlosHaya.almacenar("Moderna-001", 200, 6);
			carlosHaya.almacenar("J&J-001", 200, 10);
			carlosHaya.almacenar("Pfizer-001", 150, 6);

			// ... en el hospital Materno
			materno.almacenar("J&J-001", 200, 10);
			materno.almacenar("Moderna-001", 100, 10);
			materno.almacenar("J&J-001", 200, 10);

			//... en el Cl�nico
			clinico.almacenar("Pfizer-001", 50, 6);
			clinico.almacenar("Pfizer-001", 150, 6);
			
			// Se inoculan todas las de J&J de Carlos Haya
			carlosHaya.inocularTodas("J&J");
			

			// Se imprimen en consola
			System.out.println("Los tres almacenes de vacunas son:");
			System.out.println(carlosHaya);
			System.out.println(materno);
			System.out.println(clinico);

			// Se da informaci�n sobre el n�mero de dosis almacenadas en Carlos Haya
			System.out.println("El n�mero total de dosis en el Hospital Carlos Haya es: " + carlosHaya.totalDosis());
			
			// Se intenta lamacenar un n�mero negativo de viales de una vacuna
			clinico.almacenar("Pfizer-001", -20, 5);

		} catch (RuntimeException re) {
			System.err.println("ERROR. Se ha intentado almacenar una vacuna con un n�mero de viales o dosis negativo");
		}

	}

}