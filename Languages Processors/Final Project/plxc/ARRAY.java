import java.util.ArrayList;

public class ARRAY extends EXP{

	public ArrayList<AST> arr;
	
	public ARRAY(ArrayList<AST> a, String s){
		super(null,null);
		this.codigo = s;
		arr = a;
		this.tipo = new TIPO(TIPO.ARRAY);
	}
	
	
	
	public void ctd(){
		TIPO t1 = TablaSimbolos.buscar(this.codigo).getSubtipo();
		if(TablaSimbolos.buscar(this.codigo).getLen()<arr.size()) {Generador.error();}
		for(int i = 0; i < arr.size(); i++){
			EXP e = (EXP)arr.get(i);
			e.ctd();
			TIPO t2 = e.getTipo(); 
			if((t1.esInt() && t2.esFloat()) || (t1.esFloat() && t2.esInt())) {Generador.error();}
			Generador.asignacion(this.codigo+"["+i+"]", e.getCodigo());
		}
		
		
	}
}
