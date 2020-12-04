public class ChatClientReceiver extends Thread{
    @Override
    public void run() {
        String receive_buffer;
        Boolean exit = false;
        while(!exit){
            receive_buffer = ChatClient.receive();
            if(!receive_buffer.contains("2005")){
                ChatClientProcessor processor = new ChatClientProcessor(receive_buffer);
                processor.start();
            }
            else
                exit = true;
        }
        ChatClient.close();
    }
}