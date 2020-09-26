package login;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import network.Message;
import network.MessageGetter;
import network.MessageSender;
import startapp.Main;

import java.io.IOException;
import java.util.ArrayList;

public class LoginService extends Service<Boolean> {

    public String getLogin() {
        return login;
    }

    private String login, password;

    public LoginService(String login, String password) {
        this.login = login;
        this.password = password;
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
                        Message forwardMessage = new Message("L",(login + "\n" + password).toCharArray());
                        MessageSender ms = new MessageSender();
                        ms.send(forwardMessage);

                        Message message = null;
                        try {
                            ArrayList<String> types = new ArrayList<>();
                            types.add("L");
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
