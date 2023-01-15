package tp.backend;

import java.util.UUID;
import java.util.List;

public class GameNew {
  private int numOfPlayers;

  private IGameKind gameKind;
  private String[][] board;
  private int boardSize;

  private String player1ID;
  private String player2ID;

  private String activePlayerID;

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

  // Last analyzed move
  private int whitePrevFromX, whitePrevFromY;
  private int blackPrevFromX, blackPrevFromY;

  GameNew() {
    this.numOfPlayers = 0;
    this.activePlayerID = "";
    this.board = null;
    this.gameKind = null;
    this.player1ID = "";
    this.player2ID = "";
  }

  public void setGameKind(IGameKind gameKind) {
    this.gameKind = gameKind;
  }

  public void loadInitBoard() {
    this.board = gameKind.getGameBoard();
    this.boardSize = gameKind.getBoardSize();
  }

  public String getPlayer1ID() {
    return this.player1ID;
  }

  public String getPlayer2ID() {
    return this.player2ID;
  }

  public String getGameKindName() {
    if(this.gameKind != null) {
      return this.gameKind.getClass().getSimpleName();
    }

    return "";
  }

  public String getActivePlayerID() {
    return this.activePlayerID;
  }

  public String[][] getBoard() {
    return this.board;
  }

  public int getNumOfPlayers() {
    return this.numOfPlayers;
  }

  public String addPlayer() {
    UUID uuid = UUID.randomUUID();
    
    if(numOfPlayers == 0) {
      this.player1ID = uuid.toString();
      this.activePlayerID = this.player1ID;
      this.numOfPlayers++;
      return this.player1ID;
    } else if(numOfPlayers == 1) {
      this.player2ID = uuid.toString();
      this.numOfPlayers++;
      return this.player2ID;
    }

    return ""; //FIXME
  }

  /**
   * 
   * @param positions
   * @param currPlayer
   * @return Error Message (if move was correct error message is empty (""))
   */
  public synchronized Movement move(List<Position> positions, String currPlayerID) { //String
    Movement movement = new Movement();

    Position from = new Position();
    Position to = new Position();

    String symbol;
    int currPlayer = -1;

    if(currPlayerID.equals(this.player1ID)) {
      currPlayer = WHITE;
    } else if(currPlayerID.equals(this.player2ID)) {
      currPlayer = BLACK;
    }

    if(positions.size() > 2) { // Multi-Capture move
      // Read starting and ending position
      from = positions.get(0);
      to = positions.get(positions.size()-1);

      symbol = this.board[from.getX()][from.getY()];
      
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
        if(this.player1ID.equals(currPlayerID)) {
          this.activePlayerID = this.player2ID;
        } else if(this.player2ID.equals(currPlayerID)) {
          this.activePlayerID = this.player1ID;
        }

        // save last analyzed position
        if(currPlayer == WHITE) { 
          whitePrevFromX = from.getX();
          whitePrevFromY = from.getY();
        } else if(currPlayer == BLACK) {
          blackPrevFromX = from.getX();
          blackPrevFromY = from.getY();
        }

        return movement;
      } else {
        // update error info
        return movement;
      }
    } else if(positions.size() == 2) { // Regular/Single capture move
      // Read starting and ending position
      from = positions.get(0);
      to = positions.get(positions.size()-1);

      symbol = this.board[from.getX()][from.getY()];

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
        if(this.player1ID.equals(currPlayerID)) {
          this.activePlayerID = this.player2ID;
        } else if(this.player2ID.equals(currPlayerID)) {
          this.activePlayerID = this.player1ID;
        }
        
        // save last analyzed position
        if(currPlayer == WHITE) {
          whitePrevFromX = from.getX();
          whitePrevFromY = from.getY();
        } else if(currPlayer == BLACK) {
          blackPrevFromX = from.getX();
          blackPrevFromY = from.getY();
        }
        
        return movement;
      }  else {
        return movement;
      }
    }

    return null;
  }

}
