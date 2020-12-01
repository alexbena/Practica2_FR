
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//

public class YodafyServidor {

	public static void main(String[] args) {
		ServerSocket serverSocket;
		// Puerto de escucha
		int port=8989;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []buffer=new byte[256];
		// Número de bytes leídos
		int bytesLeidos=0;
		
		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			serverSocket = new ServerSocket(port);
			YodafyHebra hebra = new YodafyHebra(serverSocket);
			//////////////////////////////////////////////////
			
			// Mientras ... siempre!
			hebra.start();

		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
