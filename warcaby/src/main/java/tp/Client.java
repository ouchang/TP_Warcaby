package tp;

import java.net.*;
import java.io.*;

public class Client implements Runnable { 
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
  private Command gameStatus;

  //TO DO: Move this to GUI Class
  private static PrintWriter out = null;
  private static BufferedReader in = null;

  Client() {
    CD = new CoderDecoder();
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
    //System.out.println("SEND PLAYER_ID: " + playerId);
    //TO DO: Send temporary MOVE command

    // code command using CoderDecoder
    String commandString = CD.codeCommand(command);
    //Send string to Server
    out.println(commandString);

    
    //currPlayer = oponnentId;
    showing = ACTIVE;
    currPlayer = playerId;
  }

  private void receive() { //nonstatic
    //System.out.println("RECEIVE PLAYER_ID: " + playerId);
    //TO DO: Receive from Server temporary GAME STATUS command
     try {
      String commandString = in.readLine();
      //System.out.println("IN COMMAND CODE: " + commandString);
      Command command =  CD.decodeCommand(commandString, "gameStaus");
      gameStatus = (gameStatusClass) command;
      gameStatus.showView();
      
     } catch(IOException e) {
      System.out.println(e.getMessage());
     }
  }  

  private void startThread() {
    Thread playerThread = new Thread(this);
    playerThread.start();
  }

  public void run() {
    
    if(playerId == WHITE) {
      runWhite();
    } else {
      runBlack();
    }
    
    //runPlayer();
  }
/*
  void runPlayer(){
    while(true) {
        synchronized (this) {
            if (currPlayer== playerId) {
                
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
*/

  void runWhite() {
    while(true) {
      synchronized(this) {

        if(showing == ACTIVE) {
          //System.out.println("SHOW ACTIVE IN PLAYER WHITE TIME: " + java.time.LocalTime.now());
          receive();
          showing = NONACTIVE;
          //System.out.println("SHOW FOR WHITE - NONACTIVE");
        }

        if(currPlayer == WHITE) {
          //TO DO: Add temporary command to move piece
          gameCommandClass moveCommand = new gameCommandClass();
          moveCommand.setActorId(playerId);
          moveCommand.setPieceId(PIECE);
          moveCommand.fromX=6; 
          moveCommand.fromY=3;
          moveCommand.toX=5; 
          moveCommand.toY=4; 
          send(moveCommand);
          //System.out.println("SHOW FOR BLACK - ACTIVE TIME: " + java.time.LocalTime.now());
          showing = ACTIVE;
          //System.out.println("WH CURR_PLAYER: " + currPlayer);
          
          try {
            wait(10);
          } catch (InterruptedException e) {}
        }
      }
      //notifyAll();
    }
  }

  void runBlack() {
    while(true) {
      synchronized(this) {
        if(showing == ACTIVE) {
          //System.out.println("SHOW ACTIVE IN PLAYER BLACK TIME: " + java.time.LocalTime.now());
          receive();
          showing = NONACTIVE;
          //System.out.println("SHOW FOR BLACK - NONACTIVE");
        }

        if(currPlayer == BLACK) {
          try {
            wait(10);
          } catch (InterruptedException e) {}

          //System.out.println("CLIENT CURR_PLAYER BLACK TIME: " + java.time.LocalTime.now());
          //TO DO: Add temporary command to move piece
          
          gameCommandClass moveCommand = new gameCommandClass();
          moveCommand.setActorId(playerId);
          moveCommand.setPieceId(KING);
          moveCommand.fromX=2; 
          moveCommand.fromY=5;
          moveCommand.toX=5; 
          moveCommand.toY=8;
          send(moveCommand);
          showing = ACTIVE;
          //System.out.println("SHOW FOR WHITE - ACTIVE TIME: " + java.time.LocalTime.now());
          //System.out.println("BL CURR_PLAYER: " + currPlayer); 
        }
      }
      //notifyAll();
    }
  }

  public static void main(String[] main) {
    Client player = new Client();

    player.listenSocket();
    player.receiveInitInfo();
    //player.startThread();

    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);

    player.receiveInitInfo();
    System.out.println("PlayerId: " + playerId + " CurrPlayer: " + currPlayer);
  }
}
