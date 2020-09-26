package sound;

import network.SoundListener;
import network.SoundSender;

import javax.sound.sampled.*;

public class SoundLines {
    public static TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }

    public static SourceDataLine getSourceDataLine() {
        return sourceDataLine;
    }

    private static TargetDataLine targetDataLine;
    private static SourceDataLine sourceDataLine;
    private static AudioFormat format;
    private static DataLine.Info targetInfo;
    private static DataLine.Info sourceInfo;
    private static SoundSender soundSender;
    private static SoundListener soundListener;

    public static int getPackageSize() {
        return packageSize;
    }

    private static int packageSize;
    private static int sampleRate = 8000;
    private static int sampleSizeInBits = 16;

    public SoundLines() {
        format = new AudioFormat(sampleRate, sampleSizeInBits, 1, true, true);
        targetInfo = new DataLine.Info(TargetDataLine.class, format);
        sourceInfo = new DataLine.Info(SourceDataLine.class, format);
        if (!AudioSystem.isLineSupported(targetInfo)) {
            // Handle the error ...
        }
        if (!AudioSystem.isLineSupported(sourceInfo)) {
            // Handle the error ...
        }
        packageSize = (sampleRate/53)*(sampleSizeInBits/8);
    }

    public static void openLines() throws LineUnavailableException, NullPointerException{
        targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
        targetDataLine.open(format);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
        sourceDataLine.open(format);

        soundSender = new SoundSender();
        Thread t1 = new Thread(soundSender);
        t1.start();

        soundListener = new SoundListener();
        Thread t2 = new Thread(soundListener);
        t2.start();
    }

    public static void closeLines(){
        try {
            soundSender.stop();
            soundListener.stop();
            targetDataLine.close();
            sourceDataLine.close();
        }catch (NullPointerException ignore){}
    }
}
