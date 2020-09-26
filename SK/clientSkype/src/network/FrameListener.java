package network;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import sound.SoundLines;
import startapp.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Semaphore;

public class FrameListener extends NetworkConnection{
    private static int width, height, size, soundSize;

    private static Semaphore frameLock;
    private static Semaphore soundLock;
    private static Semaphore typeLock;
    private static Boolean flag;

    public static void setExpectedSize(int size, int width, int height, int soundSize) {
        FrameListener.size = size;
        FrameListener.width = width;
        FrameListener.height = height;
        FrameListener.soundSize = soundSize;
    }

    public static void stableState(){
        frameLock = new Semaphore(0, true);
        soundLock = new Semaphore(0, true);
        typeLock = new Semaphore(1, true);
        flag = false;
    }

    public static void readByte(Boolean isFrame) throws IOException {
        if(flag){ return; }
        InputStream is = transmissionSocket.getInputStream();
        byte[] type = new byte[1];
        if(is.read(type, 0, 1)!=1){
            System.out.println("nie przeczytałem bajta :(");
        }
        if (type[0] == 'p') {
            if(!isFrame){
                flag = true;
                if(typeLock.availablePermits()==0){ typeLock.release(); }
            }
            frameLock.release();
        } else if(type[0] == 's'){
            if(isFrame){
                flag = true;
                if(typeLock.availablePermits()==0){ typeLock.release(); }
            }
            soundLock.release();
        }
        else{
            if(Main.getCallService()!=null){Main.getCallService().canceledCall();}
        }
    }

    public static Mat getFrame() throws InterruptedException, IOException {
        typeLock.acquire();
        FrameListener.readByte(true);
        frameLock.acquire();
        Mat frame = null;
        try {
            InputStream is = transmissionSocket.getInputStream();
            byte[] buffer = new byte[size];
            int checksum = 0;
            while (true) {
                if (checksum == size) {
                    break;
                }
                int s = is.read(buffer, checksum, size - checksum);
                checksum += s;
            }
            if(!flag){
                typeLock.release();
            }else{
                flag = false;
                typeLock.release();
            }
            frame = Mat.zeros(height, width, CvType.CV_8UC3);
            frame.put(0, 0, buffer);
            return frame;
        } catch (IOException e) {
            if(Main.getCallService()!=null) {Main.getCallService().canceledCall();}
            return null;
        }
    }

    public static void getSound () throws InterruptedException, IOException {
        typeLock.acquire();
        FrameListener.readByte(false);
        soundLock.acquire();
        try {
            InputStream is = transmissionSocket.getInputStream();
            byte[] buffer = new byte[soundSize];
            int checksum = 0;
            while (checksum<soundSize) {
                checksum += is.read(buffer, checksum, soundSize - checksum);
            }
            if(!flag){
                typeLock.release();
            }else{
                flag = false;
                typeLock.release();
            }
            SoundLines.getSourceDataLine().write(buffer,0,soundSize);
        }
        catch (IOException e) {
            if(Main.getCallService()!=null){Main.getCallService().canceledCall();}
        }
    }
}
