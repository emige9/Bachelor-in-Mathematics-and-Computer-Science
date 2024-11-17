import java.util.ArrayList;

public class PRINTBOOL extends AST{
	
	public PRINTBOOL(AST i, AST d){
		super(i,d);
	}
	
	public void ctd(){
		der.ctd();
		String l1 = Generador.nuevaLabel();
		Generador.label(((COND)der).getEtiq().v());
		Generador.writec("116");
		Generador.writec("114");
		Generador.writec("117");
		Generador.writec("101");
		Generador.writec("10");
		Generador.gotoLabel(l1);
		Generador.label(((COND)der).getEtiq().f());
		Generador.writec("102");
		Generador.writec("97");
		Generador.writec("108");
		Generador.writec("115");
		Generador.writec("101");
		Generador.writec("10");
		Generador.label(l1);
	}
}
