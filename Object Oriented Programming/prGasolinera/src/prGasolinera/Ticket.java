package prGasolinera;

public class Ticket implements Comparable<Ticket>{
	private int numTicket;
	private String gasolinera;
	private String matricula;
	private double numLitros;
	private double precioLitro;
	private boolean facturado;
	
	public Ticket (int numero, String nombre, String matricula, double litros, double precio) {
		if(litros<=0 || nombre==null || nombre.length()==0 || matricula==null || matricula.length()==0 || precio<=0) {
			throw new GasolineraException("Valores incorrectos para crear un ticket");
		}
		numTicket=numero;
		gasolinera=nombre;
		this.matricula=matricula;
		numLitros=litros;
		precioLitro=precio;
		facturado=false;
	}
	
	public int getNumTicket() {
		return numTicket;
	}
	
	public double getNumLitros() {
		return numLitros;
	}
	
	public String getGasolinera() {
		return gasolinera;
	}
	
	public boolean getFacturado() {
		return facturado;
	}
	
	public void setFacturado(boolean facturado) {
		this.facturado=facturado;
	}
	
	public double precioTotal() {
		return numLitros*precioLitro;
	}
	
	public boolean equals(Object obj) {
		boolean res=obj instanceof Ticket;
		Ticket ticket = res ? (Ticket)obj : null;
		return res && gasolinera.equalsIgnoreCase(ticket.gasolinera) && this.numTicket==ticket.numTicket;
	}
	
	public int hashCode() {
		return gasolinera.toUpperCase().hashCode()+numTicket;
	}
	
	public int compareTo(Ticket o) {
		int res=this.gasolinera.compareToIgnoreCase(o.gasolinera);
		if(res==0) {
			res=numTicket-o.numTicket;
		}
		return res;
	}
	
	public String toString() {
		return "Ticket: " + numTicket + " (gasolinera: " + gasolinera + ", matricula: " + matricula + ", litros: " + numLitros + ", PRECIO = " + precioTotal() + ")";
	}
}
