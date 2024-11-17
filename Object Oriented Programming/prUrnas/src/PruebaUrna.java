import prUrnas.Urna;

public class PruebaUrna {
	
	public static void main (String[] args) {
		Urna u=new Urna();
		
		u.votar(true);
		u.votar(true);
		u.votar(true);
		u.votar(false);
		u.votar(false);
		
		System.out.println("Resultado intermedio de la votacion:" + u.resultado());
		
		u.votar(false);
		u.votar(false);
		
		System.out.println("Resultado final de la votacion: " + u.resultado());
	}
}
