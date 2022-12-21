package tp;

public class CzechKind implements IGameKind {
  private static final int WHITE=1;
  private static final int BLACK=2;
  private int boardSize;
  private String name;

  CzechKind() {
    this.name = "Czech";
    this.boardSize = 8;
  }

  public int getBoardSize() {
    return boardSize;
  }

  public String getName() {
    return this.name;
  }

  public boolean checkMovePiece(int currPlayer, int fromX, int fromY, int toX, int toY) {
    if(currPlayer == WHITE) {
      System.out.println("CHECK_MOVE_P WHITE");
      if(toX < fromX) {
        System.out.println("CHECK_MOVE_P toX < fromX");
        if(Math.abs(fromY-toY) == 1) {
          System.out.println("CHECK_MOVE_P abs(fromY-toY)=1");
          return true;
        }
      }
    } else if(currPlayer == BLACK) {
      System.out.println("CHECK_MOVE_P BLACK");
      if(toX > fromX) {
        System.out.println("CHECK_MOVE_P toX > fromX");
        if(Math.abs(fromY-toY) == 1) {
          System.out.println("CHECK_MOVE_P abs(fromY-toY)=1");
          return true;
        }
      }
    }

    return false;
  }

  public boolean checkMoveKing(int fromX, int fromY, int toX, int toY) {
    if(Math.abs(fromX-fromY) == Math.abs(toX-toY)) {
      return true; //"rising" diagonal
    } else if(fromX+fromY == toX+toY) {
      return true; //"falling" diagonal
    }

    return false;
  }

  public void isCapturePossible() {
    //TO DO: Implement this method
  }

  public int whoStarts() {
    return WHITE;
  }
}
