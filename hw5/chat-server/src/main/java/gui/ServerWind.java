package gui;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Toolkit;

import server.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class ServerWind implements ServerRecieve {
    private static Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    public boolean isWorked = false;
    public boolean closed = false;

    private static JFrame frame = new JFrame("Chat Server");
    private static JButton serverStart = new JButton("Start");
    private static JButton serverStop = new JButton("Stop");

    private static JTextField sendText = new JTextField();
    private static JButton send = new JButton("send");

    private static JPanel p = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    private static int a = (int) (sSize.getHeight() / 2) - HEIGHT / 2;
    private static int b = (int) (sSize.getWidth() / 2) - WIDTH / 2;
    /*
     * Добавить на окно компонент JtextArea и выводить
     * сообщения сервера в него, а не в терминал.
     */
    JTextArea systemMessages = new JTextArea(4, 1);
    JScrollPane scrollPane = new JScrollPane(systemMessages,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    /*
     * Создать простейшее окно управления сервером (по сути, любым),
     * содержащее две кнопки (JButton) – запустить сервер и остановить сервер.
     * Кнопки должны просто логировать нажатие (имитировать запуск и
     * остановку сервера, соответственно) и выставлять внутри интерфейса
     * соответствующее булево isServerWorking.
     */
    public ServerRecieve getIntrf() {
        return this;
    }

    private void fill() {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        // c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        p.add(serverStart, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        // c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 0;
        p.add(serverStop, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        p.add(scrollPane, c);

        systemMessages.setEditable(false);
    }

    private ServerSend serverSend;

    public ServerWind(Server newServer) {
        this.serverSend = newServer.getInterface();
        fill();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(b, a, WIDTH, HEIGHT);
        frame.add(p, BorderLayout.NORTH);
        frame.add(sendText, BorderLayout.CENTER);
        frame.add(send, BorderLayout.EAST);
        frame.setVisible(true);

        /*
         * Задача: Если сервер не запущен, кнопка остановки должна сообщить, что
         * сервер не запущен и более ничего не делать. Если сервер запущен, кнопка
         * старта должна сообщить, что сервер работает и более ничего не делать.
         */
        serverStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWorked) {
                    System.out.println("Server is already working");
                    systemMessages.append("Server is already working" + "\n");
                } else {
                    isWorked = true;
                    System.out.println("Server working status is " + isWorked);
                    systemMessages.append("Server working status is " + isWorked + "\n");
                }
            }
        });
        serverStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isWorked) {
                    System.out.println("A server is already stoped ");
                    systemMessages.append("A server is already stoped" + "\n");
                } else {
                    isWorked = false;
                    System.out.println("Server working status is " + isWorked);
                    systemMessages.append("Server working status is " + isWorked + "\n");
                }
            }
        });

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendString();
            }
        });

        sendText.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    sendString();
                }
                if (e.getKeyCode() == 27) {
                    sendText.setText(null);
                }
            }

            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {            }
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {            }

        });

        systemMessages.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                systemMessages.getCaret().setDot(Integer.MAX_VALUE);
            }
        });
    }

    @Override
    public void getmessage(String s) {
        systemMessages.append(s+"\n");
        systemMessages.setCaretPosition(systemMessages.getDocument().getLength());
    }

    public boolean getStatus() {
        return isWorked;
    }

    private void sendString() {
        if (!sendText.getText().equals("")) {
            //String out = Logger.getTime() + "Server : " + sendText.getText();// + "\n";
            String out = "Server : " + sendText.getText();// + "\n";
            serverSend.sendtoClient(out);
            //systemMessages.append(out);
            sendText.setText(null);
            systemMessages.setCaretPosition(systemMessages.getDocument().getLength());
        }
    }

}