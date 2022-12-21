package tp;

import java.io.*;
import java.net.*;

public class Game implements Runnable {
  private Socket firstPlayer;
  private Socket secondPlayer;
  private CoderDecoder CD;

  private IGameKind gameKind;
  private String[][] board;

  private final static int WHITE=1;
  private final static int BLACK=2;

  private static final int PIECE=0;
  private static final int KING=1;

  private static final String EMPTY="0";
  private static final String WH_PIECE="1";
  private static final String WH_KING="2";
  private static final String BL_PIECE="3";
  private static final String BL_KING="4";

  private int currPlayer;
  private gameCommandClass moveCommand;
  private gameStatusClass statusCommand;

  public Game(Socket firstPlayer, Socket secondPlayer, IGameKind gameKind) {
    this.CD = new CoderDecoder();

    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
    this.gameKind = gameKind;

    int boardSize = gameKind.getBoardSize();
    this.board = new String[boardSize][boardSize];
    this.currPlayer = gameKind.whoStarts();

    this.moveCommand = new gameCommandClass();
    this.statusCommand = new gameStatusClass();

    //Temporary inicializing statusCommand
    statusCommand.setId("12");
    statusCommand.setFriendly_name("testGame1");
    statusCommand.setGameKind(gameKind.getName());
    statusCommand.setPlayer1("Ola");
    statusCommand.setPlayer2("Aga");
    statusCommand.setTurn(String.valueOf(gameKind.whoStarts()));
    statusCommand.setStatus("ongoing");
    statusCommand.setError("");
    String[][] board = new String[boardSize+1][boardSize+1];
    statusCommand.setBoard(board);
  }

  @Override
  public void run() {
    try {
      //Support for player1's socket (WHITE)
      InputStream inputFirst = firstPlayer.getInputStream();
      BufferedReader inW = new BufferedReader(new InputStreamReader(inputFirst));

      OutputStream outputFirst = firstPlayer.getOutputStream();
      PrintWriter outW = new PrintWriter(outputFirst, true); // creates a new PrintWriter from an existing OutputStream with autoFlush (true).

      //Support for player2's socket (BLACK)
      InputStream inputSecond = secondPlayer.getInputStream();
      BufferedReader inB = new BufferedReader(new InputStreamReader(inputSecond));

      OutputStream outputSecond = secondPlayer.getOutputStream();
      PrintWriter outB = new PrintWriter(outputSecond, true);

      // Tell clients their assigned indexes
      outW.println("1"); //TO DO: Change to json
      outB.println("2");

      //Tell clients who starts the game
      outW.println(String.valueOf(gameKind.whoStarts())); //TO DO: Change to json
      outB.println(String.valueOf(gameKind.whoStarts()));

      //Start game

      String line;
      //System.out.println("currPlayer: " + currPlayer);

      
      do { 
        if(currPlayer == WHITE) {
          line = inW.readLine();
          System.out.println("WHITE LINE: " + line + " TIME: " + java.time.LocalTime.now());
          //create MOVE COMMNAD instance
          
          moveCommand = (gameCommandClass)CD.decodeCommand(line, "gameCommand"); //
          
          if(moveCommand.pieceId == PIECE) {
            System.out.println("WHITE MOVE PIECE");
            if(gameKind.checkMovePiece(currPlayer, moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              System.out.println("WHITE GOOD MOVE");
              statusCommand.turn = String.valueOf(BLACK);
              statusCommand.board[moveCommand.toX][moveCommand.toY] = WH_PIECE;

              line = CD.codeCommand(statusCommand);
              System.out.println("WHITE UPDATE STATUS: " + line);
              outB.println(line);
              outW.println(line);
            }
          } else if(moveCommand.pieceId == KING) {
            System.out.println("WHITE MOVE KING");
            if(gameKind.checkMoveKing(moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              System.out.println("WHITE GOOD MOVE");
              statusCommand.turn = String.valueOf(BLACK);
              statusCommand.board[moveCommand.toX][moveCommand.toY] = WH_KING;
              System.out.println("WHITE UPDATE STATUS: " + line);
              line = CD.codeCommand(statusCommand);
              outB.println(line);
              outW.println(line);
            }
          }
          System.out.println("GAME | CHANGE TO BLACK");
          currPlayer = BLACK;
        } else if(currPlayer == BLACK) {
          line = inB.readLine();
          System.out.println("BLACK LINE: " + line);
          //create MOVE COMMNAD instance
          
          moveCommand = (gameCommandClass)CD.decodeCommand(line, "gameCommand"); //
          
          if(moveCommand.pieceId == PIECE) {
            System.out.println("BLACK MOVE PIECE");
            if(gameKind.checkMovePiece(currPlayer, moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              System.out.println("BLACK GOOD MOVE");
              statusCommand.turn = String.valueOf(WHITE);
              statusCommand.board[moveCommand.toX][moveCommand.toY] = BL_PIECE;

              line = CD.codeCommand(statusCommand);
              System.out.println("BLACK UPDATE STATUS: " + line);
              outW.println(line);
              outB.println(line);
            }
          } else if(moveCommand.pieceId == KING) {
            System.out.println("BLACK MOVE KING");
            if(gameKind.checkMoveKing(moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              System.out.println("BLACK GOOD MOVE");
              statusCommand.turn = String.valueOf(WHITE);
              statusCommand.board[moveCommand.toX][moveCommand.toY] = BL_KING;

              line = CD.codeCommand(statusCommand);
              System.out.println("BLACK UPDATE STATUS: " + line);
              outW.println(line);
              outB.println(line);
            }
          }
          System.out.println("GAME | CHANGE TO WHITE");
          currPlayer = WHITE;
        }
      } while(true);

    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
