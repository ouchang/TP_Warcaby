package tp;

import java.util.List;
import java.util.ArrayList;

public class CzechKind implements IGameKind {
  private static final int WHITE=1;
  private static final int BLACK=2;
  private int boardSize;
  private String name;
  private String[][] gameBoard;
  
  private static final String EMPTY="0";
  private static final String WH_PIECE="1";
  private static final String WH_KING="2";
  private static final String BL_PIECE="3";
  private static final String BL_KING="4";

  private static final String REGULAR = "REGULAR";
  private static final String CAPTURE = "CAPTURE";


  CzechKind() {
    this.name = "Czech";
    this.boardSize = 8;
    this.gameBoard = new String[boardSize+1][boardSize+1];

    // Initial board fill (starting player POV)
    for(int i=0; i<=boardSize; i++) {
      for(int j=0; j<=boardSize; j++) {
        gameBoard[i][j] = EMPTY;

        if(i == 0 || j == 0) {
          continue;
        }

        if(i >= 1 && i <= 3) { // BLACK side
          if(i%2 == 1 && j%2 == 0) {
            gameBoard[i][j] = BL_PIECE;
          } else if(i%2 == 0 && j%2 == 1) {
            gameBoard[i][j] = BL_PIECE;
          }
        } else if(i >= 6 && i <= 8) { // WHITE side
          if(i%2 == 0  && j%2 == 1) {
            gameBoard[i][j] = WH_PIECE;
          } else if(i%2 == 1 && j%2 == 0) {
            gameBoard[i][j] = WH_PIECE;
          }
        }
      }
    }
  }

  public String[][] getGameBoard() {
    return this.gameBoard;
  }

  public int getBoardSize() {
    return boardSize;
  }

  public String getName() {
    return this.name;
  }

  public Movement checkMovePiece(int currPlayer, List<Position> positions, String[][] board) {
    //TO DO: Replace fromX, fromy, toX, toY with Position object!
    Movement move = new Movement();
    Position cf = new Position();
    Position from = positions.get(0);
    Position to = positions.get(1);

    // check if it is actually pieces's move
    if(currPlayer == WHITE) {
      if(board[from.getX()][from.getY()] != WH_PIECE) {
        move.setErrorMessage("ERROR: White piece has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    } else if(currPlayer == BLACK) {
      if(board[from.getX()][from.getY()] != BL_PIECE) {
        move.setErrorMessage("ERROR: Black piece has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    }
    
    // check if piece goes outside the board
    if(to.getX() < 1 || to.getX() > 8 || to.getY() < 1 || to.getY() > 8) {
      move.setErrorMessage("ERROR: Move outside the board");
      return move; // return default Movement value (incorrect move)
    }

    if(currPlayer == WHITE) {
      if(to.getX() < from.getX()) {
        if(Math.abs(from.getY()-to.getY()) == 1) { // regular move
          move.setKind(REGULAR);
          move.setCorrectMove(true);
          return move;
        } else if(Math.abs(from.getY()-to.getY()) == 2 && Math.abs(from.getX()-to.getX()) == 2) { // capture move
          move.setKind(CAPTURE);
          if(from.getX()-2 == to.getX() && from.getY()-2 == to.getY()) { //left capture
            if(board[from.getX()-1][from.getY()-1] == BL_KING || board[from.getX()-1][from.getY()-1] == BL_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(from.getX()-1);
              cf.setY(from.getY()-1);
              move.addCapturedFigure(cf);
              return move;
            }
          } else if(from.getX()-2 == to.getX() && from.getX()+2 == to.getY()) { //right capture
            if(board[from.getX()-1][from.getY()+1] == BL_KING || board[from.getX()-1][from.getY()+1] == BL_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(from.getX()-1);
              cf.setY(from.getY()+1);
              move.addCapturedFigure(cf);
              return move;
            }
          } 
        }
      }
    } else if(currPlayer == BLACK) {
      if(to.getX() > from.getX()) {
        if(Math.abs(from.getY()-to.getY()) == 1) {
          move.setKind(REGULAR);
          move.setCorrectMove(true);
          return move;
        } else if(Math.abs(from.getY()-to.getY()) == 2 && Math.abs(from.getX()-to.getX()) == 2) { // capture move
          move.setKind(CAPTURE);
          if(from.getX()+2 == to.getX() && from.getY()-2 == to.getY()) { //left capture
            if(board[from.getX()+1][from.getY()-1] == WH_KING || board[from.getX()+1][from.getY()-1] == WH_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(from.getX()+1);
              cf.setY(from.getY()-1);
              move.addCapturedFigure(cf);
              return move;
            } 
          } else if(from.getX()+2 == to.getX() && from.getY()+2 == to.getY()) { //right capture
            if(board[from.getX()+1][from.getY()+1] == WH_KING || board[from.getX()+1][from.getY()+1] == WH_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(from.getX()+1);
              cf.setY(from.getY()+1);
              move.addCapturedFigure(cf);
              return move;
            }
          }
        }
      } 
    }

    move.setErrorMessage("ERROR: Incorrect move");
    return move; // return default Movement value (incorrect move)
  }

  public Movement checkMoveKing(int currPlayer, List<Position> positions, String[][] board) {
    Movement move = new Movement();
    Position cf = new Position();
    Position from = positions.get(0);
    Position to = positions.get(1);

    //TO DO: Replace fromX, fromy, toX, toY with Position object!

    // check if it is actually king's move
    if(currPlayer == WHITE) {
      if(board[from.getX()][from.getY()] != WH_KING) {
        move.setErrorMessage("ERROR: White king has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    } else if(currPlayer == BLACK) {
      if(board[from.getX()][from.getY()] != BL_KING) {
        move.setErrorMessage("ERROR: Black king has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    }

    // check if piece goes outside the board
    if(to.getX() < 1 || to.getX() > 8 || to.getY() < 1 || to.getY() > 8) {
      move.setErrorMessage("ERROR: Move outside the board");
      return move; // return default Movement value (incorrect move)
    }
    
    int blackFiguresCounter=0;
    int whiteFiguresCounter=0;

    int startX=0, endX=0, startY=0, endY=0;

    if(Math.abs(from.getX()-from.getY()) == Math.abs(to.getX()-to.getY())) { //"falling" diagonal - move
      if(to.getX() > from.getX()) { // forward move 
        startX = from.getX()+1;
        endX = to.getX();
        startY = from.getY()+1;
        endY = to.getY();
      } else if(to.getX() < from.getX()) { // backward move
        startX = to.getX()+1;
        endX = from.getX();
        startY = to.getY()+1;
        endY = from.getY();
      }

      for(int i=startX; i<endX; i++) {
        for(int j=startY; j<endY; j++) {
          if(board[i][j] == BL_KING || board[i][j] == BL_PIECE) {
            blackFiguresCounter++;

            if(currPlayer == WHITE) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
            }
          } else if(board[i][j] == WH_KING || board[i][j] == WH_PIECE) {
            whiteFiguresCounter++;

            if(currPlayer == BLACK) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
            }
          }
        }
      }

    } else if(from.getX()+from.getY() == to.getX()+to.getY()) { //"rising" diagonal
      if(to.getX() > from.getX()) { // forward move 
        startX = from.getX()+1;
        endX = to.getX();
        startY = from.getY()-1;
        endY = to.getY();
      } else if(to.getX() < from.getX()) { // backward move
        startX = to.getX()+1;
        endX = from.getX();
        startY = to.getY()-1;
        endY = from.getY();
      }

      for(int i=startX; i<endX; i++) {
        for(int j=startY; j>endY; j--) {
          if(board[i][j] == BL_KING || board[i][j] == BL_PIECE) {
            blackFiguresCounter++;

            if(currPlayer == WHITE) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
            }
          } else if(board[i][j] == WH_KING || board[i][j] == WH_PIECE) {
            whiteFiguresCounter++;

            if(currPlayer == BLACK) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
            }
          }
        }
      }
    }

    
    if(currPlayer == WHITE) {
      if(whiteFiguresCounter == 0) {
        if(blackFiguresCounter == 1) {
          move.setKind(CAPTURE);
          move.setCorrectMove(true);
          move.addCapturedFigure(cf);
          return move;
        }

        move.setKind(REGULAR);
        move.setCorrectMove(true);
        return move;
      }
    } else if(currPlayer == BLACK) {
      if(blackFiguresCounter == 0) {
        if(whiteFiguresCounter == 1) {
          move.setKind(CAPTURE);
          move.setCorrectMove(true);
          move.addCapturedFigure(cf);
          return move;
        }

        move.setKind(REGULAR);
        move.setCorrectMove(true);
        return move;
      }
    }

    move.setErrorMessage("ERROR: Incorrect move");
    return move; // return default Movement value (incorrect move)
  }

  public Movement checkMultiCapture(int currPlayer, List<Position> positions, String[][] board) {
    Movement move = new Movement();
    Position cf = new Position();
    Position start;
    start = positions.get(0);

     // check if it is actually pieces's move
     if(currPlayer == WHITE) {
      if(board[start.getX()][start.getY()] != WH_PIECE) {
        move.setErrorMessage("ERROR: White piece has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    } else if(currPlayer == BLACK) {
      if(board[start.getX()][start.getY()] != BL_PIECE) {
        move.setErrorMessage("ERROR: Black piece has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    }
    
    for(Position end : positions.subList(1, positions.size())) { //-1
      System.out.println("START X: " + start.getX() + " Y: " + start.getY() + " END X: " + end.getX() + " Y: " + end.getY());
      // check if piece goes outside the board
      if(end.getX() < 1 || end.getX() > 8 || end.getY() < 1 || end.getY() > 8) {
        move.setErrorMessage("ERROR: Move outside the board");
        return move; // return default Movement value (incorrect move)
      }

      if(currPlayer == WHITE) {
        if(Math.abs(start.getY()-end.getY()) == 2 && Math.abs(start.getX()-end.getX()) == 2) { // capture move
          move.setKind(CAPTURE);
          if(start.getX()-2 == end.getX() && start.getY()-2 == end.getY()) { //left capture
            if(board[start.getX()-1][start.getY()-1] == BL_KING || board[start.getX()-1][start.getY()-1] == BL_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(start.getX()-1);
              cf.setY(start.getY()-1);
              move.addCapturedFigure(cf);
            }
          } else if(start.getX()-2 == end.getX() && start.getY()+2 == end.getY()) { //right capture
            if(board[start.getX()-1][start.getY()+1] == BL_KING || board[start.getX()-1][start.getY()+1] == BL_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(start.getX()-1);
              cf.setY(start.getY()+1);
              move.addCapturedFigure(cf);
            }
          } 
        }
      } else if(currPlayer == BLACK) {
        if(Math.abs(start.getY()-end.getY()) == 2 && Math.abs(start.getX()-end.getX()) == 2) { // capture move
          move.setKind(CAPTURE);
          if(start.getX()+2 == end.getX() && start.getY()-2 == end.getY()) { //left capture
            if(board[start.getX()+1][start.getY()-1] == WH_KING || board[start.getX()+1][start.getY()-1] == WH_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(start.getX()+1);
              cf.setY(start.getY()-1);
              move.addCapturedFigure(cf);
            } 
          } else if(start.getX()+2 == end.getX() && start.getY()+2 == end.getY()) { //right capture
            if(board[start.getX()+1][start.getY()+1] == WH_KING || board[start.getX()+1][start.getY()+1] == WH_PIECE) {
              move.setCorrectMove(true);
              cf = new Position();
              cf.setX(start.getX()+1);
              cf.setY(start.getY()+1);
              move.addCapturedFigure(cf);
            }
          }
        }
      }

      if(move.getCorrectMove() == false) {
        move.setErrorMessage("ERROR: Wrong move!");
        return move; // return default Movement value (incorrect move)
      }

      start = end;
    }

    return move;
  }

  public void isCapturePossible() {
    //TO DO: Implement this method
  }

  public int whoStarts() {
    return WHITE;
  }
}
