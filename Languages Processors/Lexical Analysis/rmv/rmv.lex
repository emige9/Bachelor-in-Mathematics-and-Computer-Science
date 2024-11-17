

%%

%unicode
%standalone
%int


%{
	TablaSimbolos tb = new TablaSimbolos();
	String variable, valor;
%}

	Variable = [a-zA-Z_][a-zA-Z0-9_]*
	Comando = [a-zA-Z][a-zA-Z0-9_]*

%xstate VARIABLE
%xstate VALOR
%xstate ARGUMENTOS

%%

<YYINITIAL> {
	{Comando}" "    { System.out.print(yytext());
			  yybegin(ARGUMENTOS);
			  }
	{Variable}\=    { variable = yytext().substring(0,yytext().length()-1);
			  valor = "";
			  yybegin(VARIABLE);
			  }
	[ \n]           {}
}

<VARIABLE> {
	\${Variable}     {valor += tb.get(yytext());}
	[\"]		 {yybegin(VALOR);}
	([a-zA-Z0-9+=*] | (\\;))+          {valor += yytext();}
	[\n;]            {tb.put(variable, valor);
			  yybegin(YYINITIAL);} 
}

<VALOR> {
	\${Variable}     {valor += tb.get(yytext());}
	([a-zA-Z0-9,;+=*\n ] | (\\\") | (\\\$))+            {valor += yytext();}
	\"               {yybegin(VARIABLE);}
}

<ARGUMENTOS> {
	\${Variable}    {System.out.print(tb.get(yytext()));}
	[^\n$]		{System.out.print(yytext());}
	\n 		{System.out.print(yytext());
			 yybegin(YYINITIAL);}
}

 
