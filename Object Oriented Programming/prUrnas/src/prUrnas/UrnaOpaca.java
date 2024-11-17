package prUrnas;

public class UrnaOpaca extends Urna{
	
	private static final boolean ESTADO_ABIERTO=true;
	private static final boolean ESTADO_CERRADO=false;
	private boolean estadoVotacion;
	
	public UrnaOpaca() {
		super();
		estadoVotacion=ESTADO_ABIERTO;
	}
	
	public boolean estaAbierta() {
		return estadoVotacion;
	}
	
	public void cerrarVotacion() {
		estadoVotacion=ESTADO_CERRADO;
	}
	
	public void votar (boolean voto) {
		if(estadoVotacion==ESTADO_ABIERTO) {
			super.votar(voto);
		}
	}
	
	public boolean resultado() {
		estadoVotacion=ESTADO_CERRADO;
		
		return super.resultado();
	}
}
