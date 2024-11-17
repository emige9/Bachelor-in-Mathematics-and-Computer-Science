public class FORALL extends AST{
	
	public String p;
	public String q;
	
	public FORALL(String s1, String s2, AST condicion, AST sentencia){
		super(condicion, sentencia);
		p = s1;
		q = s2;
	}
	
	public FORALL(String s1, AST condicion, AST sentencia){
		super(condicion, sentencia);
		p = s1;
		q = null;
	}
	
	public void ctd(){
		if(q == null){
			Generador.asignacion(p, "0");
			izq.ctd();
			String f = ((COND)izq).getEtiq().f();
			Generador.label(((COND)izq).getEtiq().v());
			Generador.asignacion(p, "1");
			izq.ctd();
			Generador.label(((COND)izq).getEtiq().v());
			der.ctd();
			Generador.label(f);
			Generador.label(((COND)izq).getEtiq().f());
		} else {
			Generador.asignacion(p, "0");
			Generador.asignacion(q, "0");
			izq.ctd();
			String f1 = ((COND)izq).getEtiq().f();
			Generador.label(((COND)izq).getEtiq().v());
			Generador.asignacion(p, "1");
			izq.ctd();
			String f2 = ((COND)izq).getEtiq().f();
			Generador.label(((COND)izq).getEtiq().v());
			Generador.asignacion(q, "1");
			izq.ctd();
			String f3 = ((COND)izq).getEtiq().f();
			Generador.label(((COND)izq).getEtiq().v());
			Generador.asignacion(p, "0");
			Generador.asignacion(q, "1");
			izq.ctd();
			String f4 = ((COND)izq).getEtiq().f();
			Generador.label(((COND)izq).getEtiq().v());
			der.ctd();
			Generador.label(f1);
			Generador.label(f2);
			Generador.label(f3);
			Generador.label(f4);
		}
	}
}
