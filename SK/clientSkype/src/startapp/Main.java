package startapp;

import call.CallInPanel;
import call.CallOutPanel;
import call.CallService;
import call.CantCallPane;
import desktop.MainPanel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginPanel;
import network.MessageListener;
import org.opencv.core.Core;
import serverConnection.ServerPanel;
import sound.SoundLines;
import videoconference.OutputVideoService;
import videoconference.VideoconferencePanel;

public class Main extends Application {

    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;

    public static Scene getCallOutPanel() {
        return callOutPanel;
    }

    public static Scene getLoginPanel() {
        return loginPanel;
    }

    public static Scene getServerPanel() {
        return serverPanel;
    }

    public static Scene getMainPanel() {
        return mainPanel;
    }

    public static Scene getVideoPanel() {
        return videoPanel;
    }

    public static Scene getCallInPanel() {
        return callInPanel;
    }

    public static Scene getCantCallPanel() {
        return cantCallPanel;
    }

    private static Scene loginPanel;
    private static Scene serverPanel;
    private static Scene mainPanel;
    private static Scene videoPanel;
    private static Scene callInPanel;
    private static Scene callOutPanel;

    private static Scene cantCallPanel;

    private static LoginPanel loginController;

    private static ServerPanel serverController;

    private static MainPanel mainController;

    private static VideoconferencePanel videoController;

    private static CallOutPanel callOutController;

    private static CallInPanel callInController;

    public static CantCallPane getCantCallController() {
        return cantCallController;
    }

    private static CantCallPane cantCallController;

    public static LoginPanel getLoginController() {
        return loginController;
    }

    public static ServerPanel getServerController() {
        return serverController;
    }

    public static MainPanel getMainController() { return mainController; }

    public static VideoconferencePanel getVideoController() {
        return videoController;
    }

    public static CallOutPanel getCallOutController() {
        return callOutController;
    }

    public static CallInPanel getCallInController() {
        return callInController;
    }

    public static CallService getCallService() {
        return callService;
    }

    public static void setCallService(CallService callService) {
        Main.callService = callService;
    }

    private static CallService callService;

    public static void setMessageListener(MessageListener messageListener) {
        Main.messageListener = messageListener;
    }

    public static MessageListener getMessageListener() {
        return messageListener;
    }

    private static MessageListener messageListener;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Main.name = name;
    }

    private static String name;

    public static void connectionFailed(){
        messageListener = null;
        callService = null;
        if((OutputVideoService.getTimer()!=null)&&(!OutputVideoService.getTimer().isShutdown())){
            OutputVideoService.getTimer().shutdown();
        }
        SoundLines.closeLines();
        serverController.lostServerConnection();
        mainController.stopListenFriendList();
        stage.setScene(serverPanel);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });

        stage = primaryStage;
        //load loginPanel
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../login/LoginPanel.fxml"));
        Parent parent = fxmlLoader.load();
        loginPanel = new Scene(parent,600, 400);
        loginController = fxmlLoader.getController();

        //load serverPanel
        fxmlLoader = new FXMLLoader(getClass().getResource("../serverConnection/ServerPanel.fxml"));
        parent = fxmlLoader.load();
        serverPanel = new Scene(parent,600, 400);
        serverController = fxmlLoader.getController();

        //load mainPanel
        fxmlLoader = new FXMLLoader(getClass().getResource("../desktop/MainPanel.fxml"));
        parent = fxmlLoader.load();
        mainPanel = new Scene(parent,600, 400);
        mainController = fxmlLoader.getController();

        //loadVideoPanel
        fxmlLoader = new FXMLLoader(getClass().getResource("../videoconference/VideoconferencePanel.fxml"));
        parent = fxmlLoader.load();
        videoPanel = new Scene(parent,600, 400);
        videoController = fxmlLoader.getController();

        //loadCallInPanel
        fxmlLoader = new FXMLLoader(getClass().getResource("../call/CallInPanel.fxml"));
        parent = fxmlLoader.load();
        callInPanel = new Scene(parent,600, 400);
        callInController = fxmlLoader.getController();

        //loadCallOutPanel
        fxmlLoader = new FXMLLoader(getClass().getResource("../call/CallOutPanel.fxml"));
        parent = fxmlLoader.load();
        callOutPanel = new Scene(parent,600, 400);
        callOutController = fxmlLoader.getController();

        //loadCantCallPanel
        fxmlLoader = new FXMLLoader(getClass().getResource("../call/CantCall.fxml"));
        parent = fxmlLoader.load();
        cantCallPanel = new Scene(parent,600, 400);
        cantCallController = fxmlLoader.getController();

        primaryStage.setTitle("Epyx");
        primaryStage.setScene(serverPanel);
        primaryStage.show();
    }


    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new SoundLines();
        launch(args);
    }
}
