package videoconference;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sound.SoundLines;
import startapp.Main;
import video.VideoStream;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class VideoconferencePanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button hangupButton;

    @FXML
    private ImageView inputImage;

    @FXML
    private ImageView outputImage;

    private OutputVideoService outputService;

    public VideoStream getVs() {
        return vs;
    }

    private InputVideoService inputService;

    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }

    private Boolean stopped = true;

    private VideoStream vs = new VideoStream();

    public Boolean getStopped() {
        return stopped;
    }

    public void start() throws IOException {
        stopped = false;
        outputService.start();
        Thread is = new Thread(inputService);
        is.start();
        try {
            SoundLines.openLines();
        } catch (LineUnavailableException e) {
            Platform.runLater(Main::connectionFailed);
        }
    }

    @FXML
    void hangup(ActionEvent event) {
        stopped = true;
        outputService.stop();
        Main.getCallService().stopCall();
    }

    private void init(){
        inputService = new InputVideoService(inputImage);

        // is the video stream available?
        if (vs.getCapture().isOpened()){
            outputService = new OutputVideoService(outputImage,vs);
        }
        else {
            // log the error
            System.err.println("Impossible to open the camera connection...");
        }
    }

    @FXML
    void initialize() {
        assert hangupButton != null : "fx:id=\"hangupButton\" was not injected: check your FXML file 'VideoconferencePanel.fxml'.";
        assert inputImage != null : "fx:id=\"inputImage\" was not injected: check your FXML file 'VideoconferencePanel.fxml'.";
        assert outputImage != null : "fx:id=\"outputImage\" was not injected: check your FXML file 'VideoconferencePanel.fxml'.";
        init();
    }

}

