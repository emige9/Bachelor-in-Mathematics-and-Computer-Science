package paqVacunas;
import java.util.Arrays;

public class Comprador{
	private static final int TAM_INICIAL=10;
	private int numVacunas;
	private String nombre;
	Vacuna [] listaVacunas;
	
	public Comprador (String nombre, int numVacunas) {
		
		if(numVacunas<=0) {
			throw new RuntimeException("El numero de vacunas introducido es erróneo");
		} else {
			this.nombre=nombre;
			this.numVacunas=0;
			listaVacunas = new Vacuna[numVacunas];
		}	
	}
	
	public Comprador (String nombre) {
		this.nombre=nombre;
		this.numVacunas=0;
		listaVacunas = new Vacuna[TAM_INICIAL];
	}
	
	public void comprar (String codigo, double precio, int cantidad) {
		
		if(cantidad<=0) {
			throw new RuntimeException("La cantidad introducida es errónea");
		} else {
			int indice=buscaVacuna(codigo);
			
			if(indice!=-1) {
				listaVacunas[indice].setCantidad(listaVacunas[indice].getCantidad()+cantidad);
			} else {
				if(numVacunas==listaVacunas.length) {
					listaVacunas=Arrays.copyOf(listaVacunas,2*numVacunas);
				}
				
				listaVacunas[numVacunas]=new Vacuna(codigo,precio,cantidad);
				numVacunas++;
			}
			
		}	
	}
	
	public int getNumVacunas() {
		return this.numVacunas;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public double precioTotal() {
		double res=0;
		
		for(int i=0; i<numVacunas;i++) {
			res+=listaVacunas[i].precioTotal();
		}
		
		return res;
	}
	
	public void eliminar (String codigo) {
		int indice=buscaVacuna(codigo);
		
		if(indice!=-1) {
			mover(indice);
			listaVacunas[numVacunas-1]=null;
			numVacunas--;
		}
	}
	
	public String toString() {
		String res="";
		res+= getNombre() + " = "  + "[";
		for(int i=0; i<numVacunas-1; i++) {
			res+=listaVacunas[i].toString() + ", ";
		}
		
		if(numVacunas>0) {
			res+=listaVacunas[numVacunas-1];
		}
		res+="]\n";
		return res;
	}
	
	private void mover (int indice) {
		for (int i=indice; i<numVacunas; i++) {
			listaVacunas[i]=listaVacunas[i+1];
		}
	}
	
	private int buscaVacuna(String codigo) {
		int i=0;
		int pos=-1;
		
		while (i<numVacunas && pos==-1) {
			if(listaVacunas[i].getCodigo().equalsIgnoreCase(codigo)) {
				pos=i;
			}
			i++;
		}
		return pos;
	}
}
