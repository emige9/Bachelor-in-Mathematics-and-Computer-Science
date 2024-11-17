public class Generador{
	public static int temporal = 0;
	public static int label = 0;
	
	public final static int MENOR = 1;
	public final static int MENORIGUAL = 2;
	public final static int MAYOR = 3;
	public final static int MAYORIGUAL = 4;
	public final static int IGUAL = 5;
	public final static int NOIGUAL = 6;
	public final static int AND = 7;
	public final static int OR = 8;
	public final static int NOT = 9;
	
	public static String nuevaTemporal(){
		String t = "t" + temporal;
		temporal++;
		return t;
	}
	
	public static String nuevaLabel(){
		String l = "L" + label;
		label++;
		return l;
	}
	
	public static void label(String l){
		System.out.println(l + ":");
	}
	
	public static void gotoLabel(String l){
		System.out.println("\tgoto " + l + ";");
	}
	
	public static void operacionAritm(String codigo, String expresion){
		System.out.println("\t" + codigo + " = " + expresion + ";");
	}
	
	public static void asignacion(String ident, String exp){
		System.out.println("\t" + ident + " = " + exp + ";");
	}
	
	public static void print(String exp){
		System.out.println("\tprint " + exp + ";");
	}
	
	public static void printc(String exp){
		System.out.println("\tprintc " + exp + ";");
	}
	
	public static void write(String exp){
		System.out.println("\twrite " + exp + ";");
	}
	
	public static void writec(String exp){
		System.out.println("\twritec " + exp + ";");
	}

    	public static void compRango(String e1, String e2, ETIQ res){
    		System.out.println("\tif ("+e2+" < 0) goto "+res.v()+";");
    		System.out.println("\tif ("+e1+" < "+e2+") goto "+res.v()+";");
    		System.out.println("\tif ("+e1+" == "+e2+") goto "+res.v()+";");
    		System.out.println("\tgoto "+res.f()+";");
    	}
	
	public static void condicion(int cond, String e1, String e2, ETIQ res){
		switch(cond){
			case MENOR:
				System.out.println("\tif (" + e1 + " < " + e2 + ") goto " + res.v() + ";");
				System.out.println("\tgoto " + res.f() + ";");
				break;
			case MAYOR:
				System.out.println("\tif (" + e2 + " < " + e1 + ") goto " + res.v() + ";");
				System.out.println("\tgoto " + res.f() + ";");
				break;
			case MAYORIGUAL:
				System.out.println("\tif (" + e1 + " < " + e2 + ") goto " + res.f() + ";");
				System.out.println("\tgoto " + res.v() + ";");
				break;
			case MENORIGUAL:
				System.out.println("\tif (" + e2 + " < " + e1 + ") goto " + res.f() + ";");
				System.out.println("\tgoto " + res.v() + ";");
				break;
			case IGUAL:
				System.out.println("\tif (" + e1 + " == " + e2 + ") goto " + res.v() + ";");
				System.out.println("\tgoto " + res.f() + ";");
				break;
			case NOIGUAL:
				System.out.println("\tif (" + e1 + " == " + e2 + ") goto " + res.f() + ";");
				System.out.println("\tgoto " + res.v() + ";");
				break;
		}
	}
	
	public static void printError(){
		System.out.println("\terror;");
		System.out.println("halt;");
	}
	
	public static void error(){
		System.out.println("\terror;");
		System.out.println("halt;");
		System.exit(1);
	}
	
	
	
	
	
}
