package tp.backend;

import tp.backend.position.Position;

import java.io.*;
import java.net.Socket;
import java.util.List;

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

  private static final String REGULAR = "REGULAR";
  private static final String CAPTURE = "CAPTURE";

  private int currPlayer;
  private GameCommandClass moveCommand;
  private GameStatusClass statusCommand;

  private Movement movement;
  private int whitePrevFromX, whitePrevFromY;
  private int blackPrevFromX, blackPrevFromY;

  public Game(Socket firstPlayer, Socket secondPlayer, IGameKind gameKind) {
    this.CD = new CoderDecoder();

    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
    this.gameKind = gameKind;

    this.boardSize = gameKind.getBoardSize();
    this.board = gameKind.getGameBoard();
    this.currPlayer = gameKind.whoStarts();

    this.moveCommand = new GameCommandClass ();
    this.statusCommand = new GameStatusClass ();

    this.movement = new Movement();

    this.blackPrevFromX = -1;
    this.blackPrevFromY = -1;
    
    this.whitePrevFromX = -1;
    this.whitePrevFromY = -1;

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

  public synchronized boolean move(List<Position> positions, String symbol, int currPlayer, int nextTurn) {

    Position from = new Position();
    Position to = new Position();

    if(positions.size() > 2) { // multi-capture move
      from = positions.get(0);
      to = positions.get(positions.size()-1);

      movement = gameKind.checkMultiCapturePiece(currPlayer, positions, board);

      if(symbol == WH_PIECE || symbol == BL_PIECE) {
        movement = gameKind.checkMultiCapturePiece(currPlayer, positions, board);
      } else if(symbol == WH_KING || symbol == BL_KING) {
        movement = gameKind.checkMultiCaptureKing(currPlayer, positions, board);
      }

      if(movement.getCorrectMove()) {
        board[from.getX()][from.getY()] = EMPTY;
        for(Position cf : movement.getCapturedFigures()) {
          System.out.println("MULTI CAPTURE UPDATE BOARD["+cf.getX()+"]["+cf.getY()+"]: "+board[to.getX()][to.getY()] + " GO EMPTY");
          board[cf.getX()][cf.getY()] = EMPTY;
        }
        board[to.getX()][to.getY()] = symbol;
        System.out.println("MULTI CAPTURE UPDATE BOARD["+to.getX()+"]["+to.getY()+"]: "+board[to.getX()][to.getY()]);

        statusCommand.turn = String.valueOf(nextTurn);
        return true;
      } else {
        statusCommand.setError(movement.getErrorMessage());

        if(currPlayer == WHITE) { 
          whitePrevFromX = from.getX();
          whitePrevFromY = from.getY();
        } else if(currPlayer == BLACK) {
          blackPrevFromX = from.getX();
          blackPrevFromY = from.getY();
        }

        return false;
      }
    } else if(positions.size() == 2) { // single move
      from = positions.get(0);
      to = positions.get(positions.size()-1);

      if(symbol == WH_PIECE || symbol == BL_PIECE) {
        movement = gameKind.checkMovePiece(currPlayer, positions, board);
      } else if(symbol == WH_KING || symbol == BL_KING) {
        movement = gameKind.checkMoveKing(currPlayer, positions, board);
      }

      if(movement.getCorrectMove()) {
        if(movement.getKind() == CAPTURE) {
          Position cf = movement.getCapturedFigures().get(0);
          board[from.getX()][from.getY()] = EMPTY;
          board[cf.getX()][cf.getY()] = EMPTY;
          board[to.getX()][to.getY()] = symbol;
          System.out.println("CAPTURE UPDATE BOARD["+to.getX()+"]["+to.getY()+"]: "+board[to.getX()][to.getY()]);
        } else if(movement.getKind() == REGULAR) {
          board[from.getX()][from.getY()] = EMPTY;
          board[to.getX()][to.getY()] = symbol;
          System.out.println("REGULAR UPDATE BOARD["+to.getX()+"]["+to.getY()+"]: "+board[to.getX()][to.getY()]);
        }

        statusCommand.turn = String.valueOf(nextTurn);
  
        if(currPlayer == WHITE) {
          whitePrevFromX = from.getX();
          whitePrevFromY = from.getY();
        } else if(currPlayer == BLACK) {
          blackPrevFromX = from.getX();
          blackPrevFromY = from.getY();
        }
  
        return true;
      }  else {
        statusCommand.setError(movement.getErrorMessage());
        return false;
      }
    }

    return false;
  }
    
  public synchronized void sendStatus(PrintWriter out) {
    String line;
    line = CD.codeCommand(statusCommand);
    System.out.println("SEND LINE: " + line);
    out.println(line);
  }

  @Override
  public void run() {
    boolean moveOccured = false;

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
      outW.println("1"); //TO DO: Change to json (?)
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
      
      do { 
        System.out.println("NEW ITERATION - WAITING FOR PLAYER: " + currPlayer + " TO MOVE");
        if(currPlayer == WHITE) {
          //receive MOVE command
          line = inW.readLine();

          // Null line exception (?)
          if(line == null) {
            System.out.println("Empty ClientStream");
            return;
          }

          System.out.println("MOVE LINE: " + line);
          moveCommand = (GameCommandClass)CD.decodeCommand(line, "gameCommand");
          Position from = new Position();

          if(moveCommand.getPositions().size() != 0) {
            from = moveCommand.positions.get(0);
          }

          if(moveCommand.getActorId() != WHITE) { // hasn't receive info about WHITE's new move
            continue;
          }

          if(from.getX() == whitePrevFromX && from.getY() == whitePrevFromY) { // still haven't received new move info
            continue;
          }

          if(moveCommand.pieceId == PIECE) {  
            System.out.println("WHITE MOVE PIECE");


            //moveOccured = move(moveCommand.fromX, moveCommand.fromY, moveCommand.toX, moveCommand.toY, WH_PIECE, WHITE, BLACK);
            moveOccured = move(moveCommand.positions, WH_PIECE, WHITE, BLACK);
          } else if(moveCommand.pieceId == KING) {
            System.out.println("WHITE MOVE KING");
            moveOccured = move(moveCommand.positions, WH_KING, WHITE, BLACK);
          }

          if(moveOccured) {
            System.out.println("MOVE OCCURED");
            currPlayer = BLACK;
            moveOccured = false;
          }
        } else if(currPlayer == BLACK) {
          //receive MOVE command
          line = inB.readLine();

          // Null line exception (?)
          if(line == null) {
            System.out.println("Empty ClientStream");
            return;
          }

          System.out.println("MOVE LINE: " + line);
          moveCommand = (GameCommandClass)CD.decodeCommand(line, "gameCommand");
          Position from = new Position();

          if(moveCommand.getPositions().size() != 0) {
            from = moveCommand.positions.get(0);
          }

          if(moveCommand.getActorId() != BLACK) { // hasn't receive info about BLACK's new move
            continue;
          }

          if(from.getX() == blackPrevFromX && from.getY() == blackPrevFromY) { // still haven't received new move info
            continue;
          }
      
          if(moveCommand.pieceId == PIECE) {
            System.out.println("BLACK MOVE PIECE");
            moveOccured = move(moveCommand.positions, BL_PIECE, BLACK, WHITE);

          } else if(moveCommand.pieceId == KING) {
            System.out.println("BLACK MOVE KING");
            moveOccured = move(moveCommand.positions, BL_KING, BLACK, WHITE);    
          }

          if(moveOccured) {
            System.out.println("MOVE OCCURED");
            currPlayer = WHITE;
            moveOccured = false;
          }
        }

        sendStatus(outB);
        sendStatus(outW);

        if(statusCommand.getError() != "") {
          statusCommand.setError("");
        }

      } while(true);
      
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
