package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import startapp.Main;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginPanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ErrorMessageLabel;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField LoginLoginField;

    @FXML
    private AnchorPane LoginPane;

    @FXML
    private Button RegisterButton;

    @FXML
    private TextField RegisterLoginField;

    @FXML
    private AnchorPane RegisterPane;

    @FXML
    private PasswordField RegisterPasswordAgainField;

    @FXML
    private PasswordField RegisterPasswordField;

    @FXML
    private PasswordField LoginPasswordField;

    private ErrorDisplay ems;

    private void clearScene(){
        LoginLoginField.clear();
        RegisterLoginField.clear();
        RegisterPasswordAgainField.clear();
        RegisterPasswordField.clear();
        LoginPasswordField.clear();
    }

    private void runUserProfile(String name){
        //start friendList listener
        Main.setName(name);
        Main.getMainController().startListenFriendLists();
        Main.getStage().setScene(Main.getMainPanel());
        clearScene();
    }

    @FXML
    void loginAction(ActionEvent event) {
        if((LoginLoginField.getText().equals(""))||(LoginPasswordField.getText().equals(""))){
            ems.setString("LOGIN ANI HASŁO NIE MOGĄ BYĆ PUSTE");
            ems.displayErrorMessage();
        }
        else{
            LoginService service = new LoginService(LoginLoginField.getText(),LoginPasswordField.getText());
            service.setOnSucceeded(t -> {
                if(service.getValue()) {
                    runUserProfile(service.getLogin());
                }
                else {
                    ems.setString("NIEPOPRAWNY LOGIN LUB HASŁO");
                    ems.displayErrorMessage();
                }
            });
            service.setOnFailed(t -> {
                ems.setString("WYSTĄPIŁ BŁĄD PRZY PRÓBIE REJESTRACJI");
                ems.displayErrorMessage();});
            service.start();
        }
    }

    @FXML
    void registerAction(ActionEvent event) {
        if((RegisterPasswordField.getText().equals(""))||(RegisterLoginField.getText().equals(""))){
            ems.setString("LOGIN ANI HASŁO NIE MOGĄ BYĆ PUSTE");
            ems.displayErrorMessage();
        }
        else if(!RegisterPasswordField.getText().equals(RegisterPasswordAgainField.getText())){
            ems.setString("PODANE HASŁA SIĘ RÓŻNIĄ");
            ems.displayErrorMessage();
        }
        else{
            RegisterService service = new RegisterService(RegisterLoginField.getText(),RegisterPasswordField.getText());
            service.setOnSucceeded(t -> {
                if(service.getValue()) {
                    runUserProfile(service.getLogin());
                }
                else {
                    ems.setString("TEN LOGIN JEST JUŻ ZAJĘTY");
                    ems.displayErrorMessage();
                }
            });
            service.setOnFailed(t -> {ems.setString("WYSTĄPIŁ BŁĄD PRZY PRÓBIE REJESTRACJI");ems.displayErrorMessage();});
            service.start();
        }
    }

    @FXML
    void initialize() {
        assert ErrorMessageLabel != null : "fx:id=\"ErrorMessageLabel\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert LoginLoginField != null : "fx:id=\"LoginLoginField\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert LoginPane != null : "fx:id=\"LoginPane\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert LoginPasswordField != null : "fx:id=\"LoginPasswordField\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert RegisterButton != null : "fx:id=\"RegisterButton\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert RegisterLoginField != null : "fx:id=\"RegisterLoginField\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert RegisterPane != null : "fx:id=\"RegisterPane\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert RegisterPasswordAgainField != null : "fx:id=\"RegisterPasswordAgainField\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        assert RegisterPasswordField != null : "fx:id=\"RegisterPasswordField\" was not injected: check your FXML file 'LoginPanel.fxml'.";
        ems = new ErrorDisplay(ErrorMessageLabel);

    }

}
