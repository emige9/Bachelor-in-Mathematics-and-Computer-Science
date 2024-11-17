package vacunas;

public class VacunaDosisExtra extends Vacuna{
	
	public VacunaDosisExtra(String codigo, int viales, int dosis) {
		super(codigo,viales,dosis);
	}
	
	public int dosisTotales() {
		return super.dosisTotales() + this.getDosis();
	}
	
	public String toString() {
		return super.toString() + " + " + this.getViales() + " extra";
	}
}
