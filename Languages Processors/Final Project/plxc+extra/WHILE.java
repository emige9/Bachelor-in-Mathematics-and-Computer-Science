public class WHILE extends EXP {

	public WHILE(AST condicion, AST sentencia){
		super(condicion, sentencia);
	}
	
	public void ctd(){
		String inicio = Generador.nuevaLabel();
		Generador.label(inicio);
		
		if(izq != null){
			izq.ctd();
		}
		Generador.label(((COND)izq).getEtiq().v());
		
		if(der != null){
			der.ctd();
		}
		Generador.gotoLabel(inicio);
		Generador.label(((COND)izq).getEtiq().f());
	}
}
