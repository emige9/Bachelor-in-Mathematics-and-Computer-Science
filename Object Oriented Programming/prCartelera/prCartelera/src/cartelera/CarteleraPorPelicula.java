package cartelera;

import java.io.FileNotFoundException;
import java.util.SortedSet;
import java.util.TreeMap;

public class CarteleraPorPelicula extends Cartelera {
	
	public CarteleraPorPelicula() {
		cartelera = new TreeMap<Sala,SortedSet<Sesion>>(new OrdenSalaPorPelicula());
	}

	public CarteleraPorPelicula(String fichero) throws FileNotFoundException {
		this();
		leerCartelera(fichero);
	}
}
