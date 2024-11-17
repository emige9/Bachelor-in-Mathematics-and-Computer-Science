package prNotas;
import java.util.Arrays;
import java.util.Locale;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Asignatura {
	private String nombre;
	private String[] errores;
	private Alumno[] alumnos;
	
	public Asignatura(String nombre, String[] alumnos) {
		this.nombre=nombre;
		pasarAlumnos(alumnos);
	}
	
	public double getCalificacion(Alumno a) throws AlumnoException{
		int i=0;
		while((i<alumnos.length) && (!a.equals(alumnos[i]))) {
			i++;
		}
		
		if(i==alumnos.length) {
			throw new AlumnoException("El alumno " + a + " no se encuentra");
		}
		
		return alumnos[i].getCalificacion();
	}
	
	public double getMedia() throws AlumnoException {
		if(alumnos.length==0) {
			throw new AlumnoException("No hay alumnos");
		}
		double s=0;
		for(Alumno a : alumnos) {
			s+=a.getCalificacion();
		}
		return s/alumnos.length;
	}
	
	public double getMedia(CalculoMedia calc) throws AlumnoException{
		return calc.calcular(alumnos);
	}
	
	public Alumno[] getAlumnos() {
		return alumnos;
	}
	
	public String[] getErrores() {
		return errores;
	}
	
	public String toString() {
		StringBuilder sb= new StringBuilder(80*alumnos.length + 80*errores.length);
		sb.append(nombre);
		sb.append(": { ");
		sb.append(Arrays.toString(alumnos));
		sb.append(", ");
		sb.append(Arrays.toString(errores));
		sb.append(" }");
		return sb.toString();
	}
	
	private void pasarAlumnos(String[] al) {
		int numErrores=0;
		int numAlumnos=0;
		errores=new String[al.length];
		alumnos=new Alumno[al.length];
		
		for(String a : al) {     // para todo String a perteneciente al array al
			try (Scanner sc = new Scanner(a)){
				sc.useLocale(Locale.ENGLISH);
				sc.useDelimiter("[;]");
				String dni = sc.next();
				String nom = sc.next();
				double cali = sc.nextDouble();
				alumnos[numAlumnos]=new Alumno(dni,nom,cali);
				numAlumnos++;
			} catch (InputMismatchException e) {
				errores[numErrores]="ERROR. Calificacion no numérica: " + a;
				numErrores++;
			} catch (NoSuchElementException e) {
				errores[numErrores]="ERROR. Faltan datos: " + a;
				numErrores++;
			} catch (AlumnoException e) {
				errores[numErrores]="ERROR. Calificación negativa: " + a;
				numErrores++;
			}
			
		}
		errores=Arrays.copyOf(errores, numErrores);
		alumnos=Arrays.copyOf(alumnos, numAlumnos);
	}
}
