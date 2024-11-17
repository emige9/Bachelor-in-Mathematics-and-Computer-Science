import prPatinetes.*;
import java.util.*;

public class PruebaPatinete {

	public static void main(String[] args) {
		try {
			Patinete p1 = new Patinete("Campero", 100, new Posicion(-4.4204216,36.7182771),3);
			Patinete p2 = new Patinete("campero", 100, new Posicion(-4.4495993,36.7015323),2);
			Patinete p3 = new Patinete("Biznaga", 101, new Posicion(-4.4150382,36.7306184),5);
			
			System.out.println("Patinete 1: " + p1);
			System.out.println("Patinete 2: " + p2);
			System.out.println("Patinete 3: " + p3);
			
			if(p1.equals(p2)) {
				System.out.println("Los patinetes 1 y 2 son iguales");
			} else {
				System.out.println("Los patinetes 1 y 2 no son iguales");
			}
			
			SortedSet<Patinete> conjunto = new TreeSet<Patinete>();
			conjunto.add(p1);
			conjunto.add(p2);
			conjunto.add(p3);
			System.out.println("Conjunto: " + conjunto);
			p3.setAutonomia(-7);
		}catch (PatinetesException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
}
