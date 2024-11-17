public class ASIGBOOL extends EXP{
	
	public ASIGBOOL(String i, AST d){
		super(null,d);
		this.codigo = i;
	}
	
	public void ctd(){
		if(izq!=null){
			izq.ctd();
		}
		if(der!=null){
			der.ctd();
		}
		
		String l1 = Generador.nuevaLabel();
		Generador.label(((COND)der).getEtiq().v());
		Generador.asignacion(this.codigo, "1");
		Generador.gotoLabel(l1);
		Generador.label(((COND)der).getEtiq().f());
		Generador.asignacion(this.codigo,"0");
		Generador.label(l1);
	}
}
