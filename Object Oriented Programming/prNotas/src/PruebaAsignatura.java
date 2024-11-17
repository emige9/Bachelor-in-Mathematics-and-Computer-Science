import prNotas.AlumnoException;
import prNotas.Asignatura;
import prNotas.Alumno;

public class PruebaAsignatura {
	public static void main(String[] args) {
		String[] alumnos= {"12455666F;Lopez Perez, Pedro;6.7", "33678999D;Merlo Gomez, Isabel;5.8", "23555875G;Martinez Herrera, Lucia;9.1"};
		Asignatura POO = new Asignatura("POO", alumnos);
		try {
			System.out.println("Nota Media: " + POO.getMedia());
		} catch (AlumnoException e) {
			System.out.println(e);
		}
		
		for (Alumno al : POO.getAlumnos()) {
			System.out.println(al.getDni());
		}
		
		try {
			Alumno al1= new Alumno("12455666F" , "Lopez Perez, Pedro");
			System.out.println("Nota de " + al1.toString() + ": " + POO.getCalificacion(al1));
			//Alumno al2= new Alumno("12455666F" , "Lopez Lopez, Pedro");
			//System.out.println("Nota de " + al2.toString() + ": " + POO.getCalificacion(al2));
		} catch (AlumnoException e) {
			System.out.println(e);
		}
	}
}
