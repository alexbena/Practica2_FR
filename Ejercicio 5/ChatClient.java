import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient{
	static private PrintWriter outputStream;
	static private BufferedReader  inputStream;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String send_buffer, receive_buffer = "", name;
		String host="localhost";
		int port=8989;
		Socket socketService=null;

		try {
			socketService = new Socket(host, port);
			outputStream = new PrintWriter(socketService.getOutputStream(),true);
			inputStream = new BufferedReader(new InputStreamReader(socketService.getInputStream()));
			
			while(!receive_buffer.contains("200")){
				System.out.println("Introduce tu nombre de usuario: ");
				name = input.nextLine();
				send_buffer="1001+Login" + name;
				ChatClient.send(send_buffer);
				receive_buffer = ChatClient.receive();
				if(receive_buffer.contains("300"))		
					System.out.println(receive_buffer.split("\\+")[1]);	
			}
			
			if(receive_buffer.contains("200")){
				ChatClientReceiver receiver = new ChatClientReceiver();
				receiver.start();
			}

		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}

	static String receive(){
		String receive_buffer = "";
		try {
			receive_buffer = inputStream.readLine();
		} catch (IOException e) {
			System.out.println("Error de entrada/salida al abrir el socket.");
		}
		return receive_buffer;
	}

	static synchronized void send(String send_buffer){
		outputStream.println(send_buffer);
	}

}
