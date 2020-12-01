import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.io.IOException;

public class YodafyHebra extends Thread {
	private ServerSocket serverSocket;
	private Socket socketServicio;

	YodafyHebra(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		socketServicio = null;
	}

	public void run() {

		do {
			try {
				// Aceptamos una nueva conexión con accept()
				/////////////////////////////////////////////////
				socketServicio = serverSocket.accept();
				//////////////////////////////////////////////////
				
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
				procesador.procesa();
			}
			catch (IOException e) {
				System.err.println("Error al aceptar el puerto\n");
			}
			
		} while(true);	
	}
}