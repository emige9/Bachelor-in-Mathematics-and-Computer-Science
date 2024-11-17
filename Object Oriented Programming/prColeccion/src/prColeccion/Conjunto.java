package prColeccion;

public class Conjunto extends Coleccion{
	public Conjunto() {
		super();
	}
	
	public Conjunto(int tam) {
		super(tam);
	}
	
	public Conjunto union(Conjunto c) {
		Conjunto r= new Conjunto(this.getNumElem()+c.getNumElem());
		
		for(int i=0; i<this.getNumElem();i++) {
			r.anyade_directo(this.getElem(i));
		}
		
		for(int j=0; j<c.getNumElem();j++) {
			r.anyade(c.getElem(j));
		}
		
		return r;
	}
	
	public Conjunto interseccion(Conjunto c) {
		Conjunto r = new Conjunto(this.getNumElem());
		
		for(int i=0; i<this.getNumElem();i++) {
			int valor=this.getElem(i);
			if(c.contiene(valor)) {
				r.anyade_directo(valor);
			}
		}
		
		return r;
	}
	
	private void anyade_directo(int elemen) {
		super.anyade(elemen);
	}
	
}
