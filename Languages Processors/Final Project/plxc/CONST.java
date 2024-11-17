public class CONST extends EXP{
	public CONST(String valor, TIPO t){
		super(null,null);
		codigo=valor;
		tipo = t;
	}
	
	public CONST(String valor, TIPO t, AST d){
		super(null,d);
		codigo=valor;
		tipo = t;
	}
	
	public void ctd(){
		if(der!=null){
			der.ctd();
			String t = Generador.nuevaTemporal();
			Generador.asignacion(t,this.codigo+"["+((EXP)der).getCodigo()+"]");
			this.codigo = t;
		}
		
		
		
	}
}
