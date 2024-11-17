
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
//import java.nio.charset.Charset;

/**
 *
 * @author <su nombre aquÃ­>
 */
public class ServerUDP {
	
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

    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException
    {
        // DATOS DEL SERVIDOR
        //* FIJO: Si se lee de lÃ­nea de comando debe comentarse
        int port = 54322; // puerto del servidor
        //* VARIABLE: Si se lee de lÃ­nea de comando debe descomentarse
        //int port = Integer.parseInt(args[0]); // puerto del servidor

        // SOCKET
        DatagramSocket server = null;

        //* COMPLETAR Crear e inicalizar el socket del servidor
        
        try {
        	server = new DatagramSocket(port);
        } catch (SocketException e) {
        	System.err.println("Error creando el socket");
        }
        
        System.out.println("STATUS: Iniciando...");

        // Funcion PRINCIPAL del servidor
        while (true)
        {
            //* COMPLETAR: Crear e inicializar un datagrama VACIO para recibir la respuesta de mÃ¡ximo 500 bytes
        	
        	System.out.println("Recibiendo datagrama del cliente: ");
        	
        	DatagramPacket receiver = null;
            byte[] ibuf = new byte[500];
       
            try {
    			receiver = new DatagramPacket(ibuf, ibuf.length);
    		} catch (Exception e) {
    			System.err.println("Excepción creando datagrama de respuesta: " + e.toString());
    		}

            //* COMPLETAR: Recibir datagrama
            
            server.receive(receiver);

            //* COMPLETAR: Obtener texto recibido
            String line = new String (receiver.getData(), receiver.getOffset(), receiver.getLength(), StandardCharsets.UTF_8);
            
            System.out.println("STATUS: Conectado a " + receiver.getAddress());

            //* COMPLETAR: Mostrar por pantalla la direccion socket (IP y puerto) del cliente y su texto
            
            System.out.println("Cliente IP: " + receiver.getAddress().getHostAddress());
            System.out.println("Cliente Port: " + receiver.getPort());
            System.out.println("Texto: " + line);

            // Capitalizamos la linea e invertimos
            line = capitalize(line);
            line = invertir(line);
            

            //* COMPLETAR: crear datagrama de respuesta
            
            System.out.println("STATUS: Creando respuesta");
            
            DatagramPacket respuesta = null;
            byte[] message = line.getBytes();
            
            try {
    			respuesta = new DatagramPacket(message, message.length, receiver.getAddress(), receiver.getPort());
    		} catch (Exception e) {
    			System.err.println("Excepción creando datagrama de respuesta: " + e.toString());
    		}

            //* COMPLETAR: Enviar datagrama de respuesta
            
            System.out.println("STATUS: Enviando respuesta");
            
            try {
    			server.send(respuesta); 
    		} catch (Exception e) {
    			System.err.println("Excepción enviando datagrama: " + e.toString());
    		}
            

        } // Fin del bucle del servicio
    }

}
