package server;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//@Entity
//@Table(name = "accounts")
public class AccountManager implements Runnable {

    private final Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private String id;
    private String login;

    public String getId() {
        return id;
    }


    public final static LinkedList<AccountManager> accounts = new LinkedList<>();

    public AccountManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            //login = bufferedReader.readLine();
            try {
                Account recieve = (Account) inputStream.readObject();
                createAccount(recieve);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("jija");
            }

            //accounts.add(this);
            System.out.println(login + " подключился к чату.");
            broadcastMessage("Server: " + login + " подключился к чату.");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String massageFromAccount;

        while (socket.isConnected()) {
            try {
                massageFromAccount = bufferedReader.readLine();
                System.out.println(massageFromAccount);
                /*if (massageFromaccount == null){
                    // для  macOS
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }*/
                broadcastMessage(massageFromAccount);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    private static Connector connector;
    private static Session session;

    /**
     * назначение id и защита от двойника
     */
    public void createAccount(Account obj) {
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
        session.close();
        //если такой пользователь онлайн, выкинуть с чата
        if (accOnline(obj)){
            try {
                bufferedWriter.write("Двойников не принимаем");
               // bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                closeEverything( socket,  bufferedReader,  bufferedWriter);
            }
        }

        obj.setId(this.id);


    }

    public boolean accInDB(Account a) {
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

    public boolean accOnline(Account a) {
        for (AccountManager acc : accounts) {
            if (acc.id == a.getId() && acc.login == a.getLogin()) {
                return true;
            }
        }
        return false;
    }

    private void broadcastMessage(String message) {
        for (AccountManager account : accounts) {
            try {
                if (!account.login.equals(login)) {
                    account.bufferedWriter.write(message);
                    //account.bufferedWriter.newLine();
                    account.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
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
        System.out.println(login + " покинул чат.");
        broadcastMessage("Server: " + login + " покинул чат.");
    }

}
