public class CASTING extends EXP{
    

    public CASTING(AST d, TIPO t){
        super(null, d);
        this.tipo = t;
        this.codigo = Generador.nuevaTemporal();
    }

    public void ctd(){
        if(der != null){
        	der.ctd();
        }
        
        TIPO t = ((EXP)der).getTipo();
		
		if(this.tipo.esFloat() && t.esInt()){
			Generador.asignacion(this.codigo, "(float) " + ((EXP)der).getCodigo());
		} else if(this.tipo.esInt() && t.esFloat()){
			Generador.asignacion(this.codigo, "(int) " + ((EXP)der).getCodigo());
		} else if(this.tipo.esInt() && t.esChar()){
			this.codigo=((EXP)der).getCodigo();
		} else if(this.tipo.esChar() && t.esInt()){
			this.codigo=((EXP)der).getCodigo();
		} else if ((this.tipo.esChar() && t.esChar()) || (this.tipo.esInt() && t.esInt()) || (this.tipo.esFloat() && t.esFloat())){
			this.codigo=((EXP)der).getCodigo();
		} else {
			Generador.error();
		}
		
        
    }

}
