import prNotas.Alumno;
import prNotas.AlumnoException;

public class PruebaAlumno {
	public static void main(String[] args) {
		try {
			Alumno alum1 = new Alumno("22456784F", "Gonzalez Perez, Juan", 5.5);
			Alumno alum2 = new Alumno("33456777S", "Gonzalez Perez, Juan", -3.4);
			System.out.println(alum1.toString());
			System.out.println(alum2.toString());
			
			if (alum1.equals(alum2)) {
				System.out.println("Los datos introducidos son del mismo alumno");
			} else {
				System.out.println("Los datos introducidos no son del mismo alumno");
			}
		} catch(AlumnoException e) {
			System.out.println(e);
		}
	}
}
