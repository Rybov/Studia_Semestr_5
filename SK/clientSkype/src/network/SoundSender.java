package network;

import javafx.application.Platform;
import sound.SoundLines;
import startapp.Main;

public class SoundSender extends NetworkConnection implements Runnable {
    public void stop() {
        this.stopped = true;
    }

    private Boolean stopped;
    @Override
    public void run() {
        stopped = false;
        byte[] data = new byte[SoundLines.getPackageSize()];
        SoundLines.getTargetDataLine().start();
        while (!stopped) {
            // Read the next chunk of data from the TargetDataLine.
            int numBytesRead = 0;
            while (numBytesRead < SoundLines.getPackageSize()){
                if(stopped){
                    return;
                }
                 numBytesRead +=  SoundLines.getTargetDataLine().read(data, numBytesRead, SoundLines.getPackageSize() - numBytesRead);
            }
            // Save this chunk of data.
            FrameSender fs = new FrameSender();
            try {
                fs.sendSound(data);
            } catch (InterruptedException e) {
                Platform.runLater(Main::connectionFailed);
            }
        }
    }
}
