package network;

import java.io.IOException;
import java.net.Socket;

public class NetworkConnection {
    protected static Socket messageSocket, transmissionSocket;
    private static String ip_addr;
    private static int port = 1233;

    public static void setServerConnection(String ip) throws IOException {
        messageSocket = new Socket(ip, port);
        ip_addr = ip;
    }

    public static void setTransmissionConnection() throws IOException{
        transmissionSocket = new Socket(ip_addr, port);
    }


    public static void closeServerConnection() throws IOException, NullPointerException {
        messageSocket.close();
    }

    public static void closeTransmissionConnection() throws IOException, NullPointerException {
        transmissionSocket.close();
    }

}
