
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer{
	static ArrayList<SocketClient> clients = new ArrayList<>();

	public static void main(String[] args) {
		ServerSocket serverSocket;
		
		int port=8989;

		try {
			serverSocket = new ServerSocket(port);

			do {
				SocketClient socketClient = new SocketClient();
				socketClient.socket = serverSocket.accept();
				
				ChatProcessor processor = new ChatProcessor(socketClient);
				processor.start();
				
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

	static synchronized void addClient(SocketClient c){
		clients.add(c);
	}

	static synchronized void removeClient(SocketClient c){
		clients.remove(c);
	}

	static synchronized SocketClient getClient(String _name){
		for (SocketClient socketClient : clients){
			if(socketClient.name.equals(_name))
				return socketClient;
		}
		return null;
	}

	static synchronized ArrayList<String> getClientList(){
		ArrayList<String> clientList = new ArrayList<>();
		for (SocketClient socketClient : clients){
			clientList.add(socketClient.name);
		}
		return clientList;
	}

}
