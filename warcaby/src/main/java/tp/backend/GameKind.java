package tp.backend;

public class GameKind implements ICommand {
  private String playerId;
  private String kind;

  public void setKind(String kind) {
    this.kind = kind;
  }

  public String getKind() {
    return this.kind;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String setPlayerId() {
    return this.playerId;
  }

  public String showView() {
    return "";
  }
}
