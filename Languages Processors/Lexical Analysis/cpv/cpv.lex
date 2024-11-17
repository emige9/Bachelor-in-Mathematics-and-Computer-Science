

%%

%%
[a-zA-ZÑñÁÉÍÓÚáéíóú]*[aeiouAEIOUÁÉÍÓÚáéíóú]{2}[a-zA-ZÑñÁÉÍÓÚáéíóú]*[aeiouAEIOUÁÉÍÓÚáéíóú]    { 
                return new Yytoken(Yytoken.A, "0");
           }
[a-zA-ZÑñÁÉÍÓÚáéíóú]+[aeiouAEIOUÁÉÍÓÚáéíóú]{2}        { 
                return new Yytoken(Yytoken.A, "0");
           } 
[a-zA-Z]*[aeiouAEIOUÁÉÍÓÚáéíóú]         { 
                return new Yytoken(Yytoken.B, "0");
           }  
[a-zA-ZÑñÁÉÍÓÚáéíóú]*[aeiouAEIOUÁÉÍÓÚáéíóú]{2}[a-zA-ZÑñÁÉÍÓÚáéíóú]*[^aeiouAEIOUÁÉÍÓÚáéíóú,;:\n. ]         { 
                return new Yytoken(Yytoken.C, "0");
           } 
[a-zA-ZÑñÁÉÍÓÚáéíóú]*[^aeiouAEIOUÁÉÍÓÚáéíóú,;:\n. ]         { 
                return new Yytoken(Yytoken.D, "0");
           }
\n         {}
.          {}   
