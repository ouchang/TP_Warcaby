package tp;

import java.util.List;

interface IGameKind {
  // TO DO: Change methods' output type

  int getBoardSize();
  String[][] getGameBoard();
  Movement checkMovePiece(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board);
  Movement checkMoveKing(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board);
  Movement checkMultiCapture(int currPlayer, List<Position> positions, String[][] board);
  void isCapturePossible();
  int whoStarts();
  String getName();
}
