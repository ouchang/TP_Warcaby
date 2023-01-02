package tp;

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

  public Movement checkMovePiece(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board) {
    Movement move = new Movement();

    // check if it is actually pieces's move
    if(currPlayer == WHITE) {
      if(board[fromX][fromY] != WH_PIECE) {
        return move; // return default Movement value (incorrect move)
      }
    } else if(currPlayer == BLACK) {
      if(board[fromX][fromY] != BL_PIECE) {
        return move; // return default Movement value (incorrect move)
      }
    }
    
    // check if piece goes outside the board
    if(toX < 1 || toX > 8 || toY < 1 || toY > 8) {
      return move; // return default Movement value (incorrect move)
    }

    if(currPlayer == WHITE) {
      if(toX < fromX) {
        if(Math.abs(fromY-toY) == 1) { // regular move
          move.setKind(REGULAR);
          move.setCorrectMove(true);
          return move;
        } else if(Math.abs(fromY-toY) == 2 && Math.abs(fromX-toX) == 2) { // capture move
          move.setKind(CAPTURE);
          if(board[fromX-1][fromY-1] == BL_KING || board[fromX-1][fromY-1] == BL_PIECE) {
            move.setCorrectMove(true);
            move.setCapturedFigureX(fromX-1);
            move.setCapturedFigureY(fromY-1);
            return move;
          } else if(board[fromX-1][fromY+1] == BL_KING || board[fromX-1][fromY+1] == BL_PIECE) {
            move.setCorrectMove(true);
            move.setCapturedFigureX(fromX-1);
            move.setCapturedFigureY(fromY+1);
            return move;
          }
        }
      }
    } else if(currPlayer == BLACK) {
      if(toX > fromX) {
        if(Math.abs(fromY-toY) == 1) {
          move.setKind(REGULAR);
          move.setCorrectMove(true);
          return move;
        }
      } else if(Math.abs(fromY-toY) == 2 && Math.abs(fromX-toX) == 2) { // capture move
        move.setKind(CAPTURE);
        if(board[fromX+1][fromY-1] == WH_KING || board[fromX+1][fromY-1] == WH_PIECE) {
          move.setCorrectMove(true);
          return move;
        } else if(board[fromX+1][fromY+1] == WH_KING || board[fromX+1][fromY+1] == WH_PIECE) {
          move.setCorrectMove(true);
          return move;
        }
      }
    }

    return move; // return default Movement value (incorrect move)
  }

  public Movement checkMoveKing(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board) {
    Movement move = new Movement();

    // check if it is actually king's move
    if(currPlayer == WHITE) {
      if(board[fromX][fromY] != WH_KING) {
        return move; // return default Movement value (incorrect move)
      }
    } else if(currPlayer == BLACK) {
      if(board[fromX][fromY] != BL_KING) {
        return move; // return default Movement value (incorrect move)
      }
    }

    // check if piece goes outside the board
    if(toX < 1 || toX > 8 || toY < 1 || toY > 8) {
      return move; // return default Movement value (incorrect move)
    }
    
    int blackFiguresCounter=0;
    int whiteFiguresCounter=0;

    int captureFigureX=0, captureFigureY=0;

    int startX=0, endX=0, startY=0, endY=0;

    if(Math.abs(fromX-fromY) == Math.abs(toX-toY)) { //"falling" diagonal - move
      if(toX > fromX) { // forward move 
        startX = fromX+1;
        endX = toX;
        startY = fromY+1;
        endY = toY;
      } else if(toX < fromX) { // backward move
        startX = toX+1;
        endX = fromX;
        startY = toY+1;
        endY = fromY;
      }

      for(int i=startX; i<endX; i++) {
        for(int j=startY; j<endY; j++) {
          if(board[i][j] == BL_KING || board[i][j] == BL_PIECE) {
            blackFiguresCounter++;

            if(currPlayer == WHITE) {
              captureFigureX = i;
              captureFigureY = j;
            }
          } else if(board[i][j] == WH_KING || board[i][j] == WH_PIECE) {
            whiteFiguresCounter++;

            if(currPlayer == BLACK) {
              captureFigureX = i;
              captureFigureY = j;
            }
          }
        }
      }
    } else if(fromX+fromY == toX+toY) { //"rising" diagonal
      if(toX > fromX) { // forward move 
        startX = fromX+1;
        endX = toX;
        startY = fromY-1;
        endY = toY;
      } else if(toX < fromX) { // backward move
        startX = toX+1;
        endX = fromX;
        startY = toY-1;
        endY = fromY;
      }

      for(int i=startX; i<endX; i++) {
        for(int j=startY; j>endY; j--) {
          if(board[i][j] == BL_KING || board[i][j] == BL_PIECE) {
            blackFiguresCounter++;

            if(currPlayer == WHITE) {
              captureFigureX = i;
              captureFigureY = j;
            }
          } else if(board[i][j] == WH_KING || board[i][j] == WH_PIECE) {
            whiteFiguresCounter++;

            if(currPlayer == BLACK) {
              captureFigureX = i;
              captureFigureY = j;
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
          move.setCapturedFigureX(captureFigureX);
          move.setCapturedFigureY(captureFigureY);
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
          move.setCapturedFigureX(captureFigureX);
          move.setCapturedFigureY(captureFigureY);
          return move;
        }

        move.setKind(REGULAR);
        move.setCorrectMove(true);
        return move;
      }
    }

    return move; // return default Movement value (incorrect move)
  }

  public void isCapturePossible() {
    //TO DO: Implement this method
  }

  public int whoStarts() {
    return WHITE;
  }
}
