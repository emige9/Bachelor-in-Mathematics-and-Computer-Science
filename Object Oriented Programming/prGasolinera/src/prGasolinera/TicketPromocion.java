package prGasolinera;

public class TicketPromocion extends Ticket{
	private double descuento;
	
	public TicketPromocion(int numTicket, String gasolinera, String matricula, double numLitros, double precioLitro, double descuento) {
		super(numTicket,gasolinera,matricula,numLitros,precioLitro);
		this.descuento=descuento;
	}
	
	public double precioTotal() {
		return super.precioTotal()*(1-descuento);
	}
	
	public String toString() {
		return "PROMOCION " + descuento*100 + "%: " + super.toString();
	}
}
