import java.io.IOException;
import java.io.FileReader;


public class Prep {

    
	public static void main(String arg[]) {
		
		    if (arg.length>0) {
			try{
		        Yylex lex = new Yylex(new FileReader(arg[0]));
		        lex.yylex();		        
		        
		
		} catch (IOException e) {
            
		}
		}
	}
}
