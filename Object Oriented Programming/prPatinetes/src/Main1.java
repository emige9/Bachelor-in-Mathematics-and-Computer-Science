import java.util.SortedSet;
import java.util.TreeSet;

import prPatinetes.*;

public class Main1 {

	public static void main(String[] args) {
		try {
			Empresa empresa = new Empresa("Aliquindoi", "patinetes.txt");
			
			System.out.println(empresa);
	
			Empleado e1 = new Empleado("11222333X", 
					new Posicion(-4.4204216, 36.7182771));
			Empleado e2 = new Empleado("99888777H", 
					new Posicion(-4.4150382, 36.7306184));

			SortedSet<Integer> c1 = new TreeSet<>();
			c1.add(100);
			c1.add(105);
			c1.add(102);
			c1.add(8);
			empresa.asignaPatinetesEmpleado(e1, c1);
			
			SortedSet<Integer> c2 = new TreeSet<>();
			c2.add(102);
			c2.add(110);
			c2.add(7);
			empresa.asignaPatinetesEmpleado(e2, c2);
			
			SortedSet<Integer> c3 = new TreeSet<>();
			c3.add(105);
			c3.add(110);
			c3.add(101);
			empresa.asignaPatinetesEmpleado(e1, c3);
			
			System.out.println(empresa);
			
			empresa.modificaPatinete(100, 
					new Posicion(-4.5444444,36.8011111), 10);
	
			System.out.println(empresa);
			
		} catch (PatinetesException e) {
			System.err.println(e.getMessage());
		} 
	}

}
