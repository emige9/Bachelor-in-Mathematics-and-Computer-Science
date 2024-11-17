import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;

class Parser {

public final static int EOF = Yylex.EOF;
public final static int NUMERO = Yylex.NUMERO ;         
public final static int COMA = Yylex.COMA;
public final static int AC = Yylex.AC; // Abre corchete
public final static int CC = Yylex.CC; // Cierra corchete
public final static int AP = Yylex.AP; // Abre parentesis
public final static int CP = Yylex.CP; // Cierra parentesis
public final static int NELEM = Yylex.NELEM;         
public final static int MAXLENGTH = Yylex.MAXLENGTH;         
public final static int MAXDEPTH = Yylex.MAXDEPTH;         
	
private static int token;
private static Yylex lex;
private static int NELEM1 = 0;
private static int MAX1 = 0;
private static int d = 0;
private static int l = 0;
private static int MAX2 = 0;
private static int yylex() {
	int token = 0;
	try {
		token = lex.yylex();
	} catch (IOException e) {
		System.out.println("ERROR");
		System.exit(0);
	}
	return token;
}

public static void error(int itoken) {
		
		System.out.println("ERROR");
		System.exit(0);
	}

public static void main(String[] arg) {
    if (arg.length>0) {
        try {
            lex = new Yylex(new FileReader(arg[0]));
            token = yylex();
            axioma();
        } catch (IOException e) {
        } 
    }
}


private static void axioma(){
	switch(token){
		case AC:
		case EOF:
			if(token == AC){
				token = yylex();
				amayus();
			} else {
				error(token);
			}
			if(token == CC){
				token = yylex();
			} else {
				error(token);
			}
			if(token == EOF){
				token = yylex();
				System.out.println("CORRECTO");
			} else {
				error(token);
			}
			break;
		case NELEM:
			if(token == NELEM){
				token = yylex();
			} else {
				error(token);
			}
			if(token == AP){
				token = yylex();
			} else {
				error(token);
			}
			if(token == AC){
				token = yylex();
				amayus();
			} else {
				error(token);
			}
			if(token == CC){
				token = yylex();
			} else {
				error(token);
			}
			if(token == CP){
				token = yylex();
				System.out.println("NELEM="+NELEM1);
			} else {
				error(token);
			}
			break;
		case MAXDEPTH:
			if(token == MAXDEPTH){
				token = yylex();
			} else {
				error(token);
			}
			if(token == AP){
				token = yylex();
			} else {
				error(token);
			}
			if(token == AC){
				token = yylex();
				d++;
				if(d>MAX1){
				MAX1 = d;
				}
				amayus();
			} else {
				error(token);
			}
			if(token == CC){
				token = yylex();
			} else {
				error(token);
			}
			if(token == CP){
				token = yylex();
				System.out.println("MAXDEPTH="+MAX1);
			} else {
				error(token);
			}
			break;
		case MAXLENGTH:
			if(token == MAXLENGTH){
				token = yylex();
			} else {
				error(token);
			}
			if(token == AP){
				token = yylex();
			} else {
				error(token);
			}
			if(token == AC){
				token = yylex();
				amayus();
			} else {
				error(token);
			}
			if(token == CC){
				token = yylex();
			} else {
				error(token);
			}
			if(token == CP){
				token = yylex();
				System.out.println("MAXLENGTH="+MAX2);
			} else {
				error(token);
			}
			break;
		default:
			error(token);
			break;
	}

}

private static void amayus(){
	switch(token){
		case NUMERO:
			if(token == NUMERO){
				token = yylex();
				NELEM1++;
				l++;
				if(l>MAX2){
				MAX2 = l;
				}
				bmayus();
			} else {
				error(token);
			}
			break;
		case AC:
			if(token == AC){
				token = yylex();
				d++;
				if(d>MAX1){
				MAX1 = d;
				}
				amayus();
			} else {
				error(token);
			}
			if(token == CC){
				token = yylex();
				bmayus();
			} else {
				error(token);
			}
			break;
		case CC:
			break;
		default:
			error(token);
			break;	
	}
}

private static void bmayus(){
	switch(token){
		case COMA:
			if(token == COMA){
				token = yylex();
				d--;
				cmayus();
			} else {
				error(token);
			}
			break;
		case CC:
			break;
		default:
			error(token);
			break;
	}
}

private static void cmayus(){
	switch(token){
		case NUMERO:
			if(token == NUMERO){
				token = yylex();
				NELEM1++;
				l++;
				if(l>MAX2){
				MAX2 = l;
				}
				bmayus();
			} else {
				error(token);
			}
			break;
		case AC:
			if(token == AC){
				token = yylex();
				d++;
				if(d>MAX1){
				MAX1 = d;
				}
				
				amayus();
			} else {
				error(token);
			}
			if(token == CC){
				token = yylex();
				l = 0;
				bmayus();
			} else {
				error(token);
			}
			break;
		default:
			error(token);
			break;
		}
}

}
