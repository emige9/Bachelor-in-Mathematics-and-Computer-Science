public class ASIG extends EXP{

	
	public ASIG(String s, AST d){
		super(null, d);
		this.codigo = s;
	}
	
	public ASIG(String s, AST i, AST d){
		super(i,d);
		this.codigo = s;
	}
	
	public void ctd(){
		if(izq != null){
			izq.ctd();
		}
		if(der != null){
			der.ctd();
		}
		
		TIPO t1 = TablaSimbolos.buscar(this.codigo);
		TIPO t2 = ((EXP)der).getTipo();
		
		if (t1.esFloat() && t2.esInt()){
			this.setTipo(new TIPO(TIPO.FLOAT));
			Generador.asignacion(this.codigo, "(float) " + ((EXP)der).getCodigo());
		} else if (t1.esInt() && t2.esInt()){
			this.setTipo(new TIPO(TIPO.INT));
			Generador.asignacion(this.codigo, ((EXP)der).getCodigo());
		} else if (t1.esFloat() && t2.esFloat()){
			this.setTipo(new TIPO(TIPO.FLOAT));
			Generador.asignacion(this.codigo, ((EXP)der).getCodigo());
		} else if (t1.esChar() && t2.esChar()){
			this.setTipo(new TIPO(TIPO.CHAR));
			Generador.asignacion(this.codigo, ((EXP)der).getCodigo());
		} else if (t1.esArray() && t1.getSubtipo().esInt() && t2.esInt()) {
			this.setTipo(new TIPO(TIPO.INT));
			ETIQ r = new ETIQ(Generador.nuevaLabel(), Generador.nuevaLabel());
			Generador.compRango(String.valueOf(t1.getLen()),((EXP)izq).getCodigo(),r);
			Generador.label(r.v());
			Generador.printError();
			Generador.label(r.f());
			Generador.asignacion(this.codigo+"["+((EXP)izq).getCodigo()+"]", ((EXP)der).getCodigo());
		} else if (t1.esArray() && t1.getSubtipo().esFloat() && t2.esFloat()) {
			this.setTipo(new TIPO(TIPO.FLOAT));
			ETIQ r = new ETIQ(Generador.nuevaLabel(), Generador.nuevaLabel());
			Generador.compRango(String.valueOf(t1.getLen()),((EXP)izq).getCodigo(),r);
			Generador.label(r.v());
			Generador.printError();
			Generador.label(r.f());
			Generador.asignacion(this.codigo+"["+((EXP)izq).getCodigo()+"]", ((EXP)der).getCodigo());
		} else if (t1.esArray() && t1.getSubtipo().esFloat() && t2.esInt()) {
			this.setTipo(new TIPO(TIPO.FLOAT));
			ETIQ r = new ETIQ(Generador.nuevaLabel(), Generador.nuevaLabel());
			Generador.compRango(String.valueOf(t1.getLen()),((EXP)izq).getCodigo(),r);
			Generador.label(r.v());
			Generador.printError();
			Generador.label(r.f());
			String t = Generador.nuevaTemporal();
			Generador.asignacion(t,"(float) " + ((EXP)der).getCodigo());
			Generador.asignacion(this.codigo+"["+((EXP)izq).getCodigo()+"]",t);
		} else if(t1.esArray() && t1.getSubtipo().esChar() && t2.esChar()) {
			this.setTipo(new TIPO(TIPO.CHAR));
			ETIQ r = new ETIQ(Generador.nuevaLabel(), Generador.nuevaLabel());
			Generador.compRango(String.valueOf(t1.getLen()),((EXP)izq).getCodigo(),r);
			Generador.label(r.v());
			Generador.printError();
			Generador.label(r.f());
			Generador.asignacion(this.codigo+"["+((EXP)izq).getCodigo()+"]",((EXP)der).getCodigo());
		} else if(t1.esArray() && t2.esArray() && t1.getSubtipo().equals(t2.getSubtipo()) && t1.getLen()>=t2.getLen()) {
			for(int i = 0; i < t2.getLen(); i++){
				String t = Generador.nuevaTemporal();
				Generador.asignacion(t,((EXP)der).getCodigo()+"["+i+"]");
				Generador.asignacion(this.codigo+"["+i+"]", t);
			}
		} else if(t1.esString() && t2.esString()) {
			String s1 = Generador.nuevaTemporal();
			String s2 = Generador.nuevaTemporal();
			String l1 = Generador.nuevaLabel();
			String l2 = Generador.nuevaLabel();
			String l3 = Generador.nuevaLabel();
			Generador.asignacion("$"+this.codigo+"_length", "0");
			Generador.asignacion(s1,"0");
			Generador.label(l1);
			Generador.condicion(1,s1,"$"+((EXP)der).getCodigo()+"_length",new ETIQ(l2,l3));
			Generador.label(l2);
			Generador.asignacion(s2,((EXP)der).getCodigo()+"["+s1+"]");
			Generador.asignacion(this.codigo+"["+"$"+this.codigo+"_length"+"]",s2);
			Generador.asignacion("$"+this.codigo+"_length", "$"+this.codigo+"_length + 1");
			Generador.asignacion(s1,s1+" + 1");
			Generador.gotoLabel(l1);
			Generador.label(l3);
			
		} else if(t1.esBool() && t2.esBool()){
			this.setTipo(new TIPO(TIPO.BOOL));
			Generador.asignacion(this.codigo, ((EXP)der).getCodigo());
		} else {
			Generador.error();
		}
	}
}
