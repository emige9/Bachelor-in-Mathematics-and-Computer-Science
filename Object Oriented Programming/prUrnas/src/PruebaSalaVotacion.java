import prUrnas.SalaVotacion;

public class PruebaSalaVotacion {
	
	public static void main (String[] args) {
		
		SalaVotacion sala = new SalaVotacion();
		
		sala.votar(1, false);
		
		System.out.println("URNA 1 | Resultado intermedio de la votacion: " + sala.resultado(1));
		
		sala.votar(1, true);
		sala.votar(1, true);
		
		sala.votar(2, true);
		
		System.out.println("URNA 2 | Resultado intermedio de la votacion: " + sala.resultado(2));
		
		sala.votar(2, false);
		sala.votar(2, false);
		
		System.out.println("URNA 1 | Resultado final de la votacion: " + sala.resultado(1));
		System.out.println("URNA 2 | Resultado final de la votacion: " + sala.resultado(2));
	}
}
