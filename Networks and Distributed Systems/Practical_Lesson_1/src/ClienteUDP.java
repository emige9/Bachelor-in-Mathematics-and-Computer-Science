import java.net.*;

public class ClienteUDP {
	
	public static void main(String[] args) {
		String serverName = "192.168.164.126";
		int serverPort = 54322;
	
		DatagramSocket serviceSocket = null;
		
		try {
			serviceSocket = new DatagramSocket();
		} catch (SocketException e){
			System.err.println("Error creando el socket");
		}
		
		DatagramPacket datagram = null;
		String message = "Emilio G�mez Esteban.";
		
		try {
			datagram = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(serverName), serverPort);
		} catch (Exception e) {
			System.err.println("Excepci�n creando datagrama: " + e.toString());
		}
	
		try {
			serviceSocket.send(datagram); 
		} catch (Exception e) {
			System.err.println("Excepci�n enviando datagrama: " + e.toString());
		}
		
		serviceSocket.close();
	}
}
