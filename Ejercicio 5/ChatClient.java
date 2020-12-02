import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient{

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String send_buffer, receive_buffer, name;
		PrintWriter outputStream;
		BufferedReader  inputStream;
		String host="localhost";
		int port=8989;
		Socket socketService=null;

		try {

			socketService = new Socket(host, port);
		
			outputStream = new PrintWriter(socketService.getOutputStream(),true);
			inputStream = new BufferedReader(new InputStreamReader(socketService.getInputStream()));
			
			System.out.println("Introduce tu nombre de usuario: ");
			name = input.nextLine();
			send_buffer="1001+" + name;
			outputStream.println(send_buffer);
			receive_buffer = inputStream.readLine();

			if(receive_buffer.contains("200")){
				Boolean exit = false;
				String[] clientsNames = null;
				int clientSelected = 0;

				while(!exit){
					receive_buffer = inputStream.readLine();

					if(receive_buffer.contains("2001")){
						clientsNames = receive_buffer.split("\\+");
						menu(clientsNames);
						clientSelected = input.nextInt();
						input.nextLine(); // Saltamos siguiente liena
						if(clientSelected == 0){
							exit =  true;
						}
						else if(clientSelected > 0){
							send_buffer="1002+" + clientsNames[clientSelected] + "+CHAT";
							outputStream.println(send_buffer);
						}
					}

					if(receive_buffer.contains("2002")){
						Boolean endChat=false;
						while(!endChat){
							String message;
							System.out.println( "@" + name + "(Para cerrar chat introducir -1)");
							message = input.nextLine();
							if(message.equals("-1")){
								endChat = true;
								send_buffer="1004+CLOSE_CHAT";
								outputStream.println(send_buffer);
							}
							else{
								send_buffer="1003+" + clientsNames[clientSelected] + "+MESSAGE+" + message;
								outputStream.println(send_buffer);
								receive_buffer = inputStream.readLine();
								if(receive_buffer.contains("2003")){
									message = receive_buffer.split("\\+")[2];
									System.out.println("#" + clientsNames[clientSelected] + ": " + message);
								}
								else if(receive_buffer.contains("2005")){
									System.out.println(clientsNames[clientSelected] + " se ha desconectado.");
									endChat = true;
								}
								else
									System.out.println("Error: Envio de mensaje");
							}
						}
					}

					if(receive_buffer.contains("2003")){
						String message;
						message = receive_buffer.split("\\+")[2];
						String chatClientName = receive_buffer.split("\\+")[1];

						System.out.println("#" + chatClientName + ": " + message);
						Boolean endChat=false;
						while(!endChat){
							System.out.println( "@" + name + "(Para cerrar chat introducir -1)");
							message = input.nextLine();
							if(message.equals("-1")){
								endChat = true;
								send_buffer="1004+CLOSE_CHAT";
								outputStream.println(send_buffer);
							}
							else{
								send_buffer="1003+" + chatClientName + "+MESSAGE+" + message;
								outputStream.println(send_buffer);
								receive_buffer = inputStream.readLine();
								if(receive_buffer.contains("2003")){
									message = receive_buffer.split("\\+")[2];
									System.out.println("#" + chatClientName + ": " + message);
								}
								else if(receive_buffer.contains("2005")){
									System.out.println(chatClientName + " se ha desconectado.");
									endChat = true;
								}
								else
									System.out.println("Error: Envio de mensaje");
							}
						}
					}

					if(exit){
						send_buffer = "1005+BYE";
						outputStream.println(send_buffer);
					}
				}
			}

			socketService.close();
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}

	static void menu(String[] clientsNames){
		int clientNum = 0;
		System.out.println("###### Selecciona un usuario para chatear ######");
		for (String name : clientsNames){
			if(clientNum != 0)
				System.out.println("###### " + clientNum + ") " + name + " ######");
			clientNum++;
		}
		System.out.println("###### " + "-1" + ")"  + " Recargar"  + " ######");
		System.out.println("###### " + "0" + ")"  + " Salir"  + " ######");
	}
}
