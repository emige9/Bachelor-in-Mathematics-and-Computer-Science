


%%

%unicode
%standalone

	Variable = [a-zA-Z][a-zA-Z0-9_]* 
	Valor = [^\n\r]*
	
	
%{
	String cadena = "", valor = "", variable = "";
%}


%int

%xstate DEFINE
%xstate DEFINE2
%xstate IFDEF
%xstate ERROR
%xstate IGNORAR


%%


<DEFINE> {
	[^a-zA-Z][^a-zA-Z0-9_]*\s{Valor} {System.out.print("ERROR_DEFINICION");
				yybegin(YYINITIAL);}

	\s{Variable}	{variable = yytext();
			yybegin(DEFINE2);}
	
	[^]	{}
	
}

<DEFINE2>{
	{Valor}		{valor = yytext();
			yybegin(YYINITIAL);}
	
	[^]	{}	
}

<IFDEF> {
	(#ifdef)	{System.out.print("ERROR_IF_ANIDADO");
			yybegin(ERROR);	}
	
	{Valor}\${Variable}{Valor}	{if(yytext().equals(variable)){System.out.print(valor);}else {System.out.print("ID_NO_DEFINIDO");  }}
	
	(#endif)	{yybegin(YYINITIAL);}
	
	(#define)\s{Variable}\s{Valor}	{System.out.print("ERROR_DEFINE_EN_IF");}
	
	[^]	{}
	
}

<ERROR>	{
	(#endif)	{yybegin(IFDEF);}
	
	[^]	{}
	
}

<IGNORAR> {
	(#endif)	{yybegin(YYINITIAL);}
	
	[^]	{}
}

(#define)	{yybegin(DEFINE);}

(#ifdef)\s{Variable}		{cadena = yytext().substring(7,yytext().length());
					if(cadena.equals(variable)){yybegin(IFDEF);}else{yybegin(IGNORAR);}}

(#endif)	{System.out.print("ERROR_ENDIF_SIN_IF");}

\${Variable}	{ if(yytext().equals(variable)){System.out.print(valor); }else {System.out.print("ID_NO_DEFINIDO");}}

[^\n]+(#define)[^\n]+	{System.out.print(yytext());}

[^\n]+(#ifdef)[^\n]+	{System.out.print(yytext());}

[^\n]+(#endif)[^\n]+	{System.out.print(yytext());}

[^]	{System.out.print(yytext());}

