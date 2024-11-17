

%%

%%
         
[^\n\t ]+    { 
                return new Yytoken(Yytoken.PALABRA, yytext());
           }  
\n         { 
                return new Yytoken(Yytoken.EOLN, yytext());
           }  
.          {
		return new Yytoken(Yytoken.CARACTER, yytext());
	   } 
