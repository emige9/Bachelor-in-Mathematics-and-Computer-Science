public class RESTA extends EXP{
	
	public RESTA(AST e1, AST e2){
		super(e1,e2);
		this.codigo = Generador.nuevaTemporal();
	}
	
	public void ctd(){
		if(izq!=null){
			izq.ctd();
		}
		if(der!=null){
			der.ctd();
			TIPO t1 = ((EXP)izq).getTipo();
			TIPO t2 = ((EXP)der).getTipo();
		
			if(t1.esFloat() && t2.esFloat()){
				this.setTipo(new TIPO(TIPO.FLOAT));
				Generador.operacionAritm(this.codigo, ((EXP)izq).getCodigo()+" -r "+((EXP)der).getCodigo());	
			} else if(t1.esFloat() && t2.esInt()){
				this.setTipo(new TIPO(TIPO.FLOAT));
				String t = Generador.nuevaTemporal();
				Generador.asignacion(this.codigo,"(float) " + ((EXP)der).getCodigo());
				Generador.operacionAritm(t, ((EXP)izq).getCodigo()+" -r "+this.codigo);	
				this.codigo=t;
			} else if(t1.esInt() && t2.esFloat()){
				this.setTipo(new TIPO(TIPO.FLOAT));
				String t = Generador.nuevaTemporal();
				Generador.asignacion(this.codigo,"(float) " + ((EXP)izq).getCodigo());
				Generador.operacionAritm(t, this.codigo+" -r "+((EXP)der).getCodigo());
				this.codigo=t;
			} else {
				this.setTipo(new TIPO(TIPO.INT));
				Generador.operacionAritm(this.codigo, ((EXP)izq).getCodigo()+" - "+((EXP)der).getCodigo());
			}
		} else {
			TIPO t1 = ((EXP)izq).getTipo();
			if(t1.esFloat()){
				this.setTipo(new TIPO(TIPO.FLOAT));
				Generador.operacionAritm(this.codigo, " -r " + ((EXP)izq).getCodigo());
			} else {
				this.setTipo(new TIPO(TIPO.INT));
				Generador.operacionAritm(this.codigo, " - " + ((EXP)izq).getCodigo());
			}
		}
	}
}
