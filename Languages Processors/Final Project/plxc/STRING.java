public class STRING extends EXP{

	public STRING(String valor){
		super(null,null);
		codigo=valor;
		tipo = new TIPO(TIPO.STRING);
	}

	
	public void ctd(){
		String t = Generador.nuevaTemporal();
		char[] ch = this.codigo.toCharArray();
		for(int i = 0; i < ch.length; i++){
			Generador.asignacion(t+"["+i+"]", String.valueOf((int)ch[i]));
		}
		Generador.asignacion("$"+t+"_length", String.valueOf(ch.length));
		this.codigo=t;
		
		
	}
}
