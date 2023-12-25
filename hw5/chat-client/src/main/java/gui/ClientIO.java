package gui;

import java.net.Socket;

public interface ClientIO {
    void resieveMsg(String str);
    boolean isConnected();
    void nameListUpdate(String[] data);
    void sendtoServer(String msg);
    void disconnect();
    void setMessage(String str);
}