package es.uma.rysd.app;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import es.uma.rysd.entities.*;

public class SWClient {
	// TODO: Complete el nombre de la aplicación
    private final String app_name = "My SW Client v0.1";
    private final int year = 2022;
    
    private final String url_api = "https://swapi.dev/api/";

    // Métodos auxiliares facilitados
    
    // Obtiene la URL del recurso id del tipo resource
	public String generateEndpoint(String resource, Integer id){
		return url_api + resource + "/" + id + "/";
	}
	
	// Dada una URL de un recurso obtiene su ID
	public Integer getIDFromURL(String url){
		String[] parts = url.split("/");

		return Integer.parseInt(parts[parts.length-1]);
	}
	
	//Actualizamos los datos de Persona con lo que se ha recogido en el get
	private void completePersonData(Person p) {
		if(p == null) return;
		
		if(p.homeworld != null) {
			p.homeplanet = getPlanet(p.homeworld);
		}
	}
	
	// Consulta un recurso y devuelve cuántos elementos tiene
	public int getNumberOfResources(String resource){
		
		String urlname = url_api + resource + "/";
		int number = 0;
		
		// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		
		// TODO: Cree la URL correspondiente: https://swapi.dev/api/{recurso}/ reemplazando el recurso por el parámetro 
    	
    	// TODO: Cree la conexión a partir de la URL
		
		try {
			URL url = new URL(urlname);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
			
			connection.addRequestProperty("User-Agent", app_name + "-" + year);
			connection.addRequestProperty("Accept", "application/json");
    	
    	// TODO: Indique que es una petición GET
			
			connection.setRequestMethod("GET");
    	
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode/100 != 2) {
				System.err.println("Incorrect answer: " + connection.getResponseCode() + " " + connection.getResponseMessage());
				return 0;
			}
    	
    	// TODO: Deserialice la respuesta a ResourceCountResponse
        Gson parser = new Gson();
        InputStream in = connection.getInputStream(); // TODO: Obtenga el InputStream de la conexión
        ResourceCountResponse c = parser.fromJson(new InputStreamReader(in), ResourceCountResponse.class);
        number = c.count;
        connection.disconnect();
        
		} catch(MalformedURLException e) {
			System.err.println("Non-valid URL.");
			return 0;
		} catch (IOException e) {
			System.err.println("Error: " + e.toString());
		}
		
        // TODO: Devuelva el número de elementos
        return number;
	}
	
	public Person getPerson(String urlname) {
    	Person p = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");

    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		    	
    	// TODO: Cree la conexión a partir de la URL recibida
    	
    	try {
			URL url = new URL(urlname);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
    	
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
			
			connection.addRequestProperty("User-Agent", app_name + "-" + year);
			connection.addRequestProperty("Accept", "application/json");
    	
    	// TODO: Indique que es una petición GET
			
			connection.setRequestMethod("GET");
    	
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode/100 != 2) {
				System.err.println("Incorrect answer: " + connection.getResponseCode() + " " + connection.getResponseMessage());
				return null;
			}
    	
    	// TODO: Deserialice la respuesta a Person
			
			Gson parser = new Gson();
			p = parser.fromJson(new InputStreamReader(connection.getInputStream()), Person.class);
			completePersonData(p);
			connection.disconnect();
    	
        // TODO: Para las preguntas 2 y 3 (no necesita completar esto para la pregunta 1)
    	// TODO: A partir de la URL en el campo homreworld obtenga los datos del planeta y almacénelo en atributo homeplanetç
			
    	} catch(MalformedURLException e) {
			System.err.println("Non-valid URL.");
			return null;
		} catch (IOException e) {
			System.err.println("Error: " + e.toString());
		}

    	return p;
	}
	

	public Vehicle getVehicle(String urlname) {
		Vehicle vehiculo = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");

    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		    	
    	// TODO: Cree la conexión a partir de la URL recibida
    	
    	try {
			URL url = new URL(urlname);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
    	
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
			
			connection.addRequestProperty("User-Agent", app_name + "-" + year);
			connection.addRequestProperty("Accept", "application/json");
    	
    	// TODO: Indique que es una petición GET
			
			connection.setRequestMethod("GET");
    	
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode/100 != 2) {
				System.err.println("Incorrect answer: " + connection.getResponseCode() + " " + connection.getResponseMessage());
				return null;
			}
    	
    	// TODO: Deserialice la respuesta a Planet
			
			Gson parser = new Gson();
			vehiculo = parser.fromJson(new InputStreamReader(connection.getInputStream()), Vehicle.class);
			connection.disconnect();
			
    	} catch(MalformedURLException er) {
			System.err.println("Non-valid URL.");
			return null;
		} catch (IOException er) {
			System.err.println("Error: " + er.toString());
		}
    	
        return vehiculo;
	}
	
	public Starship getStarship(String urlname) {
		Starship nave = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");

    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		    	
    	// TODO: Cree la conexión a partir de la URL recibida
    	
    	try {
			URL url = new URL(urlname);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
    	
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
			
			connection.addRequestProperty("User-Agent", app_name + "-" + year);
			connection.addRequestProperty("Accept", "application/json");
    	
    	// TODO: Indique que es una petición GET
			
			connection.setRequestMethod("GET");
    	
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode/100 != 2) {
				System.err.println("Incorrect answer: " + connection.getResponseCode() + " " + connection.getResponseMessage());
				return null;
			}
    	
    	// TODO: Deserialice la respuesta a Planet
			
			Gson parser = new Gson();
			nave = parser.fromJson(new InputStreamReader(connection.getInputStream()), Starship.class);
			connection.disconnect();
			
    	} catch(MalformedURLException er) {
			System.err.println("Non-valid URL.");
			return null;
		} catch (IOException er) {
			System.err.println("Error: " + er.toString());
		}
    	
        return nave;
	}
	
	public Planet getPlanet(String urlname) {
    	Planet p = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");

    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		    	
    	// TODO: Cree la conexión a partir de la URL recibida
    	
    	try {
			URL url = new URL(urlname);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
    	
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
			
			connection.addRequestProperty("User-Agent", app_name + "-" + year);
			connection.addRequestProperty("Accept", "application/json");
    	
    	// TODO: Indique que es una petición GET
			
			connection.setRequestMethod("GET");
    	
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode/100 != 2) {
				System.err.println("Incorrect answer: " + connection.getResponseCode() + " " + connection.getResponseMessage());
				return null;
			}
    	
    	// TODO: Deserialice la respuesta a Planet
			
			Gson parser = new Gson();
			p = parser.fromJson(new InputStreamReader(connection.getInputStream()), Planet.class);
			connection.disconnect();
			
    	} catch(MalformedURLException e) {
			System.err.println("Non-valid URL.");
			return null;
		} catch (IOException e) {
			System.err.println("Error: " + e.toString());
		}
    	
        return p;
	}

	public Person search(String name) throws UnsupportedEncodingException{
    	Person p = null;
    	
    	String urlname = url_api + "/people/?search=" + URLEncoder.encode(name, "utf-8");
    	
    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		    	
    	// TODO: Cree la conexión a partir de la URL (url_api + name tratado - vea el enunciado)
    	
    	try {
			URL url = new URL(urlname);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
    	
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
			
			connection.addRequestProperty("User-Agent", app_name + "-" + year);
			connection.addRequestProperty("Accept", "application/json");
    	
    	// TODO: Indique que es una petición GET
    	
			connection.setRequestMethod("GET");
			
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode/100 != 2) {
				System.err.println("Incorrect answer: " + connection.getResponseCode() + " " + connection.getResponseMessage());
				return null;
			}
    	
    	// TODO: Deserialice la respuesta a SearchResponse -> Use la primera posición del array como resultado
			
			Gson parser = new Gson();
			SearchResponse c = parser.fromJson(new InputStreamReader(connection.getInputStream()), SearchResponse.class);
			p = c.results[0];
			completePersonData(p);
			connection.disconnect();
    	
        // TODO: Para las preguntas 2 y 3 (no necesita completar esto para la pregunta 1)
    	// TODO: A partir de la URL en el campo homreworld obtenga los datos del planeta y almacénelo en atributo homeplanet

    	} catch(MalformedURLException e) {
			System.err.println("Non-valid URL.");
			return null;
		} catch (IOException e) {
			System.err.println("Error: " + e.toString());
		}
			
        return p;
    }

}
