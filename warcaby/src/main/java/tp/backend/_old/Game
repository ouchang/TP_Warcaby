package tp.backend;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Class responsible for game process
 */
public class Game implements Runnable {
  private Socket firstPlayer;
  private Socket secondPlayer;
  private CoderDecoder CD;

  private IGameKind gameKind;
  private String[][] board;
  private int boardSize;

  // Player's ID
  private final static int WHITE=1;
  private final static int BLACK=2;

  // Figure's type
  private static final int PIECE=0;
  private static final int KING=1;

  // Figure's ID
  private static final String EMPTY="0";
  private static final String WH_PIECE="1";
  private static final String WH_KING="2";
  private static final String BL_PIECE="3";
  private static final String BL_KING="4";

  // Move's type
  private static final String REGULAR = "REGULAR";
  private static final String CAPTURE = "CAPTURE";

  private int currPlayer; // whose turn is now? 
  private GameCommandClass moveCommand; // template used to inform about the move
  private GameStatusClass statusCommand; // template used to inform about the game's status

  private Movement movement; // output info received after making a move
  private int whitePrevFromX, whitePrevFromY; // last analyzed field (for player WHITE)
  private int blackPrevFromX, blackPrevFromY; // last analyzed field (for player BLACK)

  /**
   * Sets game's kind
   * @param gameKind
   */
  public void setGameKind(IGameKind gameKind) {
    this.gameKind = gameKind;
  }

  /**
   * Constructor
   * @param firstPlayer player1's socket
   * @param secondPlayer player2's socket
   * @param gameKind type of game
   */
  public Game(Socket firstPlayer, Socket secondPlayer, IGameKind gameKind) { //TO DO: Make factory for IGameKind
    this.CD = new CoderDecoder();
    //setGameKind(gameKind); //
    this.gameKind = gameKind;
    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;

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

  /**
   * Method responsible for initialize the process of checking the correctness of occured move
   * @param positions fields specialized in a given move (for example: jump from where? jump to where?)
   * @param symbol ID of piece used in a given move
   * @param currPlayer who made the move?
   * @param nextTurn whose turn is next?
   * @return
   */
  public synchronized boolean move(List<Position> positions, String symbol, int currPlayer, int nextTurn) {
    Position from = new Position();
    Position to = new Position();

    if(positions.size() > 2) { // Multi-Capture move
      // Read starting and ending position
      from = positions.get(0);
      to = positions.get(positions.size()-1);

      
      if(symbol == WH_PIECE || symbol == BL_PIECE) {
        // Start method responsible for checking piece's multi-capture move
        movement = gameKind.checkMultiCapturePiece(currPlayer, positions, board);
      } else if(symbol == WH_KING || symbol == BL_KING) {
        // Start method responsible for checking king's multi-capture move
        movement = gameKind.checkMultiCaptureKing(currPlayer, positions, board);
      }

      // Update board if the move was correct
      if(movement.getCorrectMove()) {
        // delete figure at starting position in board
        board[from.getX()][from.getY()] = EMPTY;

        // delete captured figures in board
        for(Position cf : movement.getCapturedFigures()) {
          //System.out.println("MULTI CAPTURE UPDATE BOARD["+cf.getX()+"]["+cf.getY()+"]: "+board[to.getX()][to.getY()] + " GO EMPTY");
          board[cf.getX()][cf.getY()] = EMPTY;
        }

        // put figure at ending position in board + check if piece becomes king
        if(gameKind.hasPieceUpgrade(currPlayer, to)) {
          System.out.println("UPGRADE TO KING!");
          if(symbol == WH_PIECE) {
            board[to.getX()][to.getY()] = WH_KING;
          } else if(symbol == BL_PIECE) {
            board[to.getX()][to.getY()] = BL_KING;
          }
        } else {
          System.out.println("MOVE WITHOUT UPGRADE");
          board[to.getX()][to.getY()] = symbol;
        }

        //System.out.println("MULTI CAPTURE UPDATE BOARD["+to.getX()+"]["+to.getY()+"]: "+board[to.getX()][to.getY()]);

        // change turn
        statusCommand.turn = String.valueOf(nextTurn);

        // save last analyzed position
        if(currPlayer == WHITE) { 
          whitePrevFromX = from.getX();
          whitePrevFromY = from.getY();
        } else if(currPlayer == BLACK) {
          blackPrevFromX = from.getX();
          blackPrevFromY = from.getY();
        }

        return true;
      } else {
        // update error info
        statusCommand.setError(movement.getErrorMessage());
        return false;
      }
    } else if(positions.size() == 2) { // Regular/Single capture move
      // Read starting and ending position
      from = positions.get(0);
      to = positions.get(positions.size()-1);

      if(symbol == WH_PIECE || symbol == BL_PIECE) {
        // Start method responsible for checking piece's move
        movement = gameKind.checkMovePiece(currPlayer, positions, board);
      } else if(symbol == WH_KING || symbol == BL_KING) {
        // Start method responsible for checking king's move
        movement = gameKind.checkMoveKing(currPlayer, positions, board);
      }

      // Update board if the move was correct
      if(movement.getCorrectMove()) {
        if(movement.getKind() == CAPTURE) {
          // get info about captured figure
          Position cf = movement.getCapturedFigures().get(0);
          
          // delete figure at starting position in board
          board[from.getX()][from.getY()] = EMPTY;

          // delete captured figures in board
          board[cf.getX()][cf.getY()] = EMPTY;

          
          // put figure at ending position in board + check if piece becomes king
          if(gameKind.hasPieceUpgrade(currPlayer, to)) {
            System.out.println("UPGRADE TO KING!");
            if(symbol == WH_PIECE) {
              board[to.getX()][to.getY()] = WH_KING;
            } else if(symbol == BL_PIECE) {
              board[to.getX()][to.getY()] = BL_KING;
            }
          } else {
            System.out.println("MOVE WITHOUT UPGRADE");
            board[to.getX()][to.getY()] = symbol;
          }

          //System.out.println("CAPTURE UPDATE BOARD["+to.getX()+"]["+to.getY()+"]: "+board[to.getX()][to.getY()]);
        } else if(movement.getKind() == REGULAR) {
          // delete figure at starting position in board
          board[from.getX()][from.getY()] = EMPTY;
          
          // put figure at ending position in board
          board[to.getX()][to.getY()] = symbol;

          //System.out.println("REGULAR UPDATE BOARD["+to.getX()+"]["+to.getY()+"]: "+board[to.getX()][to.getY()]);
        }
        
        // change turn
        statusCommand.turn = String.valueOf(nextTurn);
        
        // save last analyzed position
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

      //TO DO: Receive info about gameKind from player1 (WHITE) -> first connected Client plays WHITE by default
      

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
          //moveCommand = (GameCommandClass)CD.decodeCommand(line, "gameCommand");
          GameCommandClass moveCommand = (GameCommandClass) CD.decodeCommand(line);

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
          moveCommand = (GameCommandClass)CD.decodeCommand(line);
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
