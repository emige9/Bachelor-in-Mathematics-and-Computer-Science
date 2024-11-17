package alturas;
import java.util.*;
import java.io.*;

public class Mundo {
private List<Pais> paises;
	
	private Mundo(List<Pais> ps) {
		paises=ps;
	}
	
	public List<Pais> getPaises(){
		return paises;
	}
	
	public static Mundo createFromFile(String file) throws FileNotFoundException{
		List<Pais> ps = new ArrayList<>();
		try (Scanner sc = new Scanner(new File(file))){
			while(sc.hasNextLine()) {
				String datoPais=sc.nextLine();
				try(Scanner scPais=new Scanner(datoPais)){
					scPais.useDelimiter("[,]");
					scPais.useLocale(Locale.ENGLISH);
					Pais pais=new Pais(scPais.next(),scPais.next(),scPais.nextDouble());
					ps.add(pais);
				}catch(InputMismatchException ime) {
					//ignorado
				} catch(NoSuchElementException nsee) {
					//ignorado
				}
			}
		}
		return new Mundo(ps);
	}
	
	public static <K,V> void presentaEnConsola(Map<K,V> map) {
		for(Map.Entry<K,V> valores: map.entrySet()) {
			System.out.println(valores.getKey() + "\t" + valores.getValue());
		}
	}
	
	public SortedMap<String,Integer> numeroDePaisesPorContinente(){
		SortedMap<String,Integer> res = new TreeMap<>();
		for(Pais pais : paises) {
			String continente=pais.getContinente();
			int numPaises = res.getOrDefault(continente, 0);
			res.put(continente,numPaises+1);
		}
		return res;
	}
	
	public SortedMap<Double,List<Pais>> paisesPorAltura(){
		SortedMap<Double,List<Pais>> res = new TreeMap<>();
		for(Pais pais : paises) {
			double altura = pais.getAltura();
			double altura1Dec=(int)(altura*10)/10.0;
			List<Pais> listaPaisesAltura = res.getOrDefault(altura1Dec, new ArrayList<Pais>());
			res.putIfAbsent(altura1Dec, listaPaisesAltura);
			listaPaisesAltura.add(pais);
		}
		return res;
	}
	
	public SortedMap<String,SortedSet<Pais>> paisesPorContinente(){
		SortedMap<String,SortedSet<Pais>> res = new TreeMap<>();
		for(Pais pais: paises) {
			String continente = pais.getContinente();
			SortedSet<Pais> conjuntoPaises = res.getOrDefault(continente, new TreeSet<Pais>());
			res.putIfAbsent(continente, conjuntoPaises);
			conjuntoPaises.add(pais);
		}
		return res;
	}
	
	public SortedMap<Character,SortedSet<Pais>> paisesPorInicial(){
		SortedMap<Character,SortedSet<Pais>> res = new TreeMap<>();
		for(Pais pais : paises) {
			char inicial = pais.getNombre().charAt(0);
			SortedSet<Pais> conjuntoPaises = res.getOrDefault(inicial, new TreeSet<Pais>());
			res.putIfAbsent(inicial, conjuntoPaises);
			conjuntoPaises.add(pais);
		}
		
		return res;
	}
	
	public SortedMap<String,Double> mediaPorContinente(){
		SortedMap<String,Double> res = new TreeMap<>();
		SortedMap<String,SortedSet<Pais>> paisesPorContinente = paisesPorContinente();
		for(String continente : paisesPorContinente.keySet()) {
			double media=0;
			int total=0;
			for(Pais p : paisesPorContinente.get(continente)) {
				media+=p.getAltura();
				total++;
			}
			media=media/total;
			res.put(continente, media);
		}
		return res;
	}
	
	public List<String> continentesConMasPaises(){
		SortedMap <String,Integer> contNumPaises = numeroDePaisesPorContinente();
		SortedMap <Integer,List<String>> numeroPaisesListaContinentes = new TreeMap<>();
		for (Map.Entry<String,Integer> entrada : contNumPaises.entrySet()){
			String continente = entrada.getKey();
			int numPaises = entrada.getValue();
			List<String> continentes = numeroPaisesListaContinentes.getOrDefault(numPaises, new ArrayList<>());
			continentes.add(continente);
			numeroPaisesListaContinentes.putIfAbsent(numPaises,continentes);
		}
		return numeroPaisesListaContinentes.get(numeroPaisesListaContinentes.lastKey());
	}
}
