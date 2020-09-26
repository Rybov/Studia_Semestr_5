package videoconference;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.FrameListener;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import startapp.Main;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class InputVideoService implements Runnable{
    private ImageView img;
    private Boolean isWorking = true;

    public InputVideoService(ImageView img) {
        this.img = img;
    }

    private void actualizeImage(Mat frame){
        //put frame to imageView in proper format
        try {
            MatOfByte byteMat = new MatOfByte();
            Imgcodecs.imencode(".bmp", frame, byteMat);
            img.setImage(new Image(new ByteArrayInputStream(byteMat.toArray())));
        }catch (CvException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!Main.getVideoController().getStopped()){
            try {
                Mat frame = FrameListener.getFrame();
                if(frame != null) {
                    actualizeImage(frame);
                }else{
                    if(Main.getCallService()!=null){Main.getCallService().canceledCall();}
                    return;
                }
            } catch (InterruptedException | IOException e) {
                try {
                    if(Main.getCallService()!=null){Main.getCallService().canceledCall();}
                } catch (IOException ignore) {
                }
                return;
            }
        }
    }
}
