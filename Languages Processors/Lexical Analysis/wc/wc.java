import java.io.FileReader;
import java.io.IOException;

public class wc {
    protected static int sumaEOLN = 0;
    protected static int sumaCAR = 0;
    protected static int sumaPAL = 0;
    
    
    public static void main(String arg[]) {
    
        if (arg.length>0) {
            try {
                Yylex lex = new Yylex(new FileReader(arg[0]));
                Yytoken yytoken = null;
		while (  (yytoken = lex.yylex()) != null  ) {
                    if (yytoken.getToken() == Yytoken.PALABRA)  {
                       sumaPAL++;
                       sumaCAR = sumaCAR + yytoken.getCaracteres();
                    } else if (yytoken.getToken() == Yytoken.EOLN) {
                       sumaCAR++;
                       sumaEOLN++;
                    } else if (yytoken.getToken() == Yytoken.CARACTER) {
                       sumaCAR++;
                    } 
                }
                System.out.println(sumaEOLN + "  " + sumaPAL + "  " + sumaCAR + "  " + arg[0]);
                
	    } catch (IOException e) {
	    }
        }
    }

}
