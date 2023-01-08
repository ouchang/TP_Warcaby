package tp.backend;

class gameStatusClass implements ICommand {
  String id;
  String friendly_name;
  String gameKind;
  String player1;
  String player2;
  String turn;
  String status;
  String error;
  String[][] board;

  void setId(String id) {
    this.id = id;
  }

  String getId() {
    return id;
  }

  void setFriendly_name(String friendly_name) {
    this.friendly_name = friendly_name;
  }

  String getFriendly_name() {
    return this.friendly_name;
  }

  void setPlayer1(String player1) {
    this.player1 = player1;
  }

  String getPlayer1() {
    return this.player1;
  }

  void setPlayer2(String player2) {
    this.player2 = player2;
  }

  String getPlayer2() {
    return this.player2;
  }

  void setGameKind(String gameKind) {
    this.gameKind = gameKind;
  }

  String getGameKind() {
    return gameKind;
  }

  void setTurn(String turn) {
    this.turn = turn;
  }

  String getTurn() {
    return this.turn;
  }

  void setStatus(String status) {
    this.status = status;
  }

  String getStatus() {
    return status;
  }

  void setError(String error) {
    this.error = error;
  }

  String getError() {
    return this.error;
  }

  void setBoard(String[][] board) {
    this.board = board;
  }

  String[][] getBoard() {
    return board;
  }

  public String showView() {
    return id + "|" + friendly_name + "|" + player1 + "|" + player2 + "|" +  gameKind + "|" + turn  + "|" + status + "|" + error + "|" + board;
  }
}
