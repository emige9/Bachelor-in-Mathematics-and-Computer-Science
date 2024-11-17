public class IF extends AST{
	public String fin;
	
	public IF(AST condicion, AST sentencia){
		super(condicion, sentencia);
	}
	
	public String getFinLabel(){
		return fin;
	}
	
	public void ctd(){
		if(izq != null){
			izq.ctd();
		}
		Generador.label(((COND)izq).getEtiq().v());
		
		if(der != null){
			der.ctd();
		}
		fin = Generador.nuevaLabel();
		Generador.gotoLabel(fin);
		Generador.label(((COND)izq).getEtiq().f());
	}
}
