package cartelera;

public class CarteleraException extends RuntimeException {
	public CarteleraException() {
		super();
	}
	
	public CarteleraException(String mens) {
		super(mens);
	}
}
