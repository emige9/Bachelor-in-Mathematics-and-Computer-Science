package prGasolinera;
import java.util.*;
import java.io.*;

public class Gasolinera {
	protected String nombre;
	protected int sigTicket;
	private Map<String,List<Double>> surtidores;
	private Map<String,SortedSet<Ticket>> repostajes;
	private TicketOrdenAlternativo orden;
	public static final String GASOLINA95 = "gasolina95";
	public static final String GASOLINA98 = "gasolina98";
	public static final String DIESEL = "diesel";
	public static final String DIESELPLUS = "dieselPlus";
	private static final int NUM_SURT = 4;
	private final Map<String,Double> precios;
	
	public Gasolinera(String nombre, Map<String,Double> precios, String nombreFichero, TicketOrdenAlternativo orden) throws GasolineraException{
		this.nombre=nombre;
		this.sigTicket=1;
		this.precios=precios;
		repostajes = new HashMap<>();
		this.orden=orden;
		crearSurtidores();
		leerFichero(nombreFichero);
	}
	
	public Gasolinera(String nombre, Map<String,Double> precios, String nomFich) throws GasolineraException{
		this(nombre,precios,nomFich,null);
	}
	
	private void crearSurtidores() {
		surtidores = new HashMap<>();
		for(String s : new String[] {GASOLINA95, GASOLINA98, DIESEL, DIESELPLUS}) {
			List<Double> sts=new ArrayList<>();
			for(int i=0; i<NUM_SURT; i++) {
				sts.add(0.0);
			}
			surtidores.put(s,sts);
		}
	}
	
	private void leerFichero(String nomFich) throws GasolineraException{
		try(Scanner sc = new Scanner(new File(nomFich))){
			while(sc.hasNextLine()) {
				String linea=sc.nextLine();
				anyadirDatosSurtidor(linea);
			}
		}catch(Exception e) {
			throw new GasolineraException(e.getMessage());
		}
	}
	
	private void anyadirDatosSurtidor(String linea) {
		try(Scanner sc = new Scanner(linea)){
			sc.useLocale(Locale.ENGLISH);
			int num_surtidor=sc.nextInt();
			String tipo = sc.next();
			double contenido = sc.nextDouble();
			anyadirDatosSurtidor(tipo,num_surtidor,contenido);
		}catch(Exception e) {
			
		}
	}
	
	private void anyadirDatosSurtidor(String tipo, int num_surtidor, double contenido) {
		if((0<=num_surtidor && num_surtidor<NUM_SURT) && (contenido>=0)) {
			List<Double> sts = surtidores.get(tipo);
			if(sts!=null) {
				double nuevo_contenido=sts.get(num_surtidor)+contenido;
				sts.set(num_surtidor, nuevo_contenido);
			}
		}
	}
	
	public void repostar(String matricula, String tipo, int surtidor, double cantidad) {
		List<Double> lista=surtidores.get(tipo);
		if(lista==null || surtidor<0 || surtidor>=NUM_SURT || cantidad<=0.0) {
			throw new GasolineraException("Datos incorrectos");
		}
		double capacidad_surtidor=lista.get(surtidor);
		if(cantidad>capacidad_surtidor) {
			cantidad=capacidad_surtidor;
		}
		if(cantidad>0.0) {
			lista.set(surtidor, capacidad_surtidor-cantidad);
			asociarTicketVehiculo(matricula,cantidad,precios.get(tipo));
		}
	}
	
	protected Ticket crearTicket(String matricula, double cantidad, double precio) {
		Ticket t=new Ticket(sigTicket,nombre,matricula,cantidad,precio);
		sigTicket++;
		return t;
	}
	
	private void asociarTicketVehiculo(String matricula, double cantidad,double precio) {
		SortedSet<Ticket> tickets = repostajes.get(matricula);
		if(tickets==null) {
			tickets = new TreeSet<>(orden);
			repostajes.put(matricula, tickets);
		}
		Ticket t = crearTicket(matricula,cantidad,precio);
		if(tickets.contains(t)) {
			throw new GasolineraException("Ticket duplicado");
		}
		tickets.add(t);
	}
	
	
	public void facturar(String matricula)throws GasolineraException{
		try(PrintWriter pw = new PrintWriter(nombre+"_"+matricula+".txt")){
			SortedSet<Ticket> tickets = repostajes.get(matricula);
			double total=0.0;
			if(tickets!=null) {
				for(Ticket ticket : tickets) {
					if(!ticket.getFacturado()) {
						pw.println(ticket);
						total=total+ticket.precioTotal();
						ticket.setFacturado(true);
					}
				}
			}
			pw.println("TOTAL = " + total);
		}catch(Exception e) {
			throw new GasolineraException(e.getMessage());
		}
	}
	
	public double obtenerConsumoFacturado(String matricula) {
		double consumo=0.0;
		SortedSet<Ticket> tickets = repostajes.get(matricula);
		if(tickets!=null) {
			for(Ticket t : tickets) {
				if(t.getFacturado()) {
					consumo=consumo+t.getNumLitros();
				}
			}
		}
		return consumo;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(nombre+" = \n");
		sb.append("    Gasolina95: ");
		sb.append(surtidores.get(GASOLINA95)+"\n");
		sb.append("    Gasolina98: ");
		sb.append(surtidores.get(GASOLINA98)+"\n");
		sb.append("    Diesel: ");
		sb.append( surtidores.get(DIESEL)+"\n");
		sb.append("    DieselPlus: ");
		sb.append( surtidores.get(DIESELPLUS)+"\n");
		sb.append("    Repostajes: ");
		sb.append(repostajes.values().toString());
		return sb.toString();
	}
}
