

%%

%unicode
%standalone

%int

%xstate LINEA
%xstate BLOQUE
%xstate DOCUMENTACION

%%

<YYINITIAL> {
	\".*\"	{}

	\/\/    {yybegin(LINEA);}
	
	\/\*   {yybegin(BLOQUE);}
	
	\/\*\*          {yybegin(DOCUMENTACION);}
	
	[^]		{}
}

<LINEA> {
	[^\s\t\n\r]    {JCom.linea++;}
	
	\n		 {yybegin(YYINITIAL);}

	[^]		{}
}

<BLOQUE> {
	\*\/          {yybegin(YYINITIAL);}
	
	[^\s\t\n\r]    {JCom.bloque++;}
	
	[^]		{}
}

<DOCUMENTACION> {
	\*\/    	{yybegin(YYINITIAL);}

	[^\s\t\n\r]    {JCom.documentacion++;}

	[^]		{}
}

 
