package prUrnas;

public class SalaVotacion {
	Urna urna1, urna2;
	
	public SalaVotacion() {
		urna1 = new Urna();
		urna2 = new Urna();
	}
	
	public SalaVotacion(Urna urna1, Urna urna2) {
		this.urna1 = urna1;
		this.urna2 = urna2;
	}
	
	public void votar (int i,boolean voto) {
		if(i==1) {
			urna1.votar(voto);
		}else if(i==2) {
			urna2.votar(voto);
		} else {
			throw new RuntimeException ("No existe indice de urna: " + i);
		}
	}
	
	public boolean resultado(int i) {
		boolean res;
		
		if(i==1) {
			res = urna1.resultado();
		} else if (i==2) {
			res = urna2.resultado();
		} else {
			throw new RuntimeException("No existe indice de urna: " + i);
		}
		
		return res;
	}
}
