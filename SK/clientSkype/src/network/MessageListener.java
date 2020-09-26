package network;

import javafx.application.Platform;
import startapp.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class MessageListener extends NetworkConnection implements Runnable {
    private static ArrayList<Message> messages = new ArrayList<>();
    private static Semaphore writeLock = new Semaphore(1, true);

    public static Semaphore getWaitLock() {
        return waitLock;
    }

    private static Semaphore waitLock = new Semaphore(1, true);
    private static ArrayList<MessageGetter> waitingGetters = new ArrayList<>();

    public static void addWaitingGetter(MessageGetter messageGetter){
        waitingGetters.add(messageGetter);
    }

    static Message seekMessage(MessageGetter messageGetter) throws InterruptedException {
        writeLock.acquire();
        for (int i = 0; i < messages.size(); i++) {
            for(String type  : messageGetter.getTypes()){
                if (messages.get(i).getType().equals(type)) {
                    Message m = new Message(messages.get(i));
                    messages.remove(i);
                    writeLock.release();
                    return m;
                }
            }
        }
        waitingGetters.add(messageGetter);
        writeLock.release();
        waitLock.release();
        return null;
    }

    static private void unlockWaiting(){
        for (MessageGetter messageGetter :waitingGetters) {
            messageGetter.getTypeLock().release();
        }
        waitingGetters.clear();
    }

    public void listen(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(messageSocket.getInputStream()));
            //read next message from buffer
            while(true) {
                //read size
                String message = reader.readLine();
                int messageSize;
                try {
                    messageSize = Integer.parseInt(message.trim());
                }
                catch (NumberFormatException nfe) {
                    continue;
                }
                //read message's type
                message = reader.readLine();
                char[] buffer = new char[messageSize];
                int checksum = 0;
                while (true){
                    if(checksum == messageSize){
                        writeLock.acquire();
                        messages.add(new Message(message, buffer));
                        unlockWaiting();
                        waitLock.release();
                        writeLock.release();
                        break;
                    }
                    int s = reader.read(buffer,checksum,messageSize-checksum);
                    checksum += s;
                }
            }
        } catch (IOException | InterruptedException | NullPointerException e) {
            Platform.runLater(Main::connectionFailed);
        }
    }

    @Override
    public void run() {
        listen();
    }
}
