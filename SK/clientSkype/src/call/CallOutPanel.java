package call;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import startapp.Main;

import java.net.URL;
import java.util.ResourceBundle;


public class CallOutPanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label name;

    @FXML
    void goBack(ActionEvent event) {
        Main.getCallService().stopCall();
    }

    public void call(String str){
        name.setText(str);
        Main.getCallService().callOut(str);
        Main.getStage().setScene(Main.getCallOutPanel());
    }

    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'CallOutPanel.fxml'.";

    }

}
