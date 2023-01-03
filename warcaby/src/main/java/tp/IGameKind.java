package tp;

import java.util.List;

interface IGameKind {
  // TO DO: Change methods' output type

  int getBoardSize();
  String[][] getGameBoard();
  Movement checkMovePiece(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMoveKing(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMultiCapture(int currPlayer, List<Position> positions, String[][] board);
  void isCapturePossible();
  int whoStarts();
  String getName();
}
