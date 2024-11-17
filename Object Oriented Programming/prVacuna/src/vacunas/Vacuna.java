// Apellidos y Nombre: Emilio Gómez Esteban
// Titulación: Doble Grado de Matemáticas + Ing Informática
// Grupo: 1ºA

package vacunas;

public class Vacuna {
	private String codigo;
	private int dosis;
	private int viales;
	
	public Vacuna(String codigo, int viales, int dosis) {
		
		if(viales<=0 || dosis<=0) {
			throw new RuntimeException("La cantidad de viales o de dosis no es correcta");
		}
		
		this.codigo=codigo;
		this.dosis=dosis;
		this.viales=viales;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public int getDosis() {
		return this.dosis;
	}
	
	public int getViales() {
		return this.viales;
	}
	
	public void setDosis(int dosisNuevas) {
		
		if(dosisNuevas<=0) {
			throw new RuntimeException("Cantidad de dosis introducida no válida");
		} else {
			this.dosis=dosisNuevas;
		}
	}
	
	public void setViales(int vialesNuevos) {
		
		if(vialesNuevos<=0) {
			throw new RuntimeException("Cantidad de viales introducida no válida");
		} else {
			this.viales=vialesNuevos;
		}
	}
	
	public int dosisTotales() {
		return this.dosis*this.viales;
	}
	
	public String toString() {
		return "(" + this.codigo.toUpperCase() + ": " + this.viales + " x " + this.dosis + " dosis)";
	}
	
}
