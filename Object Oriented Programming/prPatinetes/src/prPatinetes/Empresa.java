package prPatinetes;
import java.util.*;
import java.io.*;

public class Empresa {
	private String nombreEmpresa;
	private SortedSet<Patinete> patinetes;
	private List<String> errores;
	private Map<Empleado,SortedSet<Integer>> empleados;
	
	public Empresa(String nombre, String nomFich) throws PatinetesException{
		nombreEmpresa=nombre;
		empleados=new HashMap<>();
		patinetes=new TreeSet<>();
		errores=new ArrayList<>();
		
		try(Scanner sc = new Scanner(new File(nomFich))){
			while(sc.hasNextLine()) {
				procesarLinea(sc.nextLine());
			}
		}catch(FileNotFoundException e) {
			throw new PatinetesException("ERROR: El fichero " + nomFich + " no puede abrirse");
		}
	}
	
	private void procesarLinea(String linea) {
		try {
			String[] datos = linea.split(";");
			if(datos[0].equalsIgnoreCase(nombreEmpresa)) {
				patinetes.add(new Patinete(nombreEmpresa, Integer.parseInt(datos[1]),new Posicion(Double.parseDouble(datos[2]),Double.parseDouble(datos[3])),Double.parseDouble(datos[4])));
			}
		}catch (NumberFormatException e) {
			errores.add("ERROR: Dato de tipo incorrecto EN LINEA: " + linea);
		}catch(ArrayIndexOutOfBoundsException e) {
			errores.add("ERROR: Faltan datos EN LINEA " + linea);
		}catch(PatinetesException e) {
			errores.add("ERROR: " + e.getMessage() + " EN LINEA: " + linea);
		}
	}
	
	public void asignaPatinetesEmpleado(Empleado e, SortedSet<Integer> codigos) {
		SortedSet<Integer> codigosValidos = new TreeSet<>();
		for(Integer codigo : codigos) {
			if(!asignado(codigo) && buscaPatinete(codigo)!=null) {
				codigosValidos.add(codigo);
			}
		}
		
		SortedSet<Integer> codigosAsociados = empleados.get(e);
		if(codigosAsociados==null) {
			empleados.put(e, codigosValidos);
		} else {
			codigosAsociados.addAll(codigosValidos);
		}
	}
	
	private boolean asignado(Integer codigo) {
		boolean encontrado=false;
		
		Iterator<Empleado> it = empleados.keySet().iterator();
		while(it.hasNext() && !encontrado) {
			encontrado = empleados.get(it.next()).contains(codigo);
		}
		return encontrado;
	}
	
	public Patinete buscaPatinete(int codigo) {
		Patinete res=null;
		
		Iterator<Patinete> it = patinetes.iterator();
		while(res==null && it.hasNext()) {
			Patinete pat = it.next();
			if(codigo == pat.getCodigo()) {
				res=pat;
			}
		}
		return res;
	}
	
	public String toString() {
		StringBuilder sB = new StringBuilder(nombreEmpresa);
		sB.append("\nPatinetes: ").append(patinetes);
		sB.append("\nErrores: ").append(errores);
		sB.append("\nEmpleados: ").append(empleados);
		sB.append("\n");
		
		return sB.toString();
	}
	
	public void modificaPatinete(int codigo, Posicion posicion, double autonomia)throws PatinetesException {
		Patinete pat = buscaPatinete(codigo);
		
		if(pat==null) {
			throw new PatinetesException("Patinete no encontrado al intentar modificar su posicion y autonomia");
		}
		pat.setPosicion(posicion);
		pat.setAutonomia(autonomia);
	}
}
