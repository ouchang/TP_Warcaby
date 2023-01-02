package tp;

interface IGameKind {
  // TO DO: Change methods' output type

  int getBoardSize();
  String[][] getGameBoard();
  Movement checkMovePiece(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board);
  Movement checkMoveKing(int currPlayer, int fromX, int fromY, int toX, int toY, String[][] board);
  void isCapturePossible();
  int whoStarts();
  String getName();
}
