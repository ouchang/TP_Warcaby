package tp.backend;

public class WhoStartsClass implements ICommand {
  private String startingPlayer;

  public void setStartingPlayer(String startingPlayer) {
    this.startingPlayer = startingPlayer;
  }

  public String getStartingPlayer() {
    return this.startingPlayer;
  }

  public String showView() {
    return "";
  }
}
