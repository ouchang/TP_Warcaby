package tp.backend;

import java.util.UUID;

public class GameNew {
  private int numOfPlayers;

  private IGameKind gameKind;
  private String[][] board;
  private int boardSize;

  private String player1ID;
  private String player2ID;

  // Player's ID
  private final static int WHITE=1;
  private final static int BLACK=2;

  // Figure's type
  private static final int PIECE=0;
  private static final int KING=1;

  // Figure's ID
  private static final String EMPTY="0";
  private static final String WH_PIECE="1";
  private static final String WH_KING="2";
  private static final String BL_PIECE="3";
  private static final String BL_KING="4";

  // Move's type
  private static final String REGULAR = "REGULAR";
  private static final String CAPTURE = "CAPTURE";

  GameNew() {
    this.numOfPlayers = 0;
  }

  public String getPlayer1D() {
    return this.player1ID;
  }

  public String getPlayer2D() {
    return this.player2ID;
  }

  public int getNumOfPlayers() {
    return this.numOfPlayers;
  }

  public String addPlayer() {
    UUID uuid = UUID.randomUUID();
    
    if(numOfPlayers == 0) {
      this.player1ID = uuid.toString();
      this.numOfPlayers++;
      return this.player1ID;
    } else if(numOfPlayers == 1) {
      this.player2ID = uuid.toString();
      this.numOfPlayers++;
      return this.player2ID;
    }

    return ""; //FIXME
  }

}
