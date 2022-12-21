package tp;

interface IGameKind {
  // TO DO: Change methods' output type

  int getBoardSize();
  boolean checkMovePiece(int currPlayer, int fromX, int fromY, int toX, int toY);
  boolean checkMoveKing(int fromX, int fromY, int toX, int toY);
  void isCapturePossible();
  int whoStarts();
  String getName();
}
