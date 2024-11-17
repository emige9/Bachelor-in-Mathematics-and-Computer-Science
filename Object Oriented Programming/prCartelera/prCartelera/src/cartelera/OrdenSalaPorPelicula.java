package cartelera;

import java.util.Comparator;

public class OrdenSalaPorPelicula implements Comparator<Sala> {
	public int compare(Sala s1, Sala s2) {
		int res = s1.getPelicula().compareToIgnoreCase(s2.getPelicula());
		if (res == 0) res = s1.getNombre().compareToIgnoreCase(s2.getNombre());
		return res;
	}
}
