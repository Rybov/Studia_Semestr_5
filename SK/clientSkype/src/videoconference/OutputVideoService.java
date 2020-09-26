package videoconference;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.FrameSender;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import startapp.Main;
import video.VideoStream;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OutputVideoService {
    private ImageView img;
    private VideoStream vs;

    public static ScheduledExecutorService getTimer() {
        return timer;
    }

    private static ScheduledExecutorService timer;

    public OutputVideoService(ImageView img, VideoStream vs) {
        this.img = img;
        this.vs = vs;
        timer = Executors.newSingleThreadScheduledExecutor();
    }

    private void actualizeImage(Mat frame){
            //put frame to imageView in proper format
            MatOfByte byteMat = new MatOfByte();
            Imgcodecs.imencode(".bmp", frame, byteMat);
            img.setImage(new Image(new ByteArrayInputStream(byteMat.toArray())));
    }

    public void stop(){
        timer.shutdown();
    }

    public void start(){
        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(this::getSend, 0, 33, TimeUnit.MILLISECONDS);
    }


    public void getSend() {
        Mat frame  = vs.getFrame();
        actualizeImage(frame);
        //send frame
        FrameSender fs = new FrameSender();
        try {
            fs.sendFrame(frame);
        } catch (InterruptedException e) {
            Platform.runLater(Main::connectionFailed);
        }
    }
}