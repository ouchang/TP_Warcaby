package tp;

public class BrazilianKind implements IGameKind {
  //TO DO: Implement methods
  public String getName() {
    return "";
  }

  public int getBoardSize() {
    return 0;
  }

  public boolean checkMovePiece(int currPlayer, int fromX, int fromY, int toX, int toY) {
    return false;
  }

  public boolean checkMoveKing(int fromX, int fromY, int toX, int toY) {
    return false;
  }

  public void isCapturePossible() {

  }

  public int whoStarts() {
    return 0;
  }
}
