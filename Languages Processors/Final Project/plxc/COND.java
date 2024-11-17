public class COND extends EXP{

	public int condicion;
	public ETIQ trueOrfalse;
	
	public COND(int cond, AST i, AST d){
		super(i,d);
		condicion = cond;
	}
	
	public int getCondicion(){
		return condicion;
	}
	
	public ETIQ getEtiq(){
		return trueOrfalse;
	}
	
	public void setEtiq(ETIQ e){
		trueOrfalse = e;
	}
	
	public void ctd(){
		if(izq != null){
			izq.ctd();
		}
		
		if(der != null){
			der.ctd();
		}
		
		trueOrfalse = new ETIQ(Generador.nuevaLabel(), Generador.nuevaLabel());
		Generador.condicion(this.condicion, ((EXP)izq).getCodigo(), ((EXP)der).getCodigo(), trueOrfalse);
	}
}
