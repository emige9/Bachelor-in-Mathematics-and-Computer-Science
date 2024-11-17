import java.io.IOException;
import java.io.FileReader;


public class JCom {

	public static int linea = 0;
	public static int bloque = 0;
	public static int documentacion = 0;
    
	public static void main(String arg[]) {
		
		    if (arg.length>0) {
			try{
		        Yylex lex = new Yylex(new FileReader(arg[0]));
		        lex.yylex();		        
		        
		        if(lex!=null){
		        System.out.println("// " + linea);
		        System.out.println("/* " + bloque);
		        System.out.println("/** " + documentacion);
		        }
		
		} catch (IOException e) {
		    System.err.println("Error: " + e.getMessage() + ".");            
		}
		} else {
		      System.err.println("Se necesita un archivo de texto como argumento");
		    }
		
	}
}
