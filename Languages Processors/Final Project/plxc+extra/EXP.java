public class EXP extends AST{
	protected String codigo;
	protected TIPO tipo;
	
	public EXP(AST i, AST d){
		super(i,d);
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public TIPO getTipo(){
		return this.tipo;
	}
	
	public void setTipo(TIPO t){
		this.tipo = t;
	}
}
