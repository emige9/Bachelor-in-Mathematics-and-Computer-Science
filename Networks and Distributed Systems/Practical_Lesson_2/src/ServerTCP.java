import java.io.*;
import java.net.*;
//import java.util.Scanner;

class ServerTCP {
	
	public static String invertir(String s) {
		StringBuilder sbr = new StringBuilder(s);
		sbr.reverse();
		return sbr.toString();
	}
	
    public static String capitalize(String s){
        String words[] = s.split("\\s");
        String res = "";
        for(String w: words){
            if(!res.isEmpty()){
                res += " ";
            }
            res += w.substring(0,1).toUpperCase() + w.substring(1);
        }
        return res;
    }

    public static void main(String[] args) throws IOException
    {
        // DATOS DEL SERVIDOR
        //* FIJO: Si se lee de lÃ­nea de comando debe comentarse
        int port = 12345; // puerto del servidor
        //* VARIABLE: Si se lee de lÃ­nea de comando debe descomentarse
        // int port = Integer.parseInt(args[0]);

        // SOCKETS
        ServerSocket server = null; // Pasivo (recepciÃ³n de peticiones)
        Socket client = null;       // Activo (atenciÃ³n al cliente)

        // FLUJOS PARA EL ENVÃ�O Y RECEPCIÃ“N
        BufferedReader in = null;
        PrintWriter out = null;
        
        int elemCola = 1;

        //* COMPLETAR: Crear e inicalizar el socket del servidor (socket pasivo)

        System.out.println("Inicializando servidor...");
        
        try {
			server = new ServerSocket(port, elemCola);
		} catch(Exception e) {
			System.err.println("Error creando server socket");
			System.exit(1);
		}
        
        
        
        
        while (true) // Bucle de recepciÃ³n de conexiones entrantes
        {
            //* COMPLETAR: Esperar conexiones entrantes
        	
        	System.out.println("Esperando clientes...");
        	
        	try {
				client = server.accept();
			} catch(Exception e) {
				System.err.println("Error aceptando conexión");
			}
			System.out.println("Cliente conectado");
        	

            //* COMPLETAR: Una vez aceptada una conexion, inicializar flujos de entrada/salida del socket conectado
			
			try {
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch(Exception e) {
				System.err.println("Error creando entrada");
			}
			
			try {
				out = new PrintWriter(client.getOutputStream(), true);
			} catch(Exception e) {
				System.err.println("Error creando salida");
				System.exit(1);
			}
			
			out.println("Bienvenido al servidor de inversion de textos");
			out.flush();
			
            boolean salir = false;
            while(!salir) // Inicio bucle del servicio de un cliente
            {
                //* COMPLETAR: Recibir texto en line enviado por el cliente a travÃ©s del flujo de entrada del socket conectado
                String line = null;
                line = in.readLine();

                //* COMPLETAR: Comprueba si es fin de conexion - SUSTITUIR POR LA CADENA DE FIN enunciado
                if (line.compareTo("END") != 0){
                	System.out.println("La cadena a invertir recibida es: " + line);
                	
                    line = capitalize(line);
                    line = invertir(line);
                    
                    System.out.println("La cadena invertida es: " + line);
                    
                    //* COMPLETAR: Enviar texto al cliente a traves del flujo de salida del socket conectado
                    out.println(line);
                    out.flush();
                    System.out.println("Cadena invertida enviada");
                    
                } else { // El cliente quiere cerrar conexiÃ³n, ha enviado END
                	out.println("OK");
                	out.flush();
                	System.out.println("Se va a cerrar conexión por petición del Cliente");
                    salir = true;
                }
            } // fin del servicio

            //* COMPLETAR: Cerrar flujos y socket
            
            out.close();
            in.close();
            
            try {
    			client.close();
    		} catch(Exception e) {
    			System.err.println("Error cerrando el socket");
    			System.exit(1);
    		}

        } // fin del bucle
    } // fin del metodo
}
