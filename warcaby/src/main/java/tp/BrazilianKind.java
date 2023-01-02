package tp;

public class BrazilianKind implements IGameKind {
  //TO DO: Implement methods
  public String getName() {
    return "";
  }

  public String[][] getGameBoard() {
    return null;
  }

  public int getBoardSize() {
    return 0;
  }

  public Movement checkMovePiece(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board) {
    return new Movement();
  }

  public Movement checkMoveKing(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board) {
    return new Movement();
  }

  public void isCapturePossible() {

  }

  public int whoStarts() {
    return 0;
  }
}
