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

  //private static int showingWhite = NONACTIVE; //
  //private static int showingBlack = NONACTIVE; //
  private static int showing = NONACTIVE; 

  private static int playerId; // Id that Server sends to identification
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
    for(int i=1; i<=8; i++) { //
      for(int j=1; j<=8; j++) { //
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
      //System.out.println("PLAYER_ID: " + playerId);
      currPlayer = Integer.parseInt(in.readLine()); //Receive from Server who starts the game
      //System.out.println("CURR_PLAYER_ID: " + currPlayer);

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

  private static void send(Command command) { // nonstatic
    //TO DO: Send temporary MOVE command

    // code command using CoderDecoder
    String commandString = CD.codeCommand(command);
    //Send string to Server
    out.println(commandString);
    
    //currPlayer = oponnentId;
    showing = ACTIVE;
    currPlayer = playerId;
  }

  private static void receive() { //nonstatic
    //TO DO: Receive from Server temporary GAME STATUS command
     try {
      String commandString = in.readLine();
      System.out.println("CommandString: "+commandString);
      Command command =  CD.decodeCommand(commandString, "gameStatus");
      gameStatus = (gameStatusClass) command;
      gameStatus.showView();
      
     } catch(IOException e) {
      System.out.println(e.getMessage());
     }
  }  

  public static void main(String[] main) {
    Client player = new Client();

    player.listenSocket();
    player.receiveInitInfo();

    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);

    receive();

    gameCommandClass moveCommand = new gameCommandClass();
    moveCommand.setActorId(playerId);
    moveCommand.setPieceId(PIECE);
    moveCommand.fromX=6; 
    moveCommand.fromY=3;
    moveCommand.toX=5; 
    moveCommand.toY=2;

    send(moveCommand);
    receive();
  }
}
