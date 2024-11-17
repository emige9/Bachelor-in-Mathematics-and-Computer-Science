public class DOWHILE extends EXP {

	public DOWHILE(AST sentencia, AST condicion){
		super(sentencia, condicion);
	}
	
	public void ctd(){
		String inicio = Generador.nuevaLabel();
		Generador.label(inicio);
		
		if(izq != null){
			izq.ctd();
		}
		
		if(der != null){
			der.ctd();
		}
		Generador.label(((COND)der).getEtiq().v());
		Generador.gotoLabel(inicio);
		Generador.label(((COND)der).getEtiq().f());
	}
}
