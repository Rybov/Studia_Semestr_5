package network;

public class Message {
private String type;
private char[] data;

    public Message(String type, char[] data) {
        this.type = type;
        this.data = data;
    }

    public Message(Message m) {
        type = m.type;
        data = m.data;
    }

    public String getType() {
        return type;
    }

    public char[] getData() {
        return data;
    }
}
