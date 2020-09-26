package desktop;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import network.Message;
import network.MessageGetter;
import startapp.Main;

import java.util.ArrayList;

public class FriendListLoadService implements Runnable {

    private ListView<String> friendList;
    private ObservableList<String> names;

    public void setRunning(Boolean running) {
        this.running = running;
    }

    private Boolean running = true;

    public FriendListLoadService(ListView<String> friendList) {
        this.friendList = friendList;
    }

    private void decodeReply(char[] data){
        String str = new String(data);
        if(str.equals("")){
            names = FXCollections.observableArrayList();
        }
        else {
            String[] userNames = str.split("\n");
            names = FXCollections.observableArrayList(userNames);
        }
        Platform.runLater(() -> {
            friendList.setItems(names);
            Main.getMainController().searchInList(null);
        });
    }

    @Override
    public void run() {
        while (running){
            Message message;
            try {
                ArrayList<String> types = new ArrayList<>();
                types.add("F");
                MessageGetter messageGetter = new MessageGetter(types);
                message = messageGetter.getMessage();
                decodeReply(message.getData());
            } catch (InterruptedException e) {
                Main.connectionFailed();
                return;
            }
        }
    }
}

