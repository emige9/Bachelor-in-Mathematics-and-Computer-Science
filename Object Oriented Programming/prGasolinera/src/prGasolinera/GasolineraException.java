package prGasolinera;

public class GasolineraException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public GasolineraException() {
		super();
	}
	
	public GasolineraException(String msg) {
		super(msg);
	}
}
