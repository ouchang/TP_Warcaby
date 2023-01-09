package tp.backend;

import tp.backend.position.Position;

import java.util.List;

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

  public Movement checkMovePiece(int currPlayer, List<Position> positions, String[][] board) {
    return new Movement();
  }

  public Movement checkMoveKing(int currPlayer, List<Position> positions, String[][] board) {
    return new Movement();
  }

  public Movement checkMultiCapturePiece(int currPlayer, List<Position> positions, String[][] board) {
    return new Movement();
  }

  public Movement checkMultiCaptureKing(int currPlayer, List<Position> positions, String[][] board) {
    return new Movement();
  }

  public boolean isCapturePossible(int currPlayer, String[][] board) {
    return false;
  }

  public int whoStarts() {
    return 0;
  }
}
