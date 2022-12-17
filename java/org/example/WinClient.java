package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class WinClient extends Frame implements ActionListener, Runnable {
    Label msg;
    Label output;
    Label a;
    Button send;

    TextField input;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    private int player;

    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private  static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;

    WinClient() {
        JPanel mainPanel = new JPanel ();
        mainPanel.setLayout ( new BorderLayout () );
        setSize ( 800, 700 );
        setBackground ( Color.pink );
        mainPanel.add(new JButton("WelCome"), BorderLayout.NORTH);

        // add a new JButton with name "come" and it is
        // lie button of the container
        mainPanel.add(new JButton("Geeks"), BorderLayout.SOUTH);

        // add a new JButton with name "Layout" and it is
        // lie left of the container
        mainPanel.add(new JButton("Layout"), BorderLayout.EAST);
        add ( mainPanel );

        BorderLayout bors = new BorderLayout ();
        //add (bors, BorderLayout.WEST);

        msg = new Label("Color");
        msg.setBackground ( Color.BLUE );
        Label msg2 = new Label ("Color2");
        msg2.setBackground ( Color.GREEN );
        mainPanel.add ( msg,   BorderLayout.NORTH );
        mainPanel.add ( msg2, BorderLayout.SOUTH );




        setLayout(new GridBagLayout());
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));

        input = new TextField(30);
        output = new Label();
        output.setBackground(Color.white);
        send = new Button("Send");
        send.addActionListener(this);
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        this.add(new Button("Button 1"), gbc);
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        this.add(new Button("Button 2"), gbc);


        add(msg);
        add(input);
        add(send);
        add(output);
//        for (int i = 0; i <8; i++) {
//            a = new Label ( String.valueOf ( i ) );
//            a.setBackground ( Color.blue );
//            a.setBounds ( 1, 2, 3, 4 );
//            add(a);
//        }
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == send) {
            send();
        }
    }

    private void send(){
        // Wysylanie do serwera
        out.println(input.getText());
        //msg.setText("OppositeTurn");
        send.setEnabled(false);
        input.setText("");
        input.requestFocus();
        showing = ACTIVE;
        actualPlayer = player;
    }

    private void receive(){
        try {
            // Odbieranie z serwera
            String str = in.readLine();
            output.setText(str);
            //msg.setText("My turn");
            send.setEnabled(true);
            input.setText("");
            input.requestFocus();
        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}
    }

    /*
    Połaczenie z socketem
     */
    public void listenSocket() {
        try {
            socket = new Socket("localhost", 4444);
            // Inicjalizacja wysylania do serwera
            out = new PrintWriter(socket.getOutputStream(), true);
            // Inicjalizacja odbierania z serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    /*
        Poczatkowe ustawienia klienta. Ustalenie ktory socket jest ktorym kliente
    */
    private void receiveInitFromServer() {
        try {
            player = Integer.parseInt(in.readLine());
            if (player== PLAYER1) {
                msg.setText("WHITE");
            } else {
                msg.setText("BLACK");
                send.setEnabled(false);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }


    public static void main(String[] args){

        WinClient frame = new WinClient();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //frame.pack();
        frame.setVisible(true);
        //methods from application
        frame.listenSocket();
        frame.receiveInitFromServer();
        frame.startThread();
    }

    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }

    @Override
    public void run() {
       if (player==PLAYER1) {
            f1();
        }
        else{
            f2();
        }
        // Mozna zrobic w jednej metodzie. Zostawiam
        // dla potrzeb prezentacji
        // f(player);
    }


    // Jedna metoda dla kazdego Playera
    void f(int iPlayer){
        while(true) {
            synchronized (this) {
                if (actualPlayer== iPlayer) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }

    /// Metoda uruchamiana w run dla PLAYER1
    void f1(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER1) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
              if (showing ==ACTIVE){
                  receive();
                 showing =NONACTIVE;
             }
                notifyAll();
            }
        }
    }

    /// Metoda uruchamiana w run dla PLAYER2
    void f2(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER2) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }
}


