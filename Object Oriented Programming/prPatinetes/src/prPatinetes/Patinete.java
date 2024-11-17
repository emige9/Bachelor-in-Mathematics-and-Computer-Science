package prPatinetes;

public class Patinete implements Comparable<Patinete>{
	private String nombreEmpresa;
	private int codigo;
	private Posicion posicion;
	private double autonomia;
	
	public Patinete (String nombre, int cod, Posicion pos, double aut) throws PatinetesException {
		if (cod<0) {
			throw new PatinetesException("Codigo negativo al crear Patinete");
		}
		if(aut<0) {
			throw new PatinetesException("Autonomia negativa al crear Patinete");
		}
		
		nombreEmpresa=nombre;
		codigo=cod;
		posicion=pos;
		autonomia=aut;
	}
	
	public Patinete(String nombre, int cod, Posicion pos) throws PatinetesException {
		this(nombre,cod,pos,0);
	}
	
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public Posicion getPosicion() {
		return posicion;
	}
	
	public double getAutonomia() {
		return autonomia;
	}
	
	public void setPosicion(Posicion nuevaPos) {
		posicion=nuevaPos;
	}
	
	public void setAutonomia(double nuevaAut) throws PatinetesException{
		if(nuevaAut<0) {
			throw new PatinetesException("Valor negativo al modificar la autonomia");
		}
		autonomia=nuevaAut;
	}
	
	public boolean equals(Object obj) {
		boolean res = obj instanceof Patinete;
		Patinete pat = res ? (Patinete)obj : null;
		return res && nombreEmpresa.equalsIgnoreCase(pat.getNombreEmpresa()) && codigo==pat.getCodigo();
	}
	
	public int hashCode() {
		return nombreEmpresa.toLowerCase().hashCode()+codigo;
	}
	
	public int compareTo(Patinete p) {
		int res = nombreEmpresa.compareToIgnoreCase(p.getNombreEmpresa());
		if(res==0) {
			res=codigo-p.getCodigo();
		}
		return res;
	}
	
	public String toString() {
		return "(Empresa: " + nombreEmpresa + "; Codigo: " + codigo +
				"; " + posicion + "; Autonomia: " + autonomia + ")";
	}
}
