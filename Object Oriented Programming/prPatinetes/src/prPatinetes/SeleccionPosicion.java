package prPatinetes;

public class SeleccionPosicion implements Seleccion{
	private Posicion posicionRef;
	private double distanciaMax;
	
	public SeleccionPosicion(Posicion pos, double distancia) {
		posicionRef=pos;
		distanciaMax=distancia;
	}
	
	public boolean seleccionar(Patinete p) {
		return posicionRef.distancia(p.getPosicion())<distanciaMax;
	}
}
