package client;

import gui.ClientWind;


import java.io.*;
import java.net.Socket;


public class ExtendC extends Thread {
    private static ClientWind clientWindow;

    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;

    private String message;

    private ExtendC get() {
        return this;
    }


    public ExtendC() {
        clientWindow = new ClientWind(get());
    }

    public void init(String ip, String port) {
        try {
            socket = new Socket(String.valueOf(ip), Integer.parseInt(String.valueOf(port)));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            clientWindow.resieveMsg(e.getMessage());
        }

    }


    /**
     * Слушатель для входящих сообщений
     */
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        if (message.startsWith("@users@")) {
                            parseUsers(message);
                        } else
                        if (message.startsWith("from{")) {
                            parsePrivate(message);
                        } else
                            clientWindow.resieveMsg(message);
                    } catch (IOException e) {
                        disconnect();
                    }
                }
            }
        }).start();
    }


    private void parseUsers(String data) {
        String temp = data.substring(7);
        String[] list = temp.split(";");
        clientWindow.updateUsers(list);
    }

    private void parsePrivate(String data) {
        System.out.println("принято сообщение "+data);
        String temp = data.substring(5);
        clientWindow.resieveMsg(temp.replaceFirst("}"," :"));
    }

    public boolean isConnected() {
        if (socket == null) {
            return false;
        }
        return socket.isConnected();
    }


    public void disconnect() {
        closeEverything(socket, bufferedReader, bufferedWriter);
        this.notify();
    }


    public void login(String nick, String passwd) {
        // myAccount = new Account(nick, passwd);
        try {
            sendMessage(nick);
            sendMessage(passwd);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendMessage(String str) throws IOException {
        try {
            if (isConnected()) {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (IOException e) {
            disconnect();

        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        listenForMessage();
        //listenUserList();
    }
}
