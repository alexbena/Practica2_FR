import java.util.Scanner;

public class ChatClientSender extends Thread{
    private String receptor;

    public ChatClientSender(String _receptor) {
        this.receptor = _receptor;
    }
    
    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        String message, send_buffer = "";

        while(send_buffer != null){
            System.out.println( "@(Para cerrar chat introducir -1)");
            message = input.nextLine();

            if(message.equals("-1")){
                send_buffer = null;
            }
            else{
                send_buffer = "1003+" + receptor + "+MESSAGE+" + message;
            }

            if(send_buffer != null)
                ChatClient.send(send_buffer);
        }
        
        send_buffer = "1004+LISTA";
        ChatClient.send(send_buffer);
    }

}
