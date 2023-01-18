package tp.backend;

import java.util.List;

/**
 * MVC - Controller
 * 
 * Class CzechKind determines rules in Czech version of checkers
 */
public class CzechKind implements IGameKind {
  private static final int WHITE=1; // WHITE Player ID
  private static final int BLACK=2; // BLACK Player ID

  private static int boardSize = 8;
  private String name;
  private String[][] gameBoard;
  private boolean capturedRequired; // tells if we need to use isCapturePossible()
  
  private static final int startingRowsWithFigures = 3;

  // Figure ID
  private static final String EMPTY="0";
  private static final String WH_PIECE="1";
  private static final String WH_KING="2";
  private static final String BL_PIECE="3";
  private static final String BL_KING="4";

  // Type of move
  private static final String REGULAR = "REGULAR";
  private static final String CAPTURE = "CAPTURE";

  /**
   * Constructor. 
   * Within this method, the gameBoard is initialized.
   */
  CzechKind() {
    this.name = "Czech";
    this.gameBoard = new String[boardSize+1][boardSize+1];
    this.capturedRequired = true;

    // Initial board fill (startingPlayer POV)
    for(int i=0; i<=boardSize; i++) {
      for(int j=0; j<=boardSize; j++) {
        gameBoard[i][j] = EMPTY;

        if(i == 0 || j == 0) {
          continue;
        }

        if(i >= 1 && i <= startingRowsWithFigures) { // BLACK side
          if(i%2 == 1 && j%2 == 0) {
            gameBoard[i][j] = BL_PIECE;
          } else if(i%2 == 0 && j%2 == 1) {
            gameBoard[i][j] = BL_PIECE;
          }
        } else if(i > boardSize-startingRowsWithFigures && i <= boardSize) { // WHITE side
          if(i%2 == 0  && j%2 == 1) {
            gameBoard[i][j] = WH_PIECE;
          } else if(i%2 == 1 && j%2 == 0) {
            gameBoard[i][j] = WH_PIECE;
          }
        }
      }
    }
  }

  // Setter & Getters

  public String[][] getGameBoard() {
    return this.gameBoard;
  }

  public int getBoardSize() {
    return boardSize;
  }

  public String getName() {
    return this.name;
  }

  public boolean getCapturedRequired() {
    return this.capturedRequired;
  }

  /**
   * Method checkMovePiece checks the correctness of the move, made by piece. 
   * This methods checks regular move as well as single capture
   * @param currPlayer ID of the player who made move
   * @param positions player's clicked positions
   * @param board game's board
   * @return movement's output information
   */
  public Movement checkMovePiece(int currPlayer, List<Position> positions, String[][] board) {
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
    
    //Check if distination is empty
    if(board[to.getX()][to.getY()] != EMPTY) {
      move.setErrorMessage("ERROR: This field is occupied!");
      return move; // return default Movement value (incorrect move)
    }

    // check if piece goes outside the board
    if(to.getX() < 1 || to.getX() > boardSize || to.getY() < 1 || to.getY() > boardSize) {
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
          } else if(from.getX()-2 == to.getX() && from.getY()+2 == to.getY()) { //right capture
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
      if(to.getX() > from.getX()) { // regular move
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

  /**
   * Method checkMoveKing checks the correctness of the move, made by king.
   * This methods checks regular move as well as single capture 
   * @param currPlayer ID of the player who made move
   * @param positions player's clicked positions
   * @param board game's board
   * @return movement's output information
   */
  public Movement checkMoveKing(int currPlayer, List<Position> positions, String[][] board) {
    Movement move = new Movement();
    Position cf = new Position(); // captured figure
    Position from = positions.get(0);
    Position to = positions.get(1);

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

    //Check if distination is empty
    if(board[to.getX()][to.getY()] != EMPTY) {
      move.setErrorMessage("ERROR: This field is occupied!");
      return move; // return default Movement value (incorrect move)
    }

    // check if piece goes outside the board
    if(to.getX() < 1 || to.getX() > boardSize || to.getY() < 1 || to.getY() > boardSize) {
      move.setErrorMessage("ERROR: Move outside the board");
      return move; // return default Movement value (incorrect move)
    }
    
    int blackFiguresCounter=-1;
    int whiteFiguresCounter=-1;

    int startX=0, endX=0, startY=0, endY=0;

    if(Math.abs(from.getX()-from.getY()) == Math.abs(to.getX()-to.getY()) && from.getX() != to.getY()) { //"falling" diagonal - move 
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

      int i = startX;
      int j = startY;

      blackFiguresCounter=0;
      whiteFiguresCounter=0;
      while(i < endX && j < endY) {
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

        i++;
        j++;
      }

    } else if(from.getX()+from.getY() == to.getX()+to.getY()) { //"rising" diagonal - move
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

      int i = startX;
      int j = startY;

      blackFiguresCounter=0;
      whiteFiguresCounter=0;
      while(i < endX && j > endY) {
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

        i++;
        j--;
      }
    }

    if(currPlayer == WHITE) {
      if(whiteFiguresCounter == 0) {
        if(blackFiguresCounter == 1) {
          move.setKind(CAPTURE);
          move.setCorrectMove(true);
          move.addCapturedFigure(cf);
          return move;
        } else if(blackFiguresCounter == 0) {
          move.setKind(REGULAR);
          move.setCorrectMove(true);
          return move;
        }
      }
    } else if(currPlayer == BLACK) {
      if(blackFiguresCounter == 0) {
        if(whiteFiguresCounter == 1) {
          move.setKind(CAPTURE);
          move.setCorrectMove(true);
          move.addCapturedFigure(cf);
          return move;
        } else if(whiteFiguresCounter == 0) {
          move.setKind(REGULAR);
          move.setCorrectMove(true);
          return move;
        }
      }
    }

    move.setErrorMessage("ERROR: Incorrect move");
    return move; // return default Movement value (incorrect move)
  }

  /**
   * Method checkMultiCapturePiece checks the correctness of the multi-capture move made by piece.
   * @param currPlayer ID of the player who made move
   * @param positions player's clicked positions
   * @param board game's board
   * @return movement's output information
   */
  public Movement checkMultiCapturePiece(int currPlayer, List<Position> positions, String[][] board) {
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
    
    for(Position end : positions.subList(1, positions.size())) { 
      move.setCorrectMove(false);

      // check if piece goes outside the board
      if(end.getX() < 1 || end.getX() > boardSize || end.getY() < 1 || end.getY() > boardSize) {
        move.setErrorMessage("ERROR: Move outside the board");
        return move; // return default Movement value (incorrect move)
      }

      //Check if distination is empty
      if(board[end.getX()][end.getY()] != EMPTY) {
        move.setErrorMessage("ERROR: This field is occupied!");
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

  /**
   * Method checkMultiCaptureKing checks the correctness of the multi-capture move made by king.
   * @param currPlayer ID of the player who made move
   * @param positions player's clicked positions
   * @param board game's board
   * @return movement's output information
   */
  public Movement checkMultiCaptureKing(int currPlayer, List<Position> positions, String[][] board) {
    Movement move = new Movement();
    Position cf = new Position();
    Position start;
    start = positions.get(0);

    int blackFiguresCounter=-1;
    int whiteFiguresCounter=-1;

    int startX=0, endX=0, startY=0, endY=0;

    // check if it is actually pieces's move
    if(currPlayer == WHITE) {
      if(board[start.getX()][start.getY()] != WH_KING) {
        move.setErrorMessage("ERROR: White piece has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    } else if(currPlayer == BLACK) {
      if(board[start.getX()][start.getY()] != BL_KING) {
        move.setErrorMessage("ERROR: Black piece has not been chosen!");
        return move; // return default Movement value (incorrect move)
      }
    }

    for(Position end : positions.subList(1, positions.size())) {
      move.setCorrectMove(false);
      
      blackFiguresCounter=0;
      whiteFiguresCounter=0;

      startX=0;
      endX=0; 
      startY=0;
      endY=0;

      // check if piece goes outside the board
      if(end.getX() < 1 || end.getX() > boardSize || end.getY() < 1 || end.getY() > boardSize) {
        move.setErrorMessage("ERROR: Move outside the board");
        return move; // return default Movement value (incorrect move)
      }

      //Check if distination is empty
      if(board[end.getX()][end.getY()] != EMPTY) {
        move.setErrorMessage("ERROR: This field is occupied!");
        return move; // return default Movement value (incorrect move)
      }

      // "falling" diagonal - move 
      if(Math.abs(start.getX()-start.getY()) == Math.abs(end.getX()-end.getY()) && start.getX() != end.getY()) {
        if(end.getX() > start.getX()) { // forward move
          startX = start.getX()+1;
          endX = end.getX();
          startY = start.getY()+1;
          endY = end.getY();
        } else if(end.getX() < start.getX()) { // backward move
          startX = end.getX()+1;
          endX = start.getX();
          startY = end.getY()+1;
          endY = start.getY();
        }

        int i = startX;
        int j = startY;
        while(i < endX && j < endY) {
          if(board[i][j] == BL_KING || board[i][j] == BL_PIECE) {
            blackFiguresCounter++;

            if(currPlayer == WHITE) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
              move.addCapturedFigure(cf);
            }
          } else if(board[i][j] == WH_KING || board[i][j] == WH_PIECE) {
            whiteFiguresCounter++;

            if(currPlayer == BLACK) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
              move.addCapturedFigure(cf);
            }
          }

          i++;
          j++;
        }
        
      // "rising" diagonal - move 
      } else if(start.getX()+start.getY() == end.getX()+end.getY() && start.getX() != end.getY()) {
        if(end.getX() > start.getX()) { // forward move 
          startX = start.getX()+1;
          endX = end.getX();
          startY = start.getY()-1;
          endY = end.getY();
        } else if(end.getX() < start.getX()) { // backward move
          startX = end.getX()+1;
          endX = start.getX();
          startY = end.getY()-1;
          endY = start.getY();
        }


        int i = startX;
        int j = startY;
        while(i < endX && j > endY) {
          if(board[i][j] == BL_KING || board[i][j] == BL_PIECE) {
            blackFiguresCounter++;

            if(currPlayer == WHITE) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
              move.addCapturedFigure(cf);
            }
          } else if(board[i][j] == WH_KING || board[i][j] == WH_PIECE) {
            whiteFiguresCounter++;

            if(currPlayer == BLACK) {
              cf = new Position();
              cf.setX(i);
              cf.setY(j);
              move.addCapturedFigure(cf);
            }
          }

          i++;
          j--;
        }
      }

      if(currPlayer == WHITE) {
        if(whiteFiguresCounter == 0) {
          if(blackFiguresCounter == 1) {
            move.setKind(CAPTURE);
            move.setCorrectMove(true);   
          }
        }
      } else if(currPlayer == BLACK) {
        if(blackFiguresCounter == 0) {
          if(whiteFiguresCounter == 1) {
            move.setKind(CAPTURE);
            move.setCorrectMove(true);
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

  /**
   * Method checks if in a given turn there are any possible captures
   * @param currPlayer ID of the player who made move
   * @param board game's board
   * @return true - capture is possible | false - no possible captures
   */
  public boolean isCapturePossible(int currPlayer, String[][] board) {
    String[] playerFigures = new String[2];
    String[] opponentFigures = new String[2];

    int[] moveX = {-1, 1};
    int[] moveY = {-1, 1};
    int mx,my;

    if(currPlayer == WHITE) {
      playerFigures[0] = WH_PIECE;
      playerFigures[1] = WH_KING;

      opponentFigures[0] = BL_PIECE;
      opponentFigures[1] = BL_KING;
    } else if(currPlayer == BLACK) {
      playerFigures[0] = BL_PIECE;
      playerFigures[1] = BL_KING;

      opponentFigures[0] = WH_PIECE;
      opponentFigures[1] = WH_KING;
    }

    for(int x=1; x<=boardSize; x++) {
      for(int y=1; y<=boardSize; y++) {
        if(board[x][y] == playerFigures[0]) { // piece
          for(int i=0; i<2; i++) {

            if(currPlayer == WHITE && moveX[i] == 1) { // backward capture - permitted
              continue;
            } else if(currPlayer == BLACK && moveX[i] == -1) {
              continue;
            }

            for(int j=0; j<2; j++) {
              mx = moveX[i];
              my = moveY[j];

              if(x == 1 && moveX[i] == -1) {
                continue;
              } else if(x == boardSize && moveX[i] == 1) {
                continue;
              } else if(y == 1 && moveY[j] == -1) {
                continue;
              } else if(y == boardSize && moveY[j] == 1) {
                continue;
              }

              if(board[x+mx][y+my] == opponentFigures[0] || board[x+mx][y+my] == opponentFigures[1]) { // oponnent's figure next to me
                if(board[x+2*mx][y+2*my] == EMPTY) { // check if I have space to jump
                  return true; 
                }
              }
            }
          }
        } else if(board[x][y] == playerFigures[1]) { // king
          boolean foundCadidateForCapture = false;

          // "failing" diagonal -> backward capture
          mx = x-1;
          my = y-1;

          while(mx >= 1 && my >= 1) {
            if(board[mx][my] == opponentFigures[0] || board[mx][my] == opponentFigures[1]) {
              foundCadidateForCapture = true; //found cadidate to beat
              break;
            } else if(board[mx][my] == playerFigures[0] || board[mx][my] == playerFigures[1]) {
              break; // we found obstacle!
            }

            mx--;
            my--;
          }

          if(foundCadidateForCapture) {
            if(mx-1 >= 1 && my-1 >= 1 && board[mx-1][my-1] == EMPTY) { // we have space to jump
              return true; 
            }
          }

          // "failing" diagonal -> forward capture
          foundCadidateForCapture = false;

          mx = x+1;
          my = y+1;

          while(mx <= boardSize && my <= boardSize) {
            if(board[mx][my] == opponentFigures[0] || board[mx][my] == opponentFigures[1]) {
              foundCadidateForCapture = true; //found cadidate to beat
              break;
            } else if(board[mx][my] == playerFigures[0] || board[mx][my] == playerFigures[1]) {
              break; // we found obstacle!
            }

            mx++;
            my++;
          }

          if(foundCadidateForCapture) {
            if(mx+1 <= boardSize && my+1 <= boardSize && board[mx+1][my+1] == EMPTY) { // we have space to jump
              return true; 
            }
          }

          // "rising" diagonal -> backward capture
          foundCadidateForCapture = false;

          mx = x-1;
          my = y+1;

          while(mx >= 1 && my <= boardSize) {
            if(board[mx][my] == opponentFigures[0] || board[mx][my] == opponentFigures[1]) {
              foundCadidateForCapture = true; //found cadidate to beat
              break;
            } else if(board[mx][my] == playerFigures[0] || board[mx][my] == playerFigures[1]) {
              break; // we found obstacle!
            }

            mx--;
            my++;
          }

          if(foundCadidateForCapture) {
            if(mx-1 >= 1 && my+1 <= boardSize && board[mx-1][my+1] == EMPTY) { // we have space to jump
              return true;  
            }
          }

          // "rising" diagonal -> forward capture
          foundCadidateForCapture = false;

          mx = x+1;
          my = y-1;

          while(mx <= boardSize && my >= 1) {
            if(board[mx][my] == opponentFigures[0] || board[mx][my] == opponentFigures[1]) {
              foundCadidateForCapture = true; //found cadidate to beat
              break;
            } else if(board[mx][my] == playerFigures[0] || board[mx][my] == playerFigures[1]) {
              break; // we found obstacle!
            }

            mx++;
            my--;
          }

          if(foundCadidateForCapture) {
            if(mx+1 <= boardSize && my-1 >= 1 && board[mx+1][my-1] == EMPTY) { // we have space to jump
              return true; 
            }
          }
        }
      }
    }

    return false; 
  }

  /**
   * Method checks if a piece can be upgraded into a king
   * @param currPlayer ID of the player who made move
   * @param to ending position in player's moe
   * @return true - upgrade possible | false - no possible upgrades
   */
  public boolean hasPieceUpgrade(int currPlayer, Position to) {
    System.out.println("Checking if piece has upgrade");
    if(currPlayer == BLACK) {
      if(to.getX() == boardSize) {
        return true;
      }
    } else if(currPlayer == WHITE) {
      if(to.getX() == 1) {
        return true;
      }
    }
    
    return false;
  }

  /**
   * Method determing who starts the game
   * @return color's ID
   */
  public int whoStarts() {
    return WHITE;
  }
}
