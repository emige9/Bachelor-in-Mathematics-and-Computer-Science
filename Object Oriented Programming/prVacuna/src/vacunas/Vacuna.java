// Apellidos y Nombre: Emilio G�mez Esteban
// Titulaci�n: Doble Grado de Matem�ticas + Ing Inform�tica
// Grupo: 1�A

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
			throw new RuntimeException("Cantidad de dosis introducida no v�lida");
		} else {
			this.dosis=dosisNuevas;
		}
	}
	
	public void setViales(int vialesNuevos) {
		
		if(vialesNuevos<=0) {
			throw new RuntimeException("Cantidad de viales introducida no v�lida");
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
