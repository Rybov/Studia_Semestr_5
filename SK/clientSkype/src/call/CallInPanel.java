package call;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import network.InitSender;
import network.Message;
import network.NetworkConnection;
import startapp.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CallInPanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button callButton;

    @FXML
    private Button returnButton;

    @FXML
    private Label username;


    @FXML
    void call(ActionEvent event) {
        try {
            NetworkConnection.setTransmissionConnection();
            Message message = new Message("W",Main.getName().toCharArray());
            InitSender initSender = new InitSender();
            initSender.send(message);
            Main.getStage().setScene(Main.getVideoPanel());
            Main.getCallService().answer(true);
        } catch (IOException e) {
            Platform.runLater(Main::connectionFailed);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        Platform.runLater(() -> Main.getStage().setScene(Main.getMainPanel()));
        try {
            Main.getCallService().answer(false);
        } catch (IOException e) {
            Platform.runLater(Main::connectionFailed);
        }
    }

    public void setUsername(String str){
        username.setText(str);
    }

    @FXML
    void initialize() {
        assert callButton != null : "fx:id=\"callButton\" was not injected: check your FXML file 'CallInPanel.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'CallInPanel.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'CallInPanel.fxml'.";


    }

}
