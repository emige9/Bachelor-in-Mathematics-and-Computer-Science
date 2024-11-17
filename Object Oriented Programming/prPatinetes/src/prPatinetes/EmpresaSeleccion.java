package prPatinetes;
import java.util.*;

public class EmpresaSeleccion extends Empresa{
	private Seleccion sel;
	
	public EmpresaSeleccion(String nombre, String nomFich, Seleccion s) throws PatinetesException{
		super(nombre,nomFich);
		sel=s;
	}
	
	public void asignaPatinetesEmpleado(Empleado e, SortedSet<Integer> codigos) {
		Patinete p;
		Iterator<Integer> it = codigos.iterator();
		while(it.hasNext()) {
			p = buscaPatinete(it.next());
			if(p==null || !sel.seleccionar(p)) {
				it.remove();
			}
		}
		super.asignaPatinetesEmpleado(e, codigos);
	}
}
