package prNotas;

public class MediaSinExtremos implements CalculoMedia{
	private double min;
	private double max;
	public MediaSinExtremos(double n, double x) {
		min=n;
		max=x;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
	
	public double calcular(Alumno[] alumnos) throws AlumnoException{
		int k=0;
		double s=0;
		for(Alumno a: alumnos) {
			if(min<=a.getCalificacion() && a.getCalificacion()<=max) {
				s+=a.getCalificacion();
				k++;
			}
		}
		if(k==0) {
			throw new AlumnoException("No hay alumnos");
		}
		
		return s/k;
	}
}
