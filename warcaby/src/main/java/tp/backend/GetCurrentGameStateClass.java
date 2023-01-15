package tp.backend;

class GetCurrentGameStateClass implements ICommand {
  String id;
  String playerId;

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPlayerId() {
    return this.playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }
}
