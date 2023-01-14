package tp.backend;

public class ClientInit implements ICommand {
  private String playerId;
  private boolean firstPlayer;
  private String color;

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public void setFirstPlayer(boolean firstPlayer) {
    this.firstPlayer = firstPlayer;
  }

  public void setColour(String color) {
    this.color = color;
  }

  public String getPlayerId() {
    return this.playerId;
  }

  public boolean getFirstPlayer() {
    return this.firstPlayer;
  }

  public String getColor() {
    return this.color;
  }

  public String showView() {
    return "";
  }
}
