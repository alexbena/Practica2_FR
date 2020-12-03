import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.lang.Thread;


public class ChatProcessor extends Thread {
	private SocketClient socketClient;
	private PrintWriter outputStream;
	private BufferedReader inputStream;
	private PrintWriter chatOutputStream;
	
	public ChatProcessor(SocketClient socketService) {
		this.socketClient = socketService;
	}
	
	@Override
	public void run() {
		procesa();
	}

	void procesa(){
		String data;
		String response;
		
		try {
			this.outputStream = new PrintWriter(socketClient.socket.getOutputStream(),true);
			this.inputStream = new BufferedReader(new InputStreamReader(socketClient.socket.getInputStream()));
			boolean exit = false;

			while(!exit){
				data = inputStream.readLine();
				System.out.println(data);
				if(data.contains("1001")){
					String _name = data.split("\\+")[1];
					if(ChatServer.getClient(_name) == null){
						socketClient.name = _name;
						ChatServer.addClient(socketClient);
						response = "200";
						outputStream.println(response);
						sendList();
					}
					else{
						response = "300+NOMBRE_NO_DISPONIBLE";
						outputStream.println(response);
					}
				}
				if(data.contains("1002")){
					String _name= data.split("\\+")[1];
					SocketClient clientChat = ChatServer.getClient(_name);
					if(clientChat != null){
						response = "2002+" + _name;
						outputStream.println(response);
					}
					else{
						response = "3002+USUARIO_NO_DISPONIBLE";
						outputStream.println(response);
					}
					
				}
				if(data.contains("1003")){
					String _name= data.split("\\+")[1];
					SocketClient clientChat = ChatServer.getClient(_name);
					String message = data.split("\\+")[3];
					response = "2003+" + socketClient.name + "+" +message;
					this.chatOutputStream = new PrintWriter(clientChat.socket.getOutputStream(),true);
					chatOutputStream.println(response);
				}

				if(data.contains("1004")){
					sendList();
				}

				if(data.contains("1005")){
					exit = true;
				}
			}

			ChatServer.removeClient(socketClient);
			socketClient.socket.close();

		} catch (IOException e) {
			ChatServer.removeClient(socketClient);
			System.err.println("Error al obtener los flujos de entrada/salida.");
		}
	}



	void sendList(){
		ArrayList<String> clientList = ChatServer.getClientList();
		String response = "2001";
		for (String clientName : clientList) {
			if(!socketClient.name.equals(clientName))
				response += "+" + clientName;
		}
		outputStream.println(response);
	}
}
