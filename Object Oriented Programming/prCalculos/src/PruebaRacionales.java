import prCalculos.NumeroRacional;

public class PruebaRacionales {
	private static boolean iguales(NumeroRacional r1, NumeroRacional r2) {
		return ((r1.getNumerador()==r2.getNumerador()) && (r1.getDenominador()==r2.getDenominador()));
	}
	
	public static void main(String[] args) {
		NumeroRacional r1=new NumeroRacional(14,10);
		System.out.println("R1: " + r1);
		NumeroRacional r2=new NumeroRacional(24,38);
		System.out.println("R2:" + r2);
		
		NumeroRacional r3=r1.suma(r2);
		System.out.println("R3 (r1+r2): " + r3);
		NumeroRacional r4=r3.resta(r2);
		System.out.println("R4 (r3-r2): " + r4);
		
		if(!iguales(r1,r4)) {
			System.out.println("Error en suma/resta");
		}
		
		NumeroRacional r5=r1.mult(r2);
		System.out.println("R5 (r1*r2): " + r5);
		NumeroRacional r6=r5.div(r2);
		System.out.println("R6 (r5/r2): " + r6);
		
		if(!iguales(r1,r6)) {
			System.out.println("Error en multiplicación/división");
		}
	}
}
