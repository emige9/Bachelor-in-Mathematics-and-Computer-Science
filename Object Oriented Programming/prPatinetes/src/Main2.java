import java.util.SortedSet;
import java.util.TreeSet;

import prPatinetes.*;

public class Main2 {

	public static void main(String[] args) {
		try {
			Empleado e = new Empleado("11222333X", 
					new Posicion(-4.4204216, 36.7182771));

			EmpresaSeleccion empresa1 = 
					new EmpresaSeleccion("Aliquindoi", 
							"patinetes.txt",
							new SeleccionAutonomia(5.0));
			
			SortedSet<Integer> c1 = new TreeSet<>();
			c1.add(100);
			c1.add(105);
			c1.add(102);
			c1.add(110);
			c1.add(101);
			empresa1.asignaPatinetesEmpleado(e, c1);
								
			System.out.println(empresa1);
			
			EmpresaSeleccion empresa2 = 
					new EmpresaSeleccion("Aliquindoi", 
							"patinetes.txt",
							new SeleccionPosicion(
									e.getPosicion(),
									0.05));
			
			SortedSet<Integer> c2 = new TreeSet<>();
			c2.add(100);
			c2.add(102);
			c2.add(110);
			empresa2.asignaPatinetesEmpleado(e, c2);
			
			System.out.println(empresa2);
		
		} catch (PatinetesException e) {
			System.err.println(e.getMessage());
		} 

	}

}
