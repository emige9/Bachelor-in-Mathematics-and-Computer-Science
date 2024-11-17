package prJarra;

public class Mesa {
	private Jarra jarra1;
	private Jarra jarra2;
	
	public Mesa (Jarra j1, Jarra j2) {
		if(j1==j2) {
			throw new RuntimeException("No se puede crear una mesa con dos jarras iguales");
		} else {
			jarra1=j1;
			jarra2=j2;
		}
	}
	
	public Mesa(int cap1, int cap2) {
		jarra1 = new Jarra(cap1);
		jarra2 = new Jarra(cap2);
	}
	
	public int capacidad(int i) {
		
		int dato;
		
		if(i==1) {
			dato=jarra1.capacidad();
		} else if (i==2) {
			dato=jarra2.capacidad();
		} else {
			throw new RuntimeException("Índice de Jarra erróneo: " + i);
		}
		
		return dato;
	}
	
	public int contenido(int i) {
		
		int dato;
		
		if(i==1) {
			dato=jarra1.contenido();
		} else if (i==2) {
			dato=jarra2.contenido();
		} else {
			throw new RuntimeException("Índice de Jarra erróneo: " + i);
		}
		
		return dato;
	}
	
	public void llena(int i) {
		
		if(i==1) {
			jarra1.llena();
		} else if (i==2){
			jarra2.llena();
		} else {
			throw new RuntimeException("Índice de Jarra erróneo: " + i);
		}
	}
	
	public void vacia(int i) {
		
		if(i==1) {
			jarra1.vacia();
		} else if (i==2) {
			jarra2.vacia();
		} else {
			throw new RuntimeException("Índice de Jarra erróneo: " + i);
		}
	}
	
	public void llenaDesde(int i) {
		
		if(i==1) {
			jarra2.llenaDesde(jarra1);
		} else if(i==2) {
			jarra1.llenaDesde(jarra2);
		} else {
			throw new RuntimeException("Índice de Jarra erróneo: " + i);
		}
	}
	
	public String toString() {
		return "M(" +jarra1 + "," + jarra2 + ")";
	}
}
