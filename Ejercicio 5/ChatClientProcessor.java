import java.util.Scanner;
import java.lang.Thread;


public class ChatClientProcessor extends Thread {
	private String received_message;
	
	public ChatClientProcessor(String _received_message) {
		this.received_message = _received_message;
	}
	
	@Override
	public void run() {
		procesa();
	}

	void procesa(){
		Scanner input = new Scanner(System.in);
		String send_buffer = "";
        
        if(received_message.contains("2001")){
            String[] clientsNames = received_message.split("\\+");
            menu(clientsNames);
            int clientSelected = input.nextInt();
            input.nextLine(); // Saltamos siguiente liena
            
            if(clientSelected == 0){
                send_buffer = "1005+BYE";
            }
            else if(clientSelected == -1){
                send_buffer="1004+LISTA";
            }
            else if(clientSelected > 0){
                send_buffer="1002+SELECT" + clientsNames[clientSelected];
            }
        }

        if(received_message.contains("2002")){
            String name = received_message.split("\\+")[2];
            ChatClientSender sender = new ChatClientSender(name);
            sender.start();
            send_buffer = null;
        }

        if(received_message.contains("3002")){
            System.out.println(received_message.split("\\+")[1]);
        }

        if(received_message.contains("2003")){
            String message;
            message = received_message.split("\\+")[2];
            String name = received_message.split("\\+")[1];
            System.out.println("#" + name + ": " + message);
        }

        if(received_message.contains("3003")){
            System.out.println(received_message.split("\\+")[1]);
        }
        
        if(send_buffer != null)
            ChatClient.send(send_buffer);
	}

    static void menu(String[] clientsNames){
		int clientNum = 0;
		System.out.println("###### Selecciona un usuario para chatear ######");
		for (String name : clientsNames){
			if(clientNum > 1)
				System.out.println("###### " + clientNum + ") " + name + " ######");
			clientNum++;
		}
		System.out.println("###### " + "-1" + ")"  + " Recargar"  + " ######");
		System.out.println("###### " + "0" + ")"  + " Salir"  + " ######");
	}
}
