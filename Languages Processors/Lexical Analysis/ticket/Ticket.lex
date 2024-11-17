

%%

%unicode
%standalone

	Elemento = [a-zA-Z\s]+
	Decimal = [0-9]+\.[0-9]* | [0-9]*\.[0-9]+
	Entero = [0-9] | [1-9][0-9]* 
%{
	int unidades = 1;
	float descuento = 0;
	float precio = 0;
%}

%int

%xstate IGNORAR
%xstate	LINEA


%%

<IGNORAR> {
	\n {yybegin(YYINITIAL);}
	[^]	{}
}

<LINEA> {
				
	{Entero}uds	{ unidades = Integer.parseInt(yytext().substring(0, yytext().length()-3));
			/*yybegin(DESCUENTO);*/}
	
	({Decimal} | {Entero})%		{descuento = Float.parseFloat(yytext().substring(0,yytext().length()-1));
					/*yybegin(UNIDADES)*/;}
		
			
	{Decimal} | {Entero}		{ Ticket.addItemsDistintos(1);
					Ticket.addItems(unidades);
					precio = Float.parseFloat(yytext());
					Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
					/*yybegin(YYINITIAL)*/;}
	
	({Decimal} | {Entero})E		{ Ticket.addItemsDistintos(1);
					Ticket.addItems(unidades);
						precio = Float.parseFloat(yytext().substring(0,yytext().length()-1));
						Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
									/*yybegin(YYINITIAL);*/}
	
	E({Decimal} | {Entero})		{ Ticket.addItemsDistintos(1);
						Ticket.addItems(unidades);
						precio = Float.parseFloat(yytext().substring(1,yytext().length())); 
						Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
									/*yybegin(YYINITIAL);*/}
	\n		{descuento = 0;
			unidades = 1;
			yybegin(YYINITIAL);}							
	[^]		{}
}

-	{yybegin(LINEA);}
[^]	{yybegin(IGNORAR);}	

/**
<UNIDADES> {	
	{Entero}uds	{ unidades = Integer.parseInt(yytext().substring(0, yytext().length()-3));
			Ticket.addItems(unidades-1);
			yybegin(YYINITIAL);}

	{Decimal} | {Entero}		{ precio = Float.parseFloat(yytext());
					Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
					yybegin(YYINITIAL);}
	
	({Decimal} | {Entero})E		{ precio = Float.parseFloat(yytext().substring(0,yytext().length()-1));
						Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
									yybegin(YYINITIAL);}
	
	E({Decimal} | {Entero})		{ precio = Float.parseFloat(yytext().substring(1,yytext().length())); 
						Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
									yybegin(YYINITIAL);}
	
	[^]		{}
}

<DESCUENTO> {
	({Decimal} | {Entero})%		{descuento = Float.parseFloat(yytext().substring(0,yytext().length()-1));
						yybegin(YYINITIAL);}	
						
	{Decimal} | {Entero}		{ precio = Float.parseFloat(yytext());
					Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
					yybegin(YYINITIAL);}
	
	({Decimal} | {Entero})E		{ precio = Float.parseFloat(yytext().substring(0,yytext().length()-1));
						Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
									yybegin(YYINITIAL);}
	
	E({Decimal} | {Entero})		{ precio = Float.parseFloat(yytext().substring(1,yytext().length())); 
						Ticket.addCompra(unidades*(precio-(precio*descuento*(float)0.01)));
									yybegin(YYINITIAL);}
	
	[^]		{}
}

*/
 
