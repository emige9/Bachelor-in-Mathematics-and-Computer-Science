import java.io.PrintWriter;
import java.net.*;

public class SimpleClienteTCP {
	
	public static void main(String[] args) {
		String serverName = "127.0.0.1";
		int serverPort = 12345;
		
		Socket serviceSocket = null;
		
		PrintWriter out = null;
		
		try {
			serviceSocket = new Socket(serverName, serverPort);
		} catch(Exception e) {
			System.err.println("Error creando el socket");
			System.exit(1);
		}
		
		try {
			out = new PrintWriter(serviceSocket.getOutputStream(), true);
		} catch(Exception e) {
			System.err.println("Error creando salida");
			System.exit(1);
		}
		
		System.out.println("STATUS: Conectando al servidor");
		
		out.println("Esto es una prueba");
		
		out.close();
		
		try {
			serviceSocket.close();
		} catch(Exception e) {
			System.err.println("Error cerrando el socket");
			System.exit(1);
		}
		
	}
	
}
