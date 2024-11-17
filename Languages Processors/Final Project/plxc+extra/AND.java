public class AND extends COND{

	public AND(int condicion, AST i, AST d){
		super(condicion, i, d);
	}
	
	public void ctd(){
		izq.ctd();
		String vI = ((COND)izq).getEtiq().v();
		String fI = ((COND)izq).getEtiq().f();
		Generador.label(vI);
		
		der.ctd();
		String vD = ((COND)der).getEtiq().v();
		String fD = ((COND)der).getEtiq().f();
		Generador.label(fI);
		Generador.gotoLabel(fD);
		
		ETIQ e = new ETIQ(vD, fD);
		this.setEtiq(e);
	}
}
