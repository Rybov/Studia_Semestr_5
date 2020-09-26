package network;

import startapp.Main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class InitSender extends NetworkConnection implements Runnable{

    private Message message;

    public void send(Message message) {
        this.message = message;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            OutputStream os = transmissionSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(os, true);
            int msgSize = message.getData().length;
            writer.println(msgSize);
            writer.println(message.getType());
            os.write(new String(message.getData()).getBytes());
        } catch (IOException e) {
            Main.connectionFailed();
        }
    }
}
