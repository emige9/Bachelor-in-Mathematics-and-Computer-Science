public class AST{
	protected AST izq;
	protected AST der;
	
	public AST(AST i, AST d){
		izq = i;
		der = d;
	}
	
	public AST getIzq(){
		return izq;
	}
	
	public AST getDer(){
		return der;
	}
	
	public void ctd(){
		if(izq != null){
			izq.ctd();
		}
		if(der != null){
			der.ctd();
		}
	}
}
