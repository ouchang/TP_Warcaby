package tp.backend;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IsCapturePossibleTest {
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
  public void isCapturePossibleTest1() { // possible capture
      int boardSize = 8;
      String[][] board = makeBoard(boardSize);

      // preparing arguments
      CzechKind CK = new CzechKind();
      board[3][6] = BL_PIECE;
      board[4][5] = WH_PIECE;
      board[6][5] = WH_PIECE;

      // Test
      assertEquals(true, CK.isCapturePossible(BLACK, board));
  }

  @Test
    public void isCapturePossibleTest2() { // no capture is possible
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);

        // preparing arguments
        CzechKind CK = new CzechKind();
        board[4][5] = WH_PIECE;
        board[6][7] = BL_PIECE;

        // Test
        assertEquals(false, CK.isCapturePossible(BLACK, board));
    }
}
