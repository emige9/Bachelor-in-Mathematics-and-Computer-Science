package cartelera;
import java.io.*;
import java.util.*;

public class Cartelera {
	protected Map<Sala, SortedSet<Sesion>> cartelera;
	
	public Cartelera() {
		cartelera = new HashMap<Sala,SortedSet<Sesion>>();
	}
	
	public Cartelera (String fichero) throws FileNotFoundException{
		this();
		leerCartelera(fichero);
	}
	
	public void leerCartelera(String fichero) throws FileNotFoundException {
		Scanner scFichero = new Scanner(new File(fichero));
		leerCartelera(scFichero);
		scFichero.close();
	}
	
	public void leerCartelera(Scanner sc) {
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			Scanner scLinea = new Scanner(linea);
			scLinea.useDelimiter("[>()-]+");
			try {
				String nombreSala = scLinea.next();
				String titPeli = scLinea.next();
				int dur = scLinea.nextInt();
				Sala sala = new Sala(nombreSala,titPeli,dur);
				while(scLinea.hasNext()) {
					String ses = scLinea.next();
					Scanner scSesion = new Scanner(ses);
					scSesion.useDelimiter("[:]+");
					int hora = scSesion.nextInt();
					int minutos = scSesion.nextInt();
					Sesion sesion = new Sesion(hora,minutos);
					nuevaSesion(sala,sesion);
					scSesion.close();
				}
				
			} catch (InputMismatchException ime) {
				throw new CarteleraException("La duracion de la pelicula o la hora de la sesion descrito en la linea " +
											linea + " no tienen formato numerico correcto");
			} catch (NoSuchElementException nsee) {
				throw new CarteleraException("El formato de la linea: " + linea + " no es correcto");
			} catch (CarteleraException pe) {
				String mensaje = pe.getMessage();
				// Recuperamos el mensaje, de la excepción cuando la hora o los minutos de la sesión
				// no están dentro del rango, o cuando la duración de la película es negativa.
				// En esos casos, enriquecemos el mensaje de la excepción, añadiendo la línea 
				// donde se ha producido el error.
				throw new CarteleraException(mensaje + " en la linea " + linea);
			} finally {
				scLinea.close();
			}
		}
	}
	
	public void nuevaSesion(Sala sala, Sesion sesion) {
		SortedSet<Sesion> sesiones = cartelera.getOrDefault(sala,new TreeSet<Sesion>());
		cartelera.putIfAbsent(sala, sesiones);
		sesiones.add(sesion);
	}
	
	public boolean esConsistente() {
		Iterator<Sala> itSalas = cartelera.keySet().iterator();
		boolean consistente = true;
		while (consistente && itSalas.hasNext()) {
			consistente = esConsistente(itSalas.next());
		}
		return consistente;
	}
	
	protected boolean esConsistente(Sala sala) {
		SortedSet<Sesion> sesiones = cartelera.get(sala);
		Iterator<Sesion> it = sesiones.iterator();
		// Tal y como se añaden las sesiones a una sala, si ésta aparece en la cartelera, 
		// al menos debe incluir una sesión
		Sesion ses = it.next();
		boolean consistente = true;
		while (it.hasNext() && consistente) {
			Sesion sesSig = it.next();
			consistente = ses.diferenciaMinutos(sesSig) >= sala.getDuracion();
			ses = sesSig;
		}
		return consistente;
	}
	
	public Set<Sesion> todasSesiones(String titPeli) {
		SortedSet<Sesion> sesiones = new TreeSet<Sesion>();
		for (Sala sala : cartelera.keySet()) {
			if (sala.getPelicula().equalsIgnoreCase(titPeli)) 
				sesiones.addAll(cartelera.get(sala));
		}
		return sesiones;
	}
	
	public void mostrarCartelera(String fichero) throws FileNotFoundException {
		PrintWriter pwFichero = new PrintWriter(fichero);
		mostrarCartelera(pwFichero);
		pwFichero.close();
	}
	
	public void mostrarCartelera(PrintWriter pwFichero) {
		for (Sala sala : cartelera.keySet()) {
			pwFichero.println(sala);
			pwFichero.print("\t");
			for (Sesion sesion : cartelera.get(sala)) {
				pwFichero.print(sesion);
			}
			pwFichero.println();
		}
	}
}
