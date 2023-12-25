package client;

import gui.ClientIO;
import gui.ClientWind;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ExtendC extends Thread {
    private static ClientWind clientWindow;
    private static Account myAccount;
    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;

    private String message;

    private ExtendC get() {
        return this;
    }

//    public ClientIO getInterface() {
//        return this;
//    }

    public ExtendC() {
        clientWindow = new ClientWind(get());

        //     try {
//            socket = new Socket(clientWindow.getServerIP(), clientWindow.getServerPort());
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            String msgin = "";
//            while (!clientWindow.closed) {
//                // while (!msgin.equals("exit")) {
//                msgin = bufferedReader.readLine();
//                System.out.println(socket.getOutputStream());
//                clientWindow.resieveMsg(msgin);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

    public void init(String ip, String port) {
        try {
            socket = new Socket(String.valueOf(ip), Integer.parseInt(String.valueOf(port)));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
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
                        clientWindow.resieveMsg(message);
                    } catch (IOException e) {
                        disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * Слушатель для новых пользователей
     */
  /*  public void listenUserList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Account[] users;
                while (isConnected()) {
                    try {
                        users = (Account[]) inputStream.readObject();
                        clientWindow.updateUsers(users);
                    } catch (ClassNotFoundException | IOException e) {
                        disconnect();
                    }
                }
            }
        }).start();
    }*/

    public boolean isConnected() {
        if (socket == null) {
            return false;
        }
        return socket.isConnected();
    }


    public void disconnect() {
        closeEverything(socket, bufferedReader, bufferedWriter);
    }


    public void login(String nick, String passwd) {
        myAccount = new Account(nick, passwd);
        try {
            outputStream.writeObject(myAccount);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String str) throws IOException {
        try {
            // Scanner scanner = new Scanner(str);
            //scanner.useDelimiter("\n");
            //String input;
            if (isConnected()) {
//                do {
//                    input = scanner.nextLine();
//                } while (!scanner.hasNextLine());
                bufferedWriter.write(str);
                //bufferedWriter.newLine();
                bufferedWriter.flush();
                //message = "";

                clientWindow.resieveMsg(str);
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
       // listenUserList();
    }
}
