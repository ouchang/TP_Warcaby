package tp.backend;

import java.util.List;

/**
 * Interface of game's kind
 * Used for depedency injection
 */
public interface IGameKind {
  int getBoardSize();
  String[][] getGameBoard();
  boolean getCapturedRequired();
  Movement checkMovePiece(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMoveKing(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMultiCapturePiece(int currPlayer, List<Position> positions, String[][] board);
  Movement checkMultiCaptureKing(int currPlayer, List<Position> positions, String[][] board);
  boolean isCapturePossible(int currPlayer, String[][] board);
  boolean hasPieceUpgrade(int currPlayer, Position to);
  int whoStarts();
  String getName();
}
