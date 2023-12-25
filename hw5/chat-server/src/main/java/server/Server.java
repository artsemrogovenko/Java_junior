package server;

import gui.ServerRecieve;
import gui.ServerSend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerSend {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer(){
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                AccountManager accountManager = new AccountManager(socket);
                System.out.println("Подключен новый клиент!");
                Thread thread = new Thread(accountManager);
                thread.start();
            }
        }
        catch (IOException e){
            closeSocket();
        }
    }

    private void closeSocket(){
        try{
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ServerSend getInterface(){
        return this;
    }

    @Override
    public void sendtoClient(String text) {

    }
}
