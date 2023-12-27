package server;

import org.hibernate.Session;
import org.hibernate.query.Query;


import java.io.*;
import java.net.Socket;
import java.util.*;


public class AccountManager implements Runnable {

    private final Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private static Connector connector;
    private static Session session;

    private Account current;
    private String id;
    private String login;

    public String getLogin() {
        return login;
    }
    private Account get() {
        return current;
    }
    public String getId() {
        return id;
    }


    public final static ArrayList<AccountManager> accounts = new ArrayList<>();

    public AccountManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            login = bufferedReader.readLine();
            String passwd = bufferedReader.readLine();
            current = new Account(login, passwd);

            if (!accOnline(current)) {
                accounts.add(this);
            }

            System.out.println(login + " подключился к чату.");
            broadcastMessage("Server: " + login + " подключился к чату.");
            broadcastMessage(sendUsers());

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }


    /**
     * высылает список участников
     */
    public String sendUsers() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@users@");
        for (AccountManager user : accounts) {
            stringBuilder.append(user.getLogin() + ";");
        }
        return stringBuilder.toString();
    }


    @Override
    public void run() {
        String massageFromAccount;

        while (socket.isConnected()) {
            try {
                massageFromAccount = bufferedReader.readLine();
                /*if (massageFromaccount == null){
                    // для  macOS
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }*/
                messageParser(massageFromAccount);
                //broadcastMessage(massageFromAccount);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    /**
     * отправть личное сообщение или всем
     */
    private void messageParser(String message) {
        System.out.println(message);
        if (message.startsWith("to{") && message.contains("}from{")) {
            for (AccountManager acc : accounts) {
                if (message.contains("to{" + acc.login + "}from")) {
                    try {
                        System.out.println("высылаю pm");
                        String temp=message.substring(2 + login.length());
                        System.out.println(temp);
                        sendMessage(temp, acc);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            broadcastMessage(message);
        }
    }

    //region возможность работы с mysql

    /**
     * назначение id и защита от двойника
     */
    private boolean createAccount(Account obj) {
        //если такой пользователь онлайн, выкинуть с чата
        if (accOnline(obj)) {
            try {
                bufferedWriter.write("Двойников не принимаем");
                //bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeEverything(socket, bufferedReader, bufferedWriter);
                return false;
            }
        }

        //если нету в базе
        if (accInDB(obj) == false) {
            session = connector.getSession();
            // Начало транзакции
            session.beginTransaction();
            // Создание объекта
            session.save(obj);
            //сохранение
            session.getTransaction().commit();
            //session.close();
            System.out.println("обьект добавлен " + obj);
        }

        //session = connector.getSession();
        String hql = "FROM Account WHERE логин=:find_login пароль=:find_passwd";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("find_title", obj.getLogin());
        query.setParameter("find_passwd", obj.getPasswd());

        this.id = String.valueOf(query.getResultList());
        this.login = obj.getLogin();
        obj.setId(this.id);

        session.close();

        return true;
    }

    /**
     * назначает id через базу
     */
    private boolean accInDB(Account a) {
        boolean userInDB = false;
        session = connector.getSession();
        String hql = "FROM AccountManager WHERE логин=:find_title";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("find_title", a.getLogin());

        List<Account> list = query.getResultList();
        session.close();

        for (Account thisAccount : list) {
            if (thisAccount.getPasswd() == a.getPasswd()) {
                userInDB = true;
                break;
            }
        }
        return userInDB;
    }
//endregion

    /**
     * если онлайн такой пользователь
     */
    private boolean accOnline(Account a) {
        for (AccountManager am : accounts) {

            if (am.get().getLogin() == login) {
                return true;
            }
        }
        return false;
    }

    private void broadcastMessage(String message) {
        for (AccountManager account : accounts) {
            try {
                //if (!account.login.equals(login)) {
                    account.bufferedWriter.write(message);
                    account.bufferedWriter.newLine();
                    account.bufferedWriter.flush();
               // }
            }
            catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void sendMessage(String message, AccountManager acm) throws IOException {
        acm.bufferedWriter.write(message);
        acm.bufferedWriter.newLine();
        acm.bufferedWriter.flush();
    }


    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Удаление клиента из коллекции
        removeaccount();
        try {
            // Завершаем работу буфера на чтение данных
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            // Завершаем работу буфера для записи данных
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            // Закрытие соединения с клиентским сокетом
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeaccount() {
        accounts.remove(this);
        broadcastMessage(sendUsers());
        System.out.println(login + " покинул чат.");
        broadcastMessage("Server: " + login + " покинул чат.");
    }

}
