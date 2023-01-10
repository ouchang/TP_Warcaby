package tp.backend;

import java.io.*;
import java.net.Socket;

import java.util.List;
import java.util.ArrayList;

public class Client  {
  //TO DO: Add instance of GUI class
  public static volatile int gametype = 0;
  private static final int WHITE=1;
  private static final int BLACK=2;

  private static final int PIECE=0;
  private static final int KING=1;

  // Figure's ID
  private static final String EMPTY="0";
  private static final String WH_PIECE="1";
  private static final String WH_KING="2";
  private static final String BL_PIECE="3";
  private static final String BL_KING="4";

  private static final int ACTIVE=0;
  private static final int NONACTIVE=1;

  private static int showing = NONACTIVE;

  public static int playerId; // ID that Server sends for identification
  private static int oponnentId;
  private static int currPlayer;
  private static CoderDecoder CD;
  private static GameStatusClass gameStatus;

  private static PrintWriter out = null;
  private static BufferedReader in = null;

  public Client() { //
    CD = new CoderDecoder();

    listenSocket();
    // consider to re-try for initial info
    receiveInitInfo();

    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);
  }

  public int getPlayerId() {
    return this.playerId;
  }

  public int getCurrPlayer() {
    return this.currPlayer;
  }

  // TO DO: Delete this part | only used for quick tests
  public void boardInitFill() {
    for(int i=1; i<=8; i++) {
      for(int j=1; j<=8; j++) {
        gameStatus.board[i][j] = "";
      }
    }
  }

  private static void listenSocket() {
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

  private static void receiveInitInfo() {
    try {
      //Receive from Server player's index
      playerId = Integer.parseInt(in.readLine());
      currPlayer = Integer.parseInt(in.readLine()); //Receive from Server who starts the game

      if(playerId == WHITE)
        oponnentId = BLACK;
      else
        oponnentId = WHITE;

      // receive initial gameStatus
      receive();

      //TO DO: Change Label in GUI to show whose turn is now
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  private static void send(ICommand command) { // PREV: nonstatic
    System.out.println("Client sends move info to Server");

    // code command using CoderDecoder
    String commandString = CD.codeCommand(command);
    //Send string to Server
    out.println(commandString);

    currPlayer = oponnentId; // PREV: oponnentId
  }

  private static void receive() { // PREV: nonstatic
    try {
      String commandString = in.readLine();
      System.out.println("CommandString: "+commandString);
      //ICommand command =  CD.decodeCommand(commandString, "gameStatus");
      ICommand command =  CD.decodeCommand(commandString);
      gameStatus = (GameStatusClass) command;
      System.out.println("GameStatus View:" + gameStatus.showView());

    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public boolean move(Position from, Position to) {
    System.out.println("FROM X:" + from.getX() + " FROM Y: " + from.getY() + " TO X: " + to.getX() + " TO Y: " + to.getY());

    GameCommandClass moveCommand = new GameCommandClass();

    moveCommand.setActorId(playerId);

    // read figure's type from gameStatus's boar based on FROM position
    moveCommand.setPieceId(PIECE);
    /*
    if(gameStatus.board[from.getX()][from.getY()] == WH_PIECE || gameStatus.board[from.getX()][from.getY()] == BL_PIECE) {
      System.out.println("Client move() - FIGURE KIND: PIECE");
      moveCommand.setPieceId(PIECE);
    } else if(gameStatus.board[from.getX()][from.getY()] == WH_KING || gameStatus.board[from.getX()][from.getY()] == BL_KING) {
      System.out.println("Client move() - FIGURE KIND: KING");
      moveCommand.setPieceId(KING);
    }
    */
    List<Position> positions = new ArrayList<Position>();
    positions.add(from);
    positions.add(to);
    moveCommand.setPositions(positions);

    // send move info to Server
    send(moveCommand);

    // receive move output info from Server
    receive();

    if(gameStatus.getError() == "") {
      return true; // correct move
    } else {
      return false; // incorrect move
    }
  }

  public GameStatusClass getGameStatus() {
    receive();
    return this.gameStatus;
  }

  public GameStatusClass getGameStatusClass() {
    return this.gameStatus;
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


//package tp.frontend;
//
//import javafx.application.Application;
//import javafx.scene.layout.Pane;
//
//import java.net.*;
//import java.io.*;
//import java.util.ArrayList;
//
//public class Client  {
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
//  private static void send(ICommand command) { // PREV: nonstatic
//    // code command using CoderDecoder
//    String commandString = CD.codeCommand(command);
//    //Send string to Server
//    out.println(commandString);
//
//    showing = ACTIVE;
//    currPlayer = oponnentId; // PREV: oponnentId
//  }
//
//  private static void receive() { // PREV: nonstatic
//     try {
//      String commandString = in.readLine();
//      System.out.println("CommandString: "+commandString);
//      ICommand command =  CD.decodeCommand(commandString, "gameStatus");
//      gameStatus = (gameStatusClass) command;
//      System.out.println("GameStatus View:" + gameStatus.showView());
//
//     } catch(IOException e) {
//      System.out.println(e.getMessage());
//     }
//  }
//
//
//  public static void main(String[] main) throws FileNotFoundException {
//
//    Client player = new Client();
//
//    player.listenSocket();
//    player.receiveInitInfo();
//
//    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);
//
//    receive();
//
//    gameCommandClass moveCommand = new gameCommandClass();
//    moveCommand.setActorId(playerId);
//    moveCommand.setPieceId(PIECE);
//
////    positionChanges.add ( "pos56" );
////    positionChanges.add ( "pos74" );
//
//
//    Application.launch ( GUIstart.class);
////
////
////    moveCommand.fromX=6;
////    moveCommand.fromY=3;
////    moveCommand.toX=5;
////    moveCommand.toY=2;
////
////    send(moveCommand);
////    receive();
//
//  }
//}
