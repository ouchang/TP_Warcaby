package org.example.GUI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class GUI_frame extends Application implements ActionListener {
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
    private static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;

//    GUI_frame() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader ( GUI_frame.class.getResource ( "gui_xml.fxml" ) );
//        Scene scene = new Scene ( fxmlLoader.load (), 320, 240 );
//        stage.setScene ( scene );
//        stage.show ();
////        JPanel mainPanel = new JPanel ();
//        mainPanel.setLayout ( new BorderLayout () );
//        setSize ( 800, 600 );
//        setBackground ( Color.pink );
//        JButton welcome = new JButton ("Welcome");
//        mainPanel.add ( welcome, BorderLayout.NORTH );
//
//        // add a new JButton with name "come" and it is
//        // lie button of the container
//        mainPanel.add ( new JButton ( "Geeks" ), BorderLayout.SOUTH );
//
//        // add a new JButton with name "Layout" and it is
//        // lie left of the container
//        mainPanel.add ( new JButton ( "Layout" ), BorderLayout.EAST );
//        add ( mainPanel );


    //}


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader ( GUI_frame.class.getResource ( "gui_xml.fxml" ) );
        Scene scene = new Scene ( fxmlLoader.load (), 800, 600 );
        stage.setTitle ( "CHECKERS" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ();
    }

    public static void main(String[] args){

       launch (  );
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
