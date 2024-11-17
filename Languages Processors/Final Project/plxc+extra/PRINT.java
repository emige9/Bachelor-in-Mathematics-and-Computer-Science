import java.util.ArrayList;

public class PRINT extends AST {

	public ArrayList<AST> arr;
	
	public PRINT(AST i, AST d){
		super(i,d);
	}
	
	public PRINT(AST i, AST d, ArrayList<AST> a){
		super(i,d);
		arr = a;
	}
	
	public void ctd(){
		if(izq!=null){
			izq.ctd();
			TIPO t = ((EXP)izq).getTipo();
		
			if(t.esChar()){
				Generador.printc(((EXP)izq).getCodigo());
			}else if(t.esArray() && t.getSubtipo().esChar()) {
				for(int i = 0; i < t.getLen(); i++){
					String s = Generador.nuevaTemporal();
					Generador.asignacion(s, ((EXP)izq).getCodigo()+"["+i+"]");
					Generador.printc(s);
				}
			}else if(t.esArray()) {
				for(int i = 0; i < t.getLen(); i++){
					String s = Generador.nuevaTemporal();
					Generador.asignacion(s, ((EXP)izq).getCodigo()+"["+i+"]");
					Generador.print(s);
				}
			} else if(t.esString()) {
				String t1 = Generador.nuevaTemporal();
				String t2 = Generador.nuevaTemporal();
				String l1 = Generador.nuevaLabel();
				String l2 = Generador.nuevaLabel();
				String l3 = Generador.nuevaLabel();
				Generador.asignacion(t1,"0");
				Generador.label(l1);
				Generador.condicion(1,t1,"$"+((EXP)izq).getCodigo()+"_length",new ETIQ(l2,l3));
				Generador.label(l2);
				Generador.asignacion(t2,((EXP)izq).getCodigo()+"["+t1+"]");
				Generador.writec(t2);
				Generador.asignacion(t1,t1+" + 1");
				Generador.gotoLabel(l1);
				Generador.label(l3);
				Generador.writec("10");
				
				
			}else {
				Generador.print(((EXP)izq).getCodigo());
			}
		} else {
			for(int i = 0; i < arr.size(); i++){
				Generador.print(((EXP)arr.get(i)).getCodigo());
			}
		}

	}
	
}
