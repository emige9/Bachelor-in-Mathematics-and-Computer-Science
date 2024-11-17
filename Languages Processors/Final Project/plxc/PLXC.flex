import java_cup.runtime.*;

%%

%int
%cup
%xstate STRING
%xstate UNICODE

%{
	StringBuffer str = new StringBuffer();
%}

%%

<YYINITIAL>{
"if"			{ return new Symbol(sym.IF);   	}
"else"			{ return new Symbol(sym.ELSE); 	}
"do"			{ return new Symbol(sym.DO);	 	}
"while"			{ return new Symbol(sym.WHILE);	}
"for"			{ return new Symbol(sym.FOR);		}
"print"			{ return new Symbol(sym.PRINT); 	}
"length"		{ return new Symbol(sym.LENGTH);	} 
"int"			{ return new Symbol(sym.INT);		}
"float"			{ return new Symbol(sym.FLOAT);		}
"char"			{ return new Symbol(sym.CHAR);		}
"string"		{ return new Symbol(sym.STRING);	}
"("			{ return new Symbol(sym.AP);	}
")"			{ return new Symbol(sym.CP);	}
"["			{ return new Symbol(sym.AC);	}
"]"			{ return new Symbol(sym.CC);	}
"{"			{ return new Symbol(sym.ALL);	}
"}"			{ return new Symbol(sym.CLL);	}
";"			{ return new Symbol(sym.PYC);	}
","			{ return new Symbol(sym.COMA);	}
"."			{ return new Symbol(sym.PUNTO);	}
"="			{ return new Symbol(sym.ASIG);	}
"=="			{ return new Symbol(sym.IGUAL);	}
"!="			{ return new Symbol(sym.NOIGUAL);	}
"<="			{ return new Symbol(sym.MENORIGUAL);	}
"<"			{ return new Symbol(sym.MENOR);	}
">="			{ return new Symbol(sym.MAYORIGUAL);	}
">"			{ return new Symbol(sym.MAYOR);	}
"&&"			{ return new Symbol(sym.AND);		}
"||"			{ return new Symbol(sym.OR);		}
"!"			{ return new Symbol(sym.NOT);		}
"+"			{ return new Symbol(sym.MAS);		}
"-"			{ return new Symbol(sym.MENOS);	}
"*"			{ return new Symbol(sym.POR);		}
"/"			{ return new Symbol(sym.DIV);		}
'.'			{ return new Symbol(sym.CH,String.valueOf((int)(yytext().charAt(1)))); }

[1-9][0-9]* | 0						{ return new Symbol(sym.ENTERO, yytext());	}
(([0-9]*\.[0-9]+|[0-9]+\.[0-9]*)([eE][+-]?[0-9]+)?)	{ return new Symbol(sym.REAL, yytext());	}
[_a-zA-Z][_a-zA-Z0-9]*					{ return new Symbol(sym.IDENT, yytext());	}
\"                                                      { str.setLength(0); yybegin(STRING); }
[^]							{}
}

<STRING>{
	\"		{ yybegin(YYINITIAL); return new Symbol(sym.STR, str.toString());	}
	[^\n\r\"\\]+	{ str.append(yytext());	}
	\\\"		{ str.append('\"');	}
	\\		{ str.append('\\');	}
	\\\\		{ str.append('\\');	}
	\\u		{ yybegin(UNICODE);	}
}


<UNICODE>{
	[a-zA-Z0-9]{4}		{int code = Integer.parseInt(yytext(),16); str.append((char)code); yybegin(STRING);}
}



