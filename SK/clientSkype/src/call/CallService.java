package call;

import javafx.application.Platform;
import javafx.stage.Stage;
import network.*;
import sound.SoundLines;
import startapp.Main;
import videoconference.OutputVideoService;

import java.io.IOException;
import java.util.ArrayList;

public class CallService implements Runnable {

    protected enum State {
        no, wait, yes;
    }

    protected enum InOut {
        in, out;
    }

    private State state;
    private InOut inOut;

    public CallService(int size, int width, int height) {
        this.size = size;
        this.width = width;
        this.height = height;
        state = State.no;
    }

    private int size;
    private int width;
    private int height;
    private int friendSize;
    private int friendWidth;
    private int friendHeight;
    private int friendSoundSize;

    private String friendName;

    public void callOut(String friendName) {
        try {
            NetworkConnection.setTransmissionConnection();
            Message message = new Message("W",Main.getName().toCharArray());
            InitSender initSender = new InitSender();
            initSender.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.friendName = friendName;
        Message forwardMessage = new Message("C", (friendName + "\n" + size + "\n" + width + "\n" + height + "\n" + SoundLines.getPackageSize()).toCharArray());
        MessageSender ms = new MessageSender();
        ms.send(forwardMessage);
        state = State.wait;
        inOut = InOut.out;
    }

    private void startTransmission() throws IOException {
        FrameListener.setExpectedSize(friendSize, friendWidth, friendHeight, friendSoundSize);
        FrameListener.stableState();
        Main.getVideoController().start();
        state = State.yes;
    }

    public void answer(Boolean answer) throws IOException {
        if (answer) {
            Message forwardMessage = new Message("Y", (size + "\n" + width + "\n" + height + "\n" + SoundLines.getPackageSize()).toCharArray());
            MessageSender ms = new MessageSender();
            ms.send(forwardMessage);
            startTransmission();
        } else {
            Message forwardMessage = new Message("N", "".toCharArray());
            MessageSender ms = new MessageSender();
            ms.send(forwardMessage);
            state = State.no;
        }
    }

    public void stopCall() {
        SoundLines.closeLines();
        try {
            NetworkConnection.closeTransmissionConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message forwardMessage = new Message("E", "".toCharArray());
        MessageSender ms = new MessageSender();
        ms.send(forwardMessage);
        Platform.runLater(() -> Main.getStage().setScene(Main.getMainPanel()));
        state = State.no;
    }

    private void callIn(Message message) {
        String str = new String(message.getData());
        String[] data = str.split("\n");
        friendName = data[0];
        friendSize = Integer.parseInt(data[1].trim());
        friendWidth = Integer.parseInt(data[2].trim());
        friendHeight = Integer.parseInt(data[3].trim());
        friendSoundSize = Integer.parseInt(data[4].trim());
        state = State.wait;
        inOut = InOut.in;
        Platform.runLater(() -> {
            Main.getCallInController().setUsername(friendName);
            Stage stage = Main.getStage();
            stage.setScene(Main.getCallInPanel());
        });
    }

    private void startCall(Message message) throws IOException {
        String measures = new String(message.getData());
        String[] wh = measures.split("\n");
        if (wh.length == 4) {
            try {
                friendSize = Integer.parseInt(wh[0].trim());
                friendWidth = Integer.parseInt(wh[1].trim());
                friendHeight = Integer.parseInt(wh[2].trim());
                friendSoundSize = Integer.parseInt(wh[3].trim());
            } catch (NumberFormatException nfe) {
                throw new IOException();
            }
        } else {
            throw new IOException();
        }
        Platform.runLater(() -> {
            Stage stage = Main.getStage();
            stage.setScene(Main.getVideoPanel());
        });
        startTransmission();
    }

    public void canceledCall() throws IOException {
        if(OutputVideoService.getTimer()!=null){ OutputVideoService.getTimer().shutdown(); }
        Main.getVideoController().setStopped(true);
        SoundLines.closeLines();
        NetworkConnection.closeTransmissionConnection();
        Platform.runLater(() -> Main.getStage().setScene(Main.getMainPanel()));
        state = State.no;
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<String> types = new ArrayList<>();
            types.add("N");
            types.add("Y");
            types.add("C");
            types.add("E");
            types.add("IT");
            types.add("ILO");
            try {
                MessageGetter messageGetter = new MessageGetter(types);
                Message message = messageGetter.getMessage();
                if (state == State.no) {
                    if (message.getType().equals("C")) {
                        callIn(message);
                    }
                } else if (state == State.wait) {
                    if (inOut == InOut.in) {
                        if (message.getType().equals("E")) {
                            canceledCall();
                        }
                    } else if (inOut == InOut.out) {
                        if (message.getType().equals("Y")) {
                            startCall(message);
                        } else if (message.getType().equals("N")) {
                            canceledCall();
                        } else if (message.getType().equals("IT")) {
                            Platform.runLater(Main.getCantCallController()::userTalkingNow);
                        } else if (message.getType().equals("ILO")) {
                            Platform.runLater(Main.getCantCallController()::userLogout);
                        }
                    }
                } else if (state == State.yes) {
                    if (message.getType().equals("E")) {
                        canceledCall();
                    }
                }
            } catch (InterruptedException | IOException e) {
                Platform.runLater(Main::connectionFailed);
            }
        }
    }
}
