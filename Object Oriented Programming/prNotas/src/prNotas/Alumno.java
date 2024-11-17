package prNotas;

public class Alumno {
	private String nombre;
	private String dni;
	private double nota;
	
	public Alumno(String dni, String nombre, double nota) throws AlumnoException {
		
		if(nota<0) {
			throw new AlumnoException("La nota debe ser mayor a 0");
		} else {
			this.nombre=nombre;
			this.dni=dni;
			this.nota=nota;
		}
	}
	
	public Alumno(String dni, String nombre) {
		this.nombre=nombre;
		this.dni=dni;
		this.nota=0;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getDni() {
		return this.dni;
	}
	
	public double getCalificacion() {
		return this.nota;
	}
	
	public String toString() {
		return "DNI: " + getDni() + " Nombre: "+ getNombre() + " Nota: " + getCalificacion();
	}
	
	public boolean equals(Object obj) {
		boolean res = obj instanceof Alumno;
		Alumno alu = res ? (Alumno)obj : null;
		return res && nombre.equals(alu.getNombre()) && dni.equalsIgnoreCase(alu.getDni());
	}
	
	public int hashCode() {
		return nombre.hashCode() + dni.toUpperCase().hashCode();
	}
}
