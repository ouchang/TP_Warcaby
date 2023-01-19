package tp.backend;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultiCaptureKingTest {
  private final static int WHITE=1;
  private final static int BLACK=2;

  private static final String EMPTY="0";
  private static final String WH_PIECE="1";
  private static final String WH_KING="2";
  private static final String BL_PIECE="3";
  private static final String BL_KING="4";

  public String[][] makeBoard(int boardSize) {
      String[][] board = new String[boardSize+1][boardSize+1];

      for(int i=1; i<=boardSize; i++) {
          for(int j=1 ;j<=boardSize; j++) {
              board[i][j] = EMPTY;
          }
      }

      return board;
  }

  @Test
  public void checkMultiCaptureKingTest1() { // white king multi-capture
      int boardSize = 8;
      String[][] board = makeBoard(boardSize);
      Position tmp = new Position();
      List<Position> positions = new ArrayList<Position>();

      // preparing arguments
      CzechKind CK = new CzechKind();

      board[1][8] = WH_KING;
      board[4][5] = BL_KING;
      board[7][4] = BL_PIECE;

      tmp = new Position();
      tmp.setX(1);
      tmp.setY(8);
      positions.add(tmp);

      tmp = new Position();
      tmp.setX(6);
      tmp.setY(3);
      positions.add(tmp);

      tmp = new Position();
      tmp.setX(8);
      tmp.setY(5);
      positions.add(tmp);

      Movement output = CK.checkMultiCaptureKing(WHITE, positions, board);

      // preparing expected output
      List<Position> capturedFigures = new ArrayList<Position>();

      tmp = new Position();
      tmp.setX(4);
      tmp.setY(5);
      capturedFigures.add(tmp);

      tmp = new Position();
      tmp.setX(7);
      tmp.setY(4);
      capturedFigures.add(tmp);

      // Test
      assertEquals(true, output.isEqual("CAPTURE", true, capturedFigures , ""));
  }
}
