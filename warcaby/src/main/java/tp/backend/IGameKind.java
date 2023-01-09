package tp.backend;

import tp.backend.position.Position;

import java.util.List;

public interface IGameKind {
  int getBoardSize();
  String[][] getGameBoard();
  Movement checkMovePiece(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMoveKing(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMultiCapturePiece(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMultiCaptureKing(int currPlayer, List<Position> positions, String[][] board);
  boolean isCapturePossible(int currPlayer, String[][] board);
  int whoStarts();
  String getName();
}
