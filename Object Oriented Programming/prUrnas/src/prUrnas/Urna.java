package prUrnas;

public class Urna {
	protected int votosAfirmativos;
	protected int votosNegativos;
	
	public Urna() {
		votosAfirmativos=0;
		votosNegativos=0;
	}
	
	public void votar (boolean voto) {
		if(voto) {
			votosAfirmativos++;
		} else {
			votosNegativos++;
		}
	}
	
	public boolean resultado() {
		return votosAfirmativos>=votosNegativos;
	}
}
