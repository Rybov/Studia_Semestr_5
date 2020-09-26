package serverConnection;

import call.CallService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import login.ErrorDisplay;
import network.MessageListener;
import network.NetworkConnection;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import startapp.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static startapp.Main.getVideoController;


public class ServerPanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button IPButton;

    @FXML
    private TextField IPField;

    @FXML
    private Label errorLabel;

    private ErrorDisplay errorDisplay;

    @FXML
    void setConnection(ActionEvent event) {
        try {
            NetworkConnection.setServerConnection(IPField.getText());
        }
        catch (IOException e) {
            errorDisplay.setString("BRAK POŁĄCZENIA Z SERWEREM");
            errorDisplay.displayErrorMessage();
            try {
                NetworkConnection.closeServerConnection();
            } catch (IOException | NullPointerException ignore) {
            }
            return;
        }
        //start message listener:
        MessageListener m = new MessageListener();
        Main.setMessageListener(m);
        Thread t = new Thread(m);
        t.start();
        //start call service:
        //get your cam's frame size...
        Mat frame = getVideoController().getVs().getFrame();
        Size s = frame.size();
        //...and init call service using it
        Main.setCallService(new CallService((int) (frame.total() * frame.channels()),(int)s.width,(int)s.height));
        Thread t1 = new Thread(Main.getCallService());
        t1.start();
        Main.getStage().setScene(Main.getLoginPanel());
    }

    public void lostServerConnection(){
        errorDisplay.setString("UTRACONO POŁĄCZENIE Z SERWEREM");
        errorDisplay.displayErrorMessage();
        try {
            NetworkConnection.closeTransmissionConnection();
        } catch (IOException | NullPointerException ignore) {
        }
        try {
            NetworkConnection.closeServerConnection();
        } catch (IOException | NullPointerException ignore) {
        }
    }

    @FXML
    void initialize() {
        assert IPButton != null : "fx:id=\"IPButton\" was not injected: check your FXML file 'ServerPanel.fxml'.";
        assert IPField != null : "fx:id=\"IPField\" was not injected: check your FXML file 'ServerPanel.fxml'.";
        errorDisplay = new ErrorDisplay(errorLabel);

    }

}

