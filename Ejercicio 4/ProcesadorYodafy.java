//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy{
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private DatagramSocket socketServicio;
	InetAddress direccion;
	DatagramPacket paquete;
	byte[] bufer = new byte[256];
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(DatagramSocket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		String response;
		
		
		try { 

			paquete = new DatagramPacket(bufer, bufer.length);
			socketServicio.receive(paquete);	
			// Lee la frase a Yodaficar:
			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
			
			// Yoda hace su magia:
			// Creamos un String a partir de un array de bytes de tamaño "bytesRecibidos":
			// Yoda reinterpreta el mensaje:
			response = yodaDo(new String(paquete.getData()));
			bufer = response.getBytes();
			paquete = new DatagramPacket(bufer, bufer.length, paquete.getAddress(), paquete.getPort());
			socketServicio.send(paquete);
			// Enviamos la traducción de Yoda:
			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
		} catch (IOException e) {
			System.err.println("Error al obtener los flujos de entrada/salida.");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
