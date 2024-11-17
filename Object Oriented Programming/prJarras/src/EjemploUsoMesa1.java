import prJarra.Mesa;

public class EjemploUsoMesa1 {

	public static void main(String [] args) {
		
		Mesa m = new Mesa(7,5);
		
		// Jarra jarra1 = new Jarra(7);
		// Jarra jarra2 = new Jarra(5);
		// Mesa m = new Mesa(jarra1,jarra2);
		
		m.llena(2);
		System.out.println(m.toString());
		
		m.llenaDesde(2);
		System.out.println(m.toString());
		
		m.llena(2);
		System.out.println(m.toString());
		
		m.llenaDesde(2);
		System.out.println(m.toString());
		
		m.vacia(1);
		System.out.println(m.toString());
		
		m.llenaDesde(2);
		System.out.println(m.toString());
		
		m.llena(2);
		System.out.println(m.toString());
		
		m.llenaDesde(2);
		System.out.println(m.toString());
	}
}
