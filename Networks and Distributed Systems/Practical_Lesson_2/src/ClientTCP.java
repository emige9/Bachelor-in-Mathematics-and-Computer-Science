/**
 *
 * @author <tu nombre aqui>
 */
import java.io.*;
import java.net.*;
//import java.util.Scanner;

public class ClientTCP {

    public static void main(String[] args) throws IOException {
        // DATOS DEL SERVIDOR:
        //* FIJOS: comÃ©ntelos si los lee de la lÃ­nea de comandos
         String serverName = "127.0.0.1"; // direccion local
         int serverPort = 12345;
        //* VARIABLES: descomÃ©ntelos si los lee de la lÃ­nea de comandos
       // String serverName = args[0];
        //int serverPort = Integer.parseInt(args[1]);

        // SOCKET
        Socket serviceSocket = null;

        // FLUJOS PARA EL ENV�O Y RECEPCI�N
        PrintWriter out = null;
        BufferedReader in = null;

        //* COMPLETAR: Crear socket y conectar con servidor
        
        try {
			serviceSocket = new Socket(serverName, serverPort);
		} catch(Exception e) {
			System.err.println("Error creando el socket");
			System.exit(1);
		}
        
        System.out.println("STATUS: Conectando al servidor");

        //* COMPLETAR: Inicializar los flujos de entrada/salida del socket conectado en las variables PrintWriter y BufferedReader

        
        try {
			in = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
		} catch(Exception e) {
			System.err.println("Error creando entrada");
		}
        
        try {
			out = new PrintWriter(serviceSocket.getOutputStream(), true);
		} catch(Exception e) {
			System.err.println("Error creando salida");
			System.exit(1);
		}
        
        
        //* COMPLETAR: Recibir mensaje de bienvenida del servidor y mostrarlo
        
        String bienvenida = in.readLine();
        System.out.println(bienvenida);

        // Obtener texto por teclado
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Introduzca un texto a enviar (TERMINAR para acabar): ");
        userInput = stdIn.readLine();

        //* COMPLETAR: Comprobar si el usuario ha iniciado el fin de la interacciÃ³n
        while (userInput.compareTo("TERMINAR") != 0) { // bucle del servicio
            //* COMPLETAR: Enviar texto en userInput al servidor a travÃ©s del flujo de salida del socket conectado
        	
        	out.println(userInput);
        	out.flush();
        		
            //* COMPLETAR: Recibir texto enviado por el servidor a travÃ©s del flujo de entrada del socket conectado
            String line = null;
            line = in.readLine();
            
            System.out.println("El mensaje invertido es: " + line);

            // Leer texto de usuario por teclado
            System.out.println("Introduzca un texto a enviar (TERMINAR para acabar): ");
            userInput = stdIn.readLine();
        } // Fin del bucle de servicio en cliente

        // Salimos porque el cliente quiere terminar la interaccion, ha introducido TERMINAR
        //* COMPLETAR: Enviar END al servidor para indicar el fin deL Servicio
        
        
        out.println("END");
        out.flush();

        //* COMPLETAR: Recibir el OK del Servidor
        
        String OK = in.readLine();
        System.out.println(OK);

        //* COMPLETAR Cerrar flujos y socket
        
        out.close();
        in.close();
        
        try {
			serviceSocket.close();
		} catch(Exception e) {
			System.err.println("Error cerrando el socket");
			System.exit(1);
		}
    }
}
