public class IFELSE extends AST{
	public IFELSE(AST i, AST s){
		super(i,s);
	}
	
	public void ctd(){
		if(izq != null){
			izq.ctd();
		}
		
		String fin = ((IF)izq).getFinLabel();
		if(der != null){
			der.ctd();
		}
		
		
		Generador.label(fin);
	}
}
