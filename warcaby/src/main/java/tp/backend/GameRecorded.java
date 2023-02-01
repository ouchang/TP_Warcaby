package tp.backend;

import java.util.List;

public class GameRecorded implements ICommand {
  private String gameID;
  private List<String> serializedGameStatus;

  public String getGameID() {
    return this.gameID;
  }

  public void setGameID(String gameID) {
    this.gameID = gameID;
  }

  public List<String> getSerializedGameStatus() {
    return this.serializedGameStatus;
  }

  public void setSerializedGameStatus(List<String> serializedGameStatus) {
    this.serializedGameStatus = serializedGameStatus;
  }
}
