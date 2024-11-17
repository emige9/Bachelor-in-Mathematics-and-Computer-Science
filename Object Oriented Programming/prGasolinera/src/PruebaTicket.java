import java.util.SortedSet;
import java.util.*;
import prGasolinera.*;

public class PruebaTicket {
	
	public static void main(String[] args) {
		try {
			Ticket t1 = new Ticket(1,"Teatinos","1111aaa",50,1.40);
			Ticket t2 = new Ticket(1,"TEATINOS","1111AAA",45,2.40);
			Ticket t3 = new Ticket(2,"Teatinos","2222BBB",50,1.40);
			Ticket t4 = new Ticket(1,"Ampliacion","3333CCC",40,1.30);
			
			System.out.println(t1);
			System.out.println(t2);
			if(t1.equals(t2)) {
				System.out.println("Son iguales");
			} else {
				System.out.println("No son iguales");
			}
			
			SortedSet<Ticket> tickets = new TreeSet<>();
			tickets.add(t1);
			tickets.add(t2);
			tickets.add(t3);
			tickets.add(t4);
			System.out.println("\nTickets ordenados por orden natural: ");
			System.out.println(tickets);
			
			System.out.println("\nTickets ordenados por orden alternativo: ");
			tickets = new TreeSet<>(new TicketOrdenAlternativo());
			tickets.add(t1);
			tickets.add(t2);
			tickets.add(t3);
			tickets.add(t4);
			System.out.println(tickets);
			
			Ticket t5 = new Ticket(1,"Teatinos", "4444DDD", -10.0, 1.45);
			System.out.println(t5);
		}catch(GasolineraException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}
