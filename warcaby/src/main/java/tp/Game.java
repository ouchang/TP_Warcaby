package tp;

import java.io.*;
import java.net.*;

public class Game implements Runnable {
  private Socket firstPlayer;
  private Socket secondPlayer;
  private CoderDecoder CD;

  private IGameKind gameKind;
  private String[][] board;
  private int boardSize;

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

    this.boardSize = gameKind.getBoardSize();
    this.board = new String[boardSize+1][boardSize+1];
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
    statusCommand.setBoard(this.board);
  }

  public synchronized void move(int x, int y, String symbol, int nextTurn) {
    statusCommand.turn = String.valueOf(nextTurn);
    statusCommand.board[x][y] = symbol;
  }

  public synchronized void sendStatus(PrintWriter out) {
    String line;
    line = CD.codeCommand(statusCommand);
    System.out.println("SEND LINE: " + line);
    out.println(line);
  }

  public void boardInitFill() {
    for(int i=0; i<=boardSize; i++) {
      for(int j=0; j<=boardSize; j++) {
        board[i][j] = EMPTY;
      }
    }
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

      boardInitFill(); // fill board with EMPTY symbol ("0")

      // Tell clients their assigned indexes
      outW.println("1"); //TO DO: Change to json
      outB.println("2");

      //Tell clients who starts the game
      outW.println(String.valueOf(gameKind.whoStarts())); //TO DO: Change to json
      outB.println(String.valueOf(gameKind.whoStarts()));

      //Start game
      String line;
      
      //TO DO: Replace temporary statusCommand's content from constructor with init info read from GUI
      line = CD.codeCommand(statusCommand);
      System.out.println("START STATUS: " + line);
      outW.println(line);
      outB.println(line);
      
      
      //do { 
        if(currPlayer == WHITE) {
          //receive MOVE command
          line = inW.readLine();
          System.out.println("MOVE LINE: " + line);
          moveCommand = (gameCommandClass)CD.decodeCommand(line, "gameCommand");
          
          if(moveCommand.pieceId == PIECE) {
            System.out.println("WHITE MOVE PIECE");
            if(gameKind.checkMovePiece(currPlayer, moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              move(moveCommand.toX, moveCommand.toY, WH_PIECE, BLACK);
              sendStatus(outB);
              sendStatus(outW);
            }
          } else if(moveCommand.pieceId == KING) {
            System.out.println("WHITE MOVE KING");
            if(gameKind.checkMoveKing(moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              move(moveCommand.toX, moveCommand.toY, WH_KING, BLACK);
              sendStatus(outB);
              sendStatus(outW);
            }
          }

          currPlayer = BLACK;
        } else if(currPlayer == BLACK) {
          //receive MOVE command
          line = inB.readLine();
          moveCommand = (gameCommandClass)CD.decodeCommand(line, "gameCommand"); 
          
          if(moveCommand.pieceId == PIECE) {
            System.out.println("BLACK MOVE PIECE");
            if(gameKind.checkMovePiece(currPlayer, moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              move(moveCommand.toX, moveCommand.toY, BL_PIECE, WHITE);
              sendStatus(outW);
              sendStatus(outB);
            }
          } else if(moveCommand.pieceId == KING) {
            System.out.println("BLACK MOVE KING");
            if(gameKind.checkMoveKing(moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY)) {
              move(moveCommand.toX, moveCommand.toY, BL_KING, WHITE);
              sendStatus(outW);
              sendStatus(outB);
            }
          }
          currPlayer = WHITE;
        }
      //} while(true);
      
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
