
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 *
 * @author <su nombre aquí>
 */

public class ClientUDP {
    public static void main(String[] args) throws IOException {
        // DATOS DEL SERVIDOR:
        //* FIJOS: coméntelos si los lee de la línea de comandos
        String serverName = "127.0.0.1"; //direccion local
        int serverPort = 54322;
        //* VARIABLES: descoméntelos si los lee de la línea de comandos
        //String serverName = args[0];
        //int serverPort = Integer.parseInt(args[1]);

        DatagramSocket serviceSocket = null;

        //* COMPLETAR: crear socket
		
		try {
			serviceSocket = new DatagramSocket();
		} catch (SocketException e){
			System.err.println("Error creando el socket");
		}

        // INICIALIZA ENTRADA POR TECLADO
        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in) );
        String userInput;
        System.out.println("Introduzca un texto a enviar (FINALIZAR para acabar): ");
        userInput = stdIn.readLine(); /*CADENA ALMACENADA EN userInput*/

        //* COMPLETAR: Comprobar si el usuario quiere terminar servicio
        while (userInput.compareTo("FINALIZAR") != 0)
        {
            //* COMPLETAR: Crear datagrama con la cadena escrito en el cuerpo
        	
        	DatagramPacket datagram = null;
        	byte[] message = userInput.getBytes();
        	
        	try {
    			datagram = new DatagramPacket(message, message.length, InetAddress.getByName(serverName), serverPort);
    		} catch (Exception e) {
    			System.err.println("Excepci�n creando datagrama: " + e.toString());
    		}

            //* COMPLETAR: Enviar datagrama a traves del socket
        	
        	System.out.println("STATUS: Conectado a " + serverName);
        	
        	try {
    			serviceSocket.send(datagram); 
    		} catch (Exception e) {
    			System.err.println("Excepci�n enviando datagrama: " + e.toString());
    		}

            System.out.println("STATUS: Waiting for the reply");

            //* COMPLETAR: Crear e inicializar un datagrama VACIO para recibir la respuesta de máximo 500 bytes
            
            DatagramPacket respuesta = null;
            byte[] ibuf = new byte[500];
       
            try {
    			respuesta = new DatagramPacket(ibuf, ibuf.length);
    		} catch (Exception e) {
    			System.err.println("Excepci�n creando datagrama de respuesta: " + e.toString());
    		}


            //* COMPLETAR: Recibir datagrama de respuesta
            
            System.out.println("STATUS: Recibiendo respuesta...");
            
            serviceSocket.receive(respuesta);

            //* COMPLETAR: Extraer contenido del cuerpo del datagrama en variable line
            String line = new String (respuesta.getData());
            
            System.out.println("STATUS: Extrayendo contenido...");

            System.out.println("echo: " + line);
            System.out.println("Introduzca un texto a enviar (FINALIZAR para acabar): ");
            userInput = stdIn.readLine();
        }

        System.out.println("STATUS: Closing client");

        //* COMPLETAR Cerrar socket cliente
        
        serviceSocket.close();

        System.out.println("STATUS: closed");
    }
}
