

%%

	Variable = [a-zA-Z_][a-zA-Z0-9_]*

	Igual = " "*\=" "*
	Union = " "*\+" "*
	Especial = (\\).  

	Salida = System.out.print"ln"?" "*"("


%{
	TablaSimbolos tb = new TablaSimbolos();
	String variable = "", valor = "";
%}

%int
%xstate METODO
%xstate DECLARACION
%xstate TEXTO
%xstate SALIDA
%xstate SUSTITUCION

%%

<YYINITIAL> {
	/*Declaración de una clase*/
	class    { System.out.print(yytext());}
	
	/*Nombre de la clase*/
	{Variable}    { System.out.print(yytext() + "_rmj");}
	
	/*Inicio de la clase*/
	"{"           {System.out.print(yytext());
			yybegin(METODO);}
	
	/*Final de la clase*/
	"}"		{System.out.print(yytext());}
	
	/*Todo lo demás*/
	[^] 		{System.out.print(yytext());}
}

<METODO> {
	/*Cabecera método*/
	[^]*"{"    {System.out.print(yytext());}
	
	
	{Igual}	{}
	
	/*Declaración variable*/
	String" "*	{}
	
	/*Nombre variable*/
	{Variable}	{variable = yytext();
			 yybegin(DECLARACION);}
	
	/*Mostrar por pantalla*/
	{Salida}		 {System.out.print(yytext() + "\"");
					yybegin(SALIDA);}
	
	/*Final método*/
	"}"		{System.out.print(yytext());
			 yybegin(YYINITIAL);}
			 
	/*Todo lo demás*/
	[^]            {System.out.print(yytext());} 
}

<DECLARACION> {
	/*Inicio texto (valor)*/
	\"     {yybegin(TEXTO);}
	
	/*Valor variable*/
	{Variable}	{valor += tb.get(yytext());}
	
	/*Asignación*/
	{Igual} | {Union}	{}
	
	/*Final declaración*/
	[,;\n]           {tb.put(variable, valor);
			  valor= "";
			  yybegin(METODO);}
	[^]              {System.out.print(yytext());}
}

<TEXTO> {
	/*Texto*/
	[^\\\"]+  | {Especial}    {valor += yytext();}
	
	/*Asignación*/
	{Union}		{}
	
	/*Final cadena*/
	\" 		{yybegin(DECLARACION);}
}

<SALIDA> {
	/*Inicio de un texto*/
	\"		{yybegin(SUSTITUCION);}
	
	/*Unión cadenas*/
	{Union}		{}
	\\\"		{System.out.print(yytext()); yybegin(SUSTITUCION);}
	
	/*Se reconoce una variable*/
	{Variable}	{System.out.print(tb.get(yytext()));}    
	
	/*Texto*/
	[^;\\\"+\)]+ | {Especial}	{System.out.print(yytext());}
	
	\);		{System.out.print("\"" + yytext());
			 yybegin(METODO);}
			 
	[^]		{System.out.print(yytext());}
}

<SUSTITUCION> {
	\"		{yybegin(SALIDA);}
	
	\\\"		{System.out.print(yytext());}
	
	[^\\\"]+ | {Especial}		{System.out.print(yytext());}
	
	[^]		{System.out.print(yytext());}
}

 
