package network;

import javafx.application.Platform;
import sound.SoundLines;
import startapp.Main;

import java.io.IOException;

public class SoundListener extends NetworkConnection implements Runnable {
    public void stop() {
        this.stopped = true;
    }

    private Boolean stopped;
    @Override
    public void run() {
        stopped = false;
        SoundLines.getSourceDataLine().start();
        while (!stopped) {
            // Read the next chunk of data from the TargetDataLine.
            try {
                FrameListener.getSound();
            } catch (InterruptedException | IOException e) {
                Platform.runLater(Main::connectionFailed);
            }
        }
    }
}
