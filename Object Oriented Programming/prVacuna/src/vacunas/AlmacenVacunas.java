package vacunas;
import java.util.Arrays;

public class AlmacenVacunas {
	private static final int CAP_INICIAL=3;
	private String nombre;
	private int numVacunas;
	Vacuna[] almacenVacunas;
	
	public AlmacenVacunas(String nombre, int capacidad) {
		
		if(capacidad<=0) {
			throw new RuntimeException("La capacidad del almacén introducida es errónea");
		} else {
			this.nombre=nombre;
			numVacunas=0;
			almacenVacunas=new Vacuna[capacidad];
		}
	}
	
	public AlmacenVacunas(String nombre) {
		this.nombre=nombre;
		numVacunas=0;
		almacenVacunas=new Vacuna[CAP_INICIAL];
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void almacenar(String codigo, int viales, int dosis) {
		int indice=buscar(codigo);
		
		if(indice>0) {
			almacenVacunas[indice].setViales(almacenVacunas[indice].getViales()+viales);
		} else {
			if(numVacunas==almacenVacunas.length) {
				almacenVacunas=Arrays.copyOf(almacenVacunas,almacenVacunas.length*2);
			}
			
			almacenVacunas[numVacunas]=new Vacuna(codigo,viales,dosis);
			numVacunas++;
		}
	}
	
	public int totalDosis() {
		int suma=0;
		
		for(int i=0; i<numVacunas; i++) {
			suma+=almacenVacunas[i].dosisTotales();
		}
		
		return suma;
	}
	
	public void inocularTodas(String codigo) {
		int indice=buscar(codigo);
		
		if(indice>0) {
			mover(indice);
			almacenVacunas[numVacunas-1]=null;
			numVacunas--;
		}
	}
	
	public String toString() {
		String res=this.getNombre() + " [";
		
		for(int i=0; i<numVacunas-1; i++) {
			res+=almacenVacunas[i].toString() + ", ";
		}
		
		if(numVacunas>0) {
			res+=almacenVacunas[numVacunas-1].toString() + "]";
		}
		
		return res;
	}
	
	private void mover(int indice) {
		for(int i=indice; i<numVacunas; i++) {
			almacenVacunas[i]=almacenVacunas[i+1];
		}
	}
	
	private int buscar(String codigo) {
		int i=0;
		int pos=-1;
		
		while(i<numVacunas && pos==-1) {
			if(almacenVacunas[i].getCodigo().equalsIgnoreCase(codigo)) {
				pos=i;
			}
			i++;
		}
		
		return pos;
	}
	
}
