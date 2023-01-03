package tp;

import java.net.*;
import java.io.*;

public class Client { 
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
    System.out.println("CLIENT sends: " + commandString);
    
    //Send string to Server
    out.println(commandString);
    
    showing = ACTIVE;
  }

  private static void receive() { // PREV: nonstatic
     try {
      String commandString = in.readLine();
      System.out.println("CLIENT receives: "+commandString);
      ICommand command =  CD.decodeCommand(commandString, "gameStatus");
      gameStatus = (gameStatusClass) command;
      System.out.println("GameStatus View:" + gameStatus.showView());
      
      // Display error message
      if(gameStatus.getError() != "" && Integer.parseInt(gameStatus.getTurn()) == playerId) {
        System.out.println(gameStatus.getError());
      }

      //Update currPlayer based on gameStatus
      currPlayer = Integer.parseInt(gameStatus.turn);

     } catch(IOException e) {
      System.out.println(e.getMessage());
     }
  }  

  public static void main(String[] main) {
    Client player = new Client();
    Position tmp = new Position();

    player.listenSocket();
    player.receiveInitInfo();

    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);

    receive();

    gameCommandClass moveCommand = new gameCommandClass();

    // REGULAR MOVE / SINGLE CAPTURE - TEST
    
    if(playerId == 1 && currPlayer == 1) { //WHITE
      System.out.println("WHITE sends move");
      moveCommand.setActorId(playerId);
      moveCommand.setPieceId(PIECE);

      tmp = new Position();
      tmp.setX(6);
      tmp.setY(5);
      moveCommand.addPosition(tmp);

      tmp = new Position();
      tmp.setX(5);
      tmp.setY(4);
      moveCommand.addPosition(tmp);

      send(moveCommand);
      //clear positions in moveCommand
      moveCommand.clearPositions();
    }

    
    receive();

    if(playerId == 2 && currPlayer == 2) { // BLACK
      System.out.println("BLACK sends move");
      moveCommand.setActorId(playerId);
      moveCommand.setPieceId(PIECE);

      tmp = new Position();
      tmp.setX(3);
      tmp.setY(2);
      moveCommand.addPosition(tmp);

      // Testing wrong move
//     tmp = new Position();
//      tmp.setX(3);
//      tmp.setY(3);
//      moveCommand.addPosition(tmp);

      tmp = new Position();
      tmp.setX(4);
      tmp.setY(3);
      moveCommand.addPosition(tmp);

      send(moveCommand);
      moveCommand.clearPositions();
    }
    
    receive();

    if(playerId == 1 && currPlayer == 1) { //WHITE
      System.out.println("WHITE sends move");
      moveCommand.setActorId(playerId);
      moveCommand.setPieceId(PIECE);

      tmp = new Position();
      tmp.setX(5);
      tmp.setY(4);
      moveCommand.addPosition(tmp);

      tmp = new Position();
      tmp.setX(3);
      tmp.setY(2);
      moveCommand.addPosition(tmp);

      send(moveCommand);
      moveCommand.clearPositions();
    } 

    
    receive();

    if(playerId == 2 && currPlayer == 2) { // BLACK
      System.out.println("BLACK sends move");
      moveCommand.setActorId(playerId);
      moveCommand.setPieceId(PIECE);

      tmp = new Position();
      tmp.setX(2);
      tmp.setY(1);
      moveCommand.addPosition(tmp);

      tmp = new Position();
      tmp.setX(4);
      tmp.setY(3);
      moveCommand.addPosition(tmp);
      
      send(moveCommand);
      moveCommand.clearPositions();
    }

    receive();

    
/* 
    //MULTI CAPTURE TEST
    if(playerId == 1 && currPlayer == 1) { //WHITE
      System.out.println("WHITE sends move");
      moveCommand.setActorId(playerId);
      moveCommand.setPieceId(PIECE);

      tmp = new Position();
      tmp.setX(6);
      tmp.setY(5);
      moveCommand.addPosition(tmp);

      tmp = new Position();
      tmp.setX(4);
      tmp.setY(3);
      moveCommand.addPosition(tmp);

      tmp = new Position();
      tmp.setX(2);
      tmp.setY(5);
      moveCommand.addPosition(tmp);

      send(moveCommand);
      //clear positions in moveCommand
      moveCommand.clearPositions();
    }

    receive();

    if(playerId == 2 && currPlayer == 2) { // BLACK
      System.out.println("BLACK sends move");
      moveCommand.setActorId(playerId);
      moveCommand.setPieceId(PIECE);

      tmp = new Position();
      tmp.setX(1);
      tmp.setY(6);
      moveCommand.addPosition(tmp);

      //moveCommand.fromX=3; // Testing wrong move
      //moveCommand.fromY=3;

      tmp = new Position();
      tmp.setX(3);
      tmp.setY(4);
      moveCommand.addPosition(tmp);

      send(moveCommand);
      moveCommand.clearPositions();
    }
  
    receive();    
    */
  }
}
