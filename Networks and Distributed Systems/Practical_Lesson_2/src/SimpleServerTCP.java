import java.net.*;
import java.io.*;

public class SimpleServerTCP {
	
	public static void main(String[] args) {
		int port = 12345;
		
		ServerSocket server = null;
		Socket client = null;
		
		try {
			server = new ServerSocket(port);
		} catch(Exception e) {
			System.err.println("Error creando server socket");
			System.exit(1);
		}
		
		System.out.println("Esperando clientes...");
		
		while(true) {
			
			try {
				client = server.accept();
			} catch(Exception e) {
				System.err.println("Error aceptando conexión");
			}
			System.out.println("Cliente conectado");
			
			BufferedReader in = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch(Exception e) {
				System.err.println("Error creando entrada");
			}
			
			String line = null;
			
			try {
				line = in.readLine();
				System.out.println("Línea recibida: " + line);
			} catch(Exception e) {
				System.err.println("Error leyendo buffer");
				System.exit(1);
			}
		}
		

	}
	
}
