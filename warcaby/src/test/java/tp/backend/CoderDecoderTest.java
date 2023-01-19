package tp.backend;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CoderDecoderTest {
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
    public void checkCoderDecoder() {
        int boardSize = 8;
        String[][] board = makeBoard(boardSize);
        GameStatus gameStatus = new GameStatus();
        CzechKind CK = new CzechKind();

        CoderDecoder CD = new CoderDecoder();
        
        //Temporary inicializing statusCommand
        gameStatus.setId("12");
        gameStatus.setFriendly_name("testGame1");
        gameStatus.setGameKind(CK.getName());
        gameStatus.setPlayer1("Ola");
        gameStatus.setPlayer2("Aga");
        gameStatus.setActivePlayerID(String.valueOf(CK.whoStarts()));
        gameStatus.setStatus("ongoing");
        gameStatus.setError("");
        gameStatus.setBoard(board);

        String json = CD.codeCommand(gameStatus);
        GameStatus outputStatus = (GameStatus) CD.decodeCommand(json);

        assertEquals(gameStatus.getId(), outputStatus.getId());
        assertEquals(gameStatus.getFriendly_name(), outputStatus.getFriendly_name());
        assertEquals(gameStatus.getGameKind(), outputStatus.getGameKind());
        assertEquals(gameStatus.getPlayer1(), outputStatus.getPlayer1());
        assertEquals(gameStatus.getPlayer2(), outputStatus.getPlayer2());
        assertEquals(gameStatus.getActivePlayerID(), outputStatus.getActivePlayerID());
        assertEquals(gameStatus.getStatus(), outputStatus.getStatus());
        assertEquals(gameStatus.getError(), outputStatus.getError());

        String gameStatusBoard[][] = gameStatus.getBoard();
        String outputStatusBoard[][] = outputStatus.getBoard();
        for(int i=0; i<=boardSize; i++) {
            for(int j=0; j<=boardSize; j++) {
                assertEquals(gameStatusBoard[i][j], outputStatusBoard[i][j]);
            }
        }
    }
}
