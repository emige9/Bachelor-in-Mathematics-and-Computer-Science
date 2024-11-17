package prGasolinera;
import java.util.*;

public class GasolineraPromocion extends Gasolinera{
	private static final double CONSUMO_MINIMO1=100;
	private static final double CONSUMO_MINIMO2=300;
	private static final double DESCUENTO1=0.1;
	private static final double DESCUENTO2=0.3;
	
	public GasolineraPromocion(String nombre, Map<String,Double> precios, String nombreFich) throws GasolineraException{
		this(nombre,precios,nombreFich,null);
	}
	
	public GasolineraPromocion(String nombre, Map<String,Double> precios, String nombreFich, TicketOrdenAlternativo orden) throws GasolineraException{
		super(nombre,precios,nombreFich,orden);
	}
	
	protected Ticket crearTicket(String matricula, double cantidad, double precio) {
		double consumo=obtenerConsumoFacturado(matricula);
		double descuento;
		if(consumo>=CONSUMO_MINIMO2) {
			descuento=DESCUENTO2;
		}else if (consumo>=CONSUMO_MINIMO1) {
			descuento=DESCUENTO1;
		}else {
			descuento=0.0;
		}
		
		Ticket t;
		if(descuento>0.0) {
			t = new TicketPromocion(sigTicket,nombre,matricula,cantidad,precio,descuento);
		}else {
			t = new Ticket(sigTicket,nombre,matricula,cantidad,precio);
		}
		sigTicket++;
		return t;
	}
}
