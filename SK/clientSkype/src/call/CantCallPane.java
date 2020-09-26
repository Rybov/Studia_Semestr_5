package call;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import startapp.Main;

import java.net.URL;
import java.util.ResourceBundle;


public class CantCallPane {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Label infoLabel;

        public void userLogout() {
            infoLabel.setText("UŻYTKOWNIK WYLOGOWANY\nSPRÓBUJ PÓŹNIEJ");
            Main.getStage().setScene(Main.getCantCallPanel());
        }

        public void userTalkingNow() {
            infoLabel.setText("UŻYTKOWNIK ZAJĘTY\nSPRÓBUJ PÓŹNIEJ");
            Main.getStage().setScene(Main.getCantCallPanel());
        }

        @FXML
        void returnToDesktop(){
            Main.getStage().setScene(Main.getMainPanel());
        }


        @FXML
        void initialize() {
            assert infoLabel != null : "fx:id=\"infoLabel\" was not injected: check your FXML file 'CantCall.fxml'.";
        }
    }

