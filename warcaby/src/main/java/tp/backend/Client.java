package tp.backend;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import tp.frontend.GUIstart;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Client  {
  //TO DO: Add instance of GUI class

  private static final int WHITE=1;
  private static final int BLACK=2;

  private static final int PIECE=0;
  private static final int KING=1;

  private static final int ACTIVE=0;
  private static final int NONACTIVE=1;

  private static int showing = NONACTIVE;

  private static int playerId; // ID that Server sends for identification
  private static int oponnentId;
  private static int currPlayer;
  private static CoderDecoder CD;
  private static gameStatusClass gameStatus;

  //TO DO: Move this to GUI Class
  private static PrintWriter out = null;
  private static BufferedReader in = null;

  Client() {
    CD = new CoderDecoder();
  }

  public void boardInitFill() {
    for(int i=1; i<=8; i++) {
      for(int j=1; j<=8; j++) {
        gameStatus.board[i][j] = "";
      }
    }
  }

  public void listenSocket() {
    try {
      Socket socket = new Socket("localhost", 4444);

      // TO DO: Move this section to individual method in GUI class
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  private void receiveInitInfo() {
    try {
      //Receive from Server player's index
      playerId = Integer.parseInt(in.readLine());
      currPlayer = Integer.parseInt(in.readLine()); //Receive from Server who starts the game

      if(playerId == WHITE)
        oponnentId = BLACK;
      else
        oponnentId = WHITE;

      //TO DO: Change Label in GUI to show whose turn is now
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  private static void send(ICommand command) { // PREV: nonstatic
    // code command using CoderDecoder
    String commandString = CD.codeCommand(command);
    //Send string to Server
    out.println(commandString);

    showing = ACTIVE;
    currPlayer = oponnentId; // PREV: oponnentId
  }

  private static void receive() { // PREV: nonstatic
    try {
      String commandString = in.readLine();
      System.out.println("CommandString: "+commandString);
      ICommand command =  CD.decodeCommand(commandString, "gameStatus");
      gameStatus = (gameStatusClass) command;
      System.out.println("GameStatus View:" + gameStatus.showView());

    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }


  public static void main(String[] main) throws FileNotFoundException {

    Client player = new Client();

    player.listenSocket();
    player.receiveInitInfo();

    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);

    receive();

    gameCommandClass moveCommand = new gameCommandClass();
    moveCommand.setActorId(playerId);
    moveCommand.setPieceId(PIECE);

//    positionChanges.add ( "pos56" );
//    positionChanges.add ( "pos74" );


    Application.launch ( tp.frontend.GUIstart.class);
//
//
//    moveCommand.fromX=6;
//    moveCommand.fromY=3;
//    moveCommand.toX=5;
//    moveCommand.toY=2;
//
//    send(moveCommand);
//    receive();

  }
}

//package tp.backend;
//
//import java.net.*;
//import java.io.*;
//
//public class Client {
//  //TO DO: Add instance of GUI class
//
//  private static final int WHITE=1;
//  private static final int BLACK=2;
//
//  private static final int PIECE=0;
//  private static final int KING=1;
//
//  private static final int ACTIVE=0;
//  private static final int NONACTIVE=1;
//
//  private static int showing = NONACTIVE;
//
//  private static int playerId; // ID that Server sends for identification
//  private static int oponnentId;
//  private static int currPlayer;
//  private static CoderDecoder CD;
//  private static gameStatusClass gameStatus;
//
//  //TO DO: Move this to GUI Class
//  private static PrintWriter out = null;
//  private static BufferedReader in = null;
//
//  Client() {
//    CD = new CoderDecoder();
//  }
//
//  public void boardInitFill() {
//    for(int i=1; i<=8; i++) {
//      for(int j=1; j<=8; j++) {
//        gameStatus.board[i][j] = "";
//      }
//    }
//  }
//
//  public void listenSocket() {
//    try {
//      Socket socket = new Socket("localhost", 4444);
//
//      // TO DO: Move this section to individual method in GUI class
//      out = new PrintWriter(socket.getOutputStream(), true);
//      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//    } catch(IOException e) {
//      System.out.println(e.getMessage());
//      System.exit(1);
//    }
//  }
//
//  private void receiveInitInfo() {
//    try {
//      //Receive from Server player's index
//      playerId = Integer.parseInt(in.readLine());
//      currPlayer = Integer.parseInt(in.readLine()); //Receive from Server who starts the game
//
//      if(playerId == WHITE)
//        oponnentId = BLACK;
//      else
//        oponnentId = WHITE;
//
//      //TO DO: Change Label in GUI to show whose turn is now
//    } catch(IOException e) {
//      System.out.println(e.getMessage());
//      System.exit(1);
//    }
//  }
//
//  private static void send(ICommand command) {
//    // code command using CoderDecoder
//    String commandString = CD.codeCommand(command);
//    System.out.println("CLIENT sends: " + commandString);
//
//    //Send string to Server
//    out.println(commandString);
//
//    showing = ACTIVE;
//  }
//
//  private static void receive() {
//     try {
//      String commandString = in.readLine();
//      System.out.println("CLIENT receives: "+commandString);
//      ICommand command =  CD.decodeCommand(commandString, "gameStatus");
//      gameStatus = (gameStatusClass) command;
//      System.out.println("GameStatus View:" + gameStatus.showView());
//
//      // Display error message
//      if(gameStatus.getError() != "" && Integer.parseInt(gameStatus.getTurn()) == playerId) {
//        System.out.println(gameStatus.getError());
//      }
//
//      //Update currPlayer based on gameStatus
//      currPlayer = Integer.parseInt(gameStatus.turn);
//
//     } catch(IOException e) {
//      System.out.println(e.getMessage());
//     }
//  }
//
//  public static void main(String[] main) {
//    Client player = new Client();
//    Position tmp = new Position();
//
//    player.listenSocket();
//    player.receiveInitInfo();
//
//    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);
//
//    receive();
//
//    ClientManager CM = new ClientManager(playerId);
//    gameCommandClass command;
//
//    while(true) {
//      if(currPlayer == playerId) {
//        command = (gameCommandClass) CM.start();
//        send(command);
//      }
//      receive();
//    }
//  }
//}
