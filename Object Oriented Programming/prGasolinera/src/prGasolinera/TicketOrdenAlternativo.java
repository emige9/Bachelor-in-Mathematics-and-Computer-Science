package prGasolinera;
import java.util.*;

public class TicketOrdenAlternativo implements Comparator<Ticket>{
	
	public int compare(Ticket t1, Ticket t2) {
		int res = t2.getNumTicket()-t1.getNumTicket();
		if(res==0) {
			res=t1.getGasolinera().compareToIgnoreCase(t2.getGasolinera());
		}
		return res;
	}
}
