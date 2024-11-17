
public class prueba {
	
	public static void main(String[] args) {
		SCMC scmc = new SCMC("abc", "aab", "acbaa");
		scmc.solucionaPD();
		System.out.println("La longitud de la SCMC es " + scmc.longitudDeSolucionPD());
		System.out.println("Una SCMC es " + scmc.unaSolucionPD());
	}
}
