
import java.util.List;
import java.util.LinkedList;

%%
%int
Numero = [0-9]+
Operador = [\+\-\*\/]

%{
	List<Integer> lista = new LinkedList<>();
	String operador;
	
	public void operar(List pila, String op){
		int ida, idb, a, b, res = 0;	
		
		a = (int)pila.get(pila.size()-2);
		b = (int)pila.get(pila.size()-1);
		
		ida = pila.size()-2;
		idb = pila.size()-1;
		
		if(op.equals("+")){
			res = a + b;
		} else if(op.equals("-")){
			res = a - b;
		} else if(op.equals("*")){
			res = a*b;
		} else if(op.equals("/")){
			res = a/b;
		} else {
			System.err.println("Error: operador = " + op);
		}
		
		pila.set(ida, res);
		pila.remove(idb);
	}

%}

%xstate OPERACION
%xstate PARENTESIS

%%
         
<YYINITIAL>    {
	{Numero}	{ lista.add(Integer.parseInt(yytext()));}
	
	{Operador}	{ operador = yytext();
			  yybegin(OPERACION);}
			  
	"("		{yybegin(PARENTESIS);}
	
	")"		{ if(lista.size() > 1) {
					operar(lista, "*");
				};}
	;		{System.out.println(lista.get(0));
			 lista.clear();}
	[^]		{}
           }  
           
<OPERACION>         {
	{Numero}	{lista.add(Integer.parseInt(yytext()));
			 operar(lista, operador);
			 yybegin(YYINITIAL);}
           }  
<PARENTESIS>          {
	{Numero}	{lista.add(Integer.parseInt(yytext()));}
	
	{Operador}	{operador = yytext();
			 yybegin(OPERACION);}
	")"		{ if(lista.size()>1){
				operar(lista,"*");
			   }; }
	;		{System.out.println(lista.get(0));
			 lista.clear();}
	[^]		{} 
	   } 
