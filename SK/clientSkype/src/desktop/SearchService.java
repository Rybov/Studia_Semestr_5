package desktop;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import network.Message;
import network.MessageGetter;
import network.MessageSender;
import startapp.Main;

import java.io.IOException;
import java.util.ArrayList;

public class SearchService extends Service<Boolean> {

    private String name;

    public String getName() {
        return name;
    }

    public SearchService(String name) {
        this.name = name;
    }

    private Boolean decodeReply(char[] data) throws IOException {
        if(data.length == 1){
            if(data[0] == '1')
                return true;
            if(data[0] == '0')
                return false;
        }
        throw new IOException();
    }

    @Override
    protected synchronized Task<Boolean> createTask(){
            return new Task<>() {
            @Override
            protected Boolean call(){
                while(true){
                    try {
                        Message forwardMessage = new Message("S",name.toCharArray());
                        MessageSender ms = new MessageSender();
                        ms.send(forwardMessage);

                        Message message;
                        try {
                            ArrayList<String> types = new ArrayList<>();
                            types.add("S");
                            MessageGetter messageGetter = new MessageGetter(types);
                            message = messageGetter.getMessage();
                            return decodeReply(message.getData());
                        } catch (InterruptedException e) {
                            Platform.runLater(Main::connectionFailed);
                        }
                    } catch (IOException e) {
                        this.getOnFailed();
                        return false;
                    }
                }
            }
        };
    }
}