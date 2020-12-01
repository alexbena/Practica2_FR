//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class YodafyClienteUDP {

	public static void main(String[] args) {	
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		byte[] bufer = new byte[256];
		InetAddress direccion;
		DatagramPacket paquete;
		// Socket para la conexión UDP
		DatagramSocket socketServicio=null;
		
		try {
			// Creamos un socket UDP:
			//////////////////////////////////////////////////////
			socketServicio = new DatagramSocket();
			//////////////////////////////////////////////////////	

			bufer = "Al monte del volcán debes ir sin demora".getBytes();
			
			direccion = InetAddress.getByName(host);
			paquete = new DatagramPacket(bufer, bufer.length, direccion, port);
			socketServicio.send(paquete);

			paquete = new DatagramPacket(bufer, bufer.length);
			socketServicio.receive(paquete);
			
			// Enviamos el array por el outputStream;
			//////////////////////////////////////////////////////
			//////////////////////////////////////////////////////
			
			// Aunque le indiquemos a TCP que queremos enviar varios arrays de bytes, sólo
			// los enviará efectivamente cuando considere que tiene suficientes datos que enviar...
			// Podemos usar "flush()" para obligar a TCP a que no espere para hacer el envío:
			//////////////////////////////////////////////////////

			//////////////////////////////////////////////////////
			
			// Leemos la respuesta del servidor. Para ello le pasamos un array de bytes, que intentará
			// rellenar. El método "read(...)" devolverá el número de bytes leídos.
			//////////////////////////////////////////////////////
			System.out.println("Reader UDP: " + new String(paquete.getData()));
			//////////////////////////////////////////////////////

			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////
			socketServicio.close();
			//////////////////////////////////////////////////////
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
