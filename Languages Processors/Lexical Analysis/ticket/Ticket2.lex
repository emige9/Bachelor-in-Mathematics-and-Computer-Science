%%
%int
%x linea
%x ignore
%{ 
	int haynombre = 0;
	int hayprecio = 0;
	float descuento = 0;
	int numuds = 1;
	float precio = 0;
	
%}

%eof{
	if(hayprecio+haynombre==2){Ticket.addItemsDistintos(1); Ticket.addItems(numuds); Ticket.addCompra(numuds*(precio-(precio*descuento/100)));}

%eof}
%%

<ignore> {
	\n {yybegin(YYINITIAL);}
	[^] {}
}


<linea> {
	[A-Za-z] {haynombre = 1;}
	[0-9]+uds {numuds=Integer.parseInt(yytext().substring(0,yytext().length()-3));}
	[0-9]+.?[0-9]?% {descuento = Float.parseFloat(yytext().substring(0,yytext().length()-1));}
	[0-9]+.?[0-9]*E {precio = Float.parseFloat(yytext().substring(0,yytext().length()-1)); hayprecio=1;}
	E[0-9]+.?[0-9]* {precio = Float.parseFloat(yytext().substring(1,yytext().length())); hayprecio=1;}
	[0-9]+.?[0-9]* {precio = Float.parseFloat(yytext()); hayprecio = 1;}
	\n {if(hayprecio+haynombre==2){Ticket.addItemsDistintos(1); Ticket.addItems(numuds); Ticket.addCompra(numuds*(precio-(precio*descuento*(float)0.01)));} hayprecio=0; haynombre=0; descuento=0; numuds=1; precio=0; yybegin(YYINITIAL);}
	[^] {}
}


- {yybegin(linea);}

[^]          {yybegin(ignore);}
			
	
	

