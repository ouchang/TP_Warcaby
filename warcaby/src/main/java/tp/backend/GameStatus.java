package tp.backend;

public class GameStatus implements ICommand {
  String playerId;
  String id;
  String friendly_name;
  String gameKind;
  String player1;
  String player2;
  String activePlayerID;
  String status;
  String error;
  String[][] board;

  public GameStatus() {
    this.playerId = "";
    this.id = "";
    this.friendly_name = "";
    this.gameKind = "";
    this.player1 = "";
    this.player2 = "";
    this.activePlayerID = "";
    this.status = "";
    this.error = "";
    this.board = null;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String getPlayerId() {
    return this.playerId;
  }

  public void setFriendly_name(String friendly_name) {
    this.friendly_name = friendly_name;
  }

  public String getFriendly_name() {
    return this.friendly_name;
  }

  public void setPlayer1(String player1) {
    this.player1 = player1;
  }

  public String getPlayer1() {
    return this.player1;
  }

  public void setPlayer2(String player2) {
    this.player2 = player2;
  }

  public String getPlayer2() {
    return this.player2;
  }

  public void setGameKind(String gameKind) {
    this.gameKind = gameKind;
  }

  public String getGameKind() {
    return gameKind;
  }

  public void setActivePlayerID(String activePlayerID) {
    this.activePlayerID = activePlayerID;
  }

  public String getActivePlayerID() {
    return this.activePlayerID;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getError() {
    return this.error;
  }

  public void setBoard(String[][] board) {
    this.board = board;
  }

  public String[][] getBoard() {
    return board;
  }

  public String showView() {
    return id + "|" + friendly_name + "|" + player1 + "|" + player2 + "| GK: " +  gameKind + "|" + activePlayerID  + "|" + status + "|" + error + "|" + board;
  }
}
