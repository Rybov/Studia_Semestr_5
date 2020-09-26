package network;

import org.opencv.core.Mat;
import startapp.Main;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Semaphore;

public class FrameSender extends NetworkConnection implements Runnable{
    private byte[] data;
    private Boolean isFrame = true;
    private static Semaphore lock = new Semaphore(1, true);

    public void sendFrame(Mat frame) throws InterruptedException {
        lock.acquire();
        data = new byte[(int) (frame.total() * frame.channels())];
        frame.get(0, 0, data);
        Thread t = new Thread(this);
        t.start();
    }

    public void sendSound(byte[] sound) throws InterruptedException {
        lock.acquire();
        data = sound;
        isFrame = false;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public synchronized void run() {
        try {
            OutputStream os = transmissionSocket.getOutputStream();
            if(isFrame){
                os.write("p".getBytes());
                os.write(data);
            }
            else {
                os.write("s".getBytes());
                os.write(data);
            }
        } catch (IOException e) {
            try {
                if(Main.getCallService()!=null){  Main.getCallService().canceledCall(); }
            } catch (IOException ignore) { }
        }
        lock.release();
    }
}
