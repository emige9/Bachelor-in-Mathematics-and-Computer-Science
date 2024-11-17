package prNotas;

public class MediaAritmetica implements CalculoMedia {
	public double calcular(Alumno[] alumnos) throws AlumnoException{
		if(alumnos.length==0) {
			throw new AlumnoException("No hay alumnos");
		}
		double s=0;
		for(Alumno a: alumnos) {
			s+=a.getCalificacion();
		}
		
		return s/alumnos.length;
	}
}
