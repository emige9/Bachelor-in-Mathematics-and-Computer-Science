import prUrnas.Urna;
import prUrnas.UrnaOpaca;

public class PruebaUrnaOpaca {
	
	public static void main (String[] args) {
		Urna urna=new Urna();
		
		urna.votar(true);
		urna.votar(true);
		urna.votar(true);
		urna.votar(false);
		urna.votar(false);
		
		System.out.println("URNA | Resultado intermedio de votacion: " + urna.resultado());
		
		urna.votar(false);
		urna.votar(false);
		
		System.out.println("URNA | Resultado final de votacion: " + urna.resultado());
		
		UrnaOpaca urnaOpaca=new UrnaOpaca();
		
		urnaOpaca.votar(true);
		urnaOpaca.votar(true);
		urnaOpaca.votar(true);
		urnaOpaca.votar(false);
		urnaOpaca.votar(false);
		
		System.out.println("URNA OPACA | Resultado intermedio de votacion: " + urnaOpaca.resultado());
		
		urnaOpaca.votar(false);
		urnaOpaca.votar(false);
		
		System.out.println("URNA OPACA | Resultado final de votacion: " + urnaOpaca.resultado());
	}
}
