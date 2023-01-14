package tp.backend;

import java.util.UUID;

public class GameNew {
  private int numOfPlayers;

  private IGameKind gameKind;
  private String[][] board;
  private int boardSize;

  private String player1ID;
  private String player2ID;

  private String activePlayerID;

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

  public void setGameKind(IGameKind gameKind) {
    this.gameKind = gameKind;
  }

  public String getPlayer1ID() {
    return this.player1ID;
  }

  public String getPlayer2ID() {
    return this.player2ID;
  }

  public String getGameKindName() {
    return this.gameKind.getClass().getSimpleName();
  }

  public String getActivePlayerID() {
    return this.activePlayerID;
  }

  public String[][] getBoard() {
    return this.board;
  }

  public int getNumOfPlayers() {
    return this.numOfPlayers;
  }

  public String addPlayer() {
    UUID uuid = UUID.randomUUID();
    
    if(numOfPlayers == 0) {
      this.player1ID = uuid.toString();
      this.activePlayerID = this.player1ID;
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
