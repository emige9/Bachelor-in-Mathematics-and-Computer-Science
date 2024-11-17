package prNotas;

public class MediaArmonica implements CalculoMedia{
	public double calcular(Alumno[] alumnos) throws AlumnoException{
		int k=0;
		double s=0;
		for(Alumno a: alumnos) {
			if(a.getCalificacion()>0) {
				s+=1/a.getCalificacion();
				k++;
			}
		}
		if(k==0) {
			throw new AlumnoException("No hay alumnos");
		}
		return k/s;
	}
}
