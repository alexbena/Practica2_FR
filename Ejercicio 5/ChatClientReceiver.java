public class ChatClientReceiver extends Thread{
    @Override
    public void run() {
        String receive_buffer;
        while(true){
            receive_buffer = ChatClient.receive();
            ChatClientProcessor processor = new ChatClientProcessor(receive_buffer);
            processor.start();
        }
    }
}