package prPatinetes;

public class SeleccionAutonomia implements Seleccion{
	private double umbral;
	
	public SeleccionAutonomia(double umbralAut) {
		umbral=umbralAut;
	}
	
	public boolean seleccionar(Patinete p) {
		return p.getAutonomia()<umbral;
	}
}
