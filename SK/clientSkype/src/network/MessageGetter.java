package network;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class MessageGetter {
    private Semaphore typeLock = new Semaphore(1, true);

    Semaphore getTypeLock() {
        return typeLock;
    }

    ArrayList<String> getTypes() {
        return types;
    }

    private ArrayList<String> types;

    public MessageGetter(ArrayList<String> types) {
        this.types = types;
    }

    public Message getMessage() throws InterruptedException {
        Message m1 = MessageListener.seekMessage(this);
        if (m1 != null) return m1;

        while (true) {
            typeLock.acquire();
            MessageListener.getWaitLock().acquire();
            Message m = MessageListener.seekMessage(this);
            if (m != null) return m;
        }
    }
}