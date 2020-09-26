package desktop;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import network.Message;
import network.MessageSender;
import startapp.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class MainPanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addOrDeleteNotFriend;

    @FXML
    private Button callButton;

    @FXML
    private Button callFriendButton;

    @FXML
    private Button deleteFromFriends;

    @FXML
    private AnchorPane findUserPane;

    @FXML
    private AnchorPane foundUserPane;

    @FXML
    private ListView<String> friendList;

    @FXML
    private Label friendName;

    @FXML
    private AnchorPane friendPane;

    @FXML
    private TextField friendsSearchField;

    @FXML
    private Button menuLogoutButton;

    @FXML
    private Button menuSearchButton;

    @FXML
    private TextField notFriendSearchField;

    @FXML
    private Label username;

    private Boolean addOrDelete = true;

    private FriendListLoadService fls;

    public void stopListenFriendList(){
        if(fls != null)
            fls.setRunning(false);
    }

    private void searchPaneDefault(){
        notFriendSearchField.setStyle("-fx-text-inner-color: black;");
        notFriendSearchField.clear();
        foundUserPane.setVisible(false);
    }

    @FXML
    void callFromList(ActionEvent event) {
        call(friendList.getSelectionModel().getSelectedItem());
    }

    @FXML
    void callFromSearch(ActionEvent event) {
        call(username.getText());
    }

    @FXML
    void searchInList(ActionEvent event) {
        friendList.getItems().removeAll();
        friendList.getItems().sort((o1,o2)->{
            String regex = "^"+ Pattern.quote(friendsSearchField.getText().toLowerCase())+".*";
            o1 = o1.toLowerCase();
            o2 = o2.toLowerCase();
            if(o1.matches(regex)){
                if((o2.matches(regex))&&(o1.compareTo(o2)>0))
                    return 1;
                else
                    return 0;
            }
            else{
                if(!(o2.matches(regex))&&(o1.compareTo(o2)<0))
                    return 0;
                else
                    return 1;
            }
        });
    }

    @FXML
    void sendSearchRequest(ActionEvent event) {
        SearchService service = new SearchService(notFriendSearchField.getText());
        service.setOnSucceeded(t -> {
            if(service.getValue()) {
                foundUserPane.setVisible(true);
                username.setText(service.getName());
                if(friendList.getItems().contains(service.getName())) {
                    addOrDeleteNotFriend.setText("Usuń z kontaktów");
                    addOrDelete = false;
                }
                else {
                    addOrDeleteNotFriend.setText("Dodaj do kontaktów");
                    addOrDelete = true;
                }
            }
            else {
                foundUserPane.setVisible(false);
                notFriendSearchField.setStyle("-fx-text-inner-color: red;");
            }
        });
        service.setOnFailed(t -> {
            foundUserPane.setVisible(false);
            notFriendSearchField.setStyle("-fx-text-inner-color: red;");});
        service.start();
    }

    @FXML
    void addOrDeleteFriend(ActionEvent event) {
        Runnable deleteFriend = () -> {
            Message forwardMessage = new Message("FD", friendName.getText().toCharArray());
            MessageSender ms = new MessageSender();
            ms.send(forwardMessage);

        };
        Thread t1 = new Thread(deleteFriend);
        t1.start();
        outClicked(null);
    }

    @FXML
    void selectUser(Event event) {
        String s = friendList.getSelectionModel().getSelectedItem();
        if (s==null){
            friendPane.setVisible(false);
        }
        else{
            friendName.setText(s);
            friendPane.setVisible(true);
            findUserPane.setVisible(false);
        }
    }

    @FXML
    void addOrDeleteSearch(ActionEvent event) {
        if(addOrDelete){
            Runnable addFriend = () -> {
                Message forwardMessage = new Message("FA", username.getText().toCharArray());
                MessageSender ms = new MessageSender();
                    ms.send(forwardMessage);
            };
            Thread t1 = new Thread(addFriend);
            t1.start();
            friendList.getItems().add(0,username.getText());
            addOrDelete = false;
            addOrDeleteNotFriend.setText("Usuń z kontaktów");
        }
        else {
            Runnable deleteFriend = () -> {
                Message forwardMessage = new Message("FD", username.getText().toCharArray());
                MessageSender ms = new MessageSender();
                ms.send(forwardMessage);
            };
            Thread t1 = new Thread(deleteFriend);
            t1.start();
            addOrDelete = true;
            addOrDeleteNotFriend.setText("Dodaj do kontaktów");
        }

    }

    @FXML
    void outClicked(MouseEvent event) {
        friendList.getSelectionModel().clearSelection();
        selectUser(null);
    }


    @FXML
    void call(String name) {
        Main.getCallOutController().call(name);
    }

    @FXML
    void logout(ActionEvent event) {
        clearScene();
        Stage stage = (Stage) username.getScene().getWindow();
        stage.setScene(Main.getLoginPanel());
        stopListenFriendList();
        MessageSender ms = new MessageSender();
        ms.send(new Message("LO","".toCharArray()));
    }

    @FXML
    void searchfriendsViewSet(ActionEvent event) {
        if(findUserPane.isVisible()){
            findUserPane.setVisible(false);
        }
        else{
            findUserPane.setVisible(true);
            outClicked(null);
            searchPaneDefault();
        }
    }

    public void startListenFriendLists(){
        fls = new FriendListLoadService(friendList);
        fls.setRunning(true);
        Thread t1 = new Thread(fls);
        t1.start();
    }

    private void clearScene(){
        outClicked(null);
        friendList.getItems().removeAll();
        friendsSearchField.clear();
        notFriendSearchField.clear();
        findUserPane.setVisible(false);
    }

    @FXML
    void initialize() {
        assert friendList != null : "fx:id=\"friendList\" was not injected: check your FXML file 'MainPanel.fxml'.";
        assert friendsSearchField != null : "fx:id=\"friendsSearchField\" was not injected: check your FXML file 'MainPanel.fxml'.";
        assert menuLogoutButton != null : "fx:id=\"menuLogoutButton\" was not injected: check your FXML file 'MainPanel.fxml'.";
        assert menuSearchButton != null : "fx:id=\"menuSearchButton\" was not injected: check your FXML file 'MainPanel.fxml'.";
        assert notFriendSearchField != null : "fx:id=\"notFriendSearchField\" was not injected: check your FXML file 'MainPanel.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'MainPanel.fxml'.";

        friendsSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchInList(null);
        });

        notFriendSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            notFriendSearchField.setStyle("-fx-text-inner-color: black;");
        });

    }
}
