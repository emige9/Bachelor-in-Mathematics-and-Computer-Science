import java.util.Arrays;
import prDatos.DatosException;
import prDatos.Datos;

public class PruebaDatos {
	public static void main(String[] args) {
		String cadena="10 20 5 9 Pepe 10 Maria 12 13 Paco 17 20 Ana 25 Juan Lola";
		String[] secuencia=cadena.split(" ");
		
		if(secuencia.length<3) {
			System.out.println("Error, no hay valores suficientes");
		} else {
			double min=0, max=0;
			try {
				min=Double.parseDouble(secuencia[0]);
				max=Double.parseDouble(secuencia[1]);
				Datos datos = new Datos(Arrays.copyOfRange(secuencia,2,secuencia.length),min,max);
				datos.getDatos();
				System.out.println(datos.toString());
				datos.setRango("0;4");
				System.out.println(datos.toString());
				datos.setRango("15 25");
			} catch (NumberFormatException exc) {
				System.out.println("Error al convertir un valor a número real ( " + exc.getMessage() + ")");
			} catch (DatosException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
