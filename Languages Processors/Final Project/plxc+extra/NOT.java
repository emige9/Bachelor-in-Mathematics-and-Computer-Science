public class NOT extends COND{

	public NOT(int condicion, AST i){
		super(condicion, i, null);
	}
	
	public void ctd(){
		izq.ctd();
		String vI = ((COND)izq).getEtiq().v();
		String fI = ((COND)izq).getEtiq().f();
		
		ETIQ e = new ETIQ(fI, vI);
		this.setEtiq(e);
	}
}
