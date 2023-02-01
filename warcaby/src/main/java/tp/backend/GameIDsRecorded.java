package tp.backend;

import java.util.List;

public class GameIDsRecorded implements ICommand{
  List<String> serializedRecordedGameIDs;

  public List<String> getSerializedRecordedGameIDs() {
    return this.serializedRecordedGameIDs;
  }

  public void setSerializedRecordedGameIDs(List<String> serializedRecordedGameIDs) {
    this.serializedRecordedGameIDs = serializedRecordedGameIDs;
  }
}
