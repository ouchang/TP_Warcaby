package tp.backend;

import java.util.ArrayList;
import java.util.List;

/*
 * Class for informaction about client's movement
 * 
 * A part of communication protocol.
 */
public class MoveCommand implements ICommand {
  String playerId;
  List<Position> positions;

  MoveCommand() {
    positions = new ArrayList<Position>();
  }

  void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  String getPlayerId() {
    return this.playerId;
  }

  public List<Position> getPositions() {
    return positions;
  }

  public void setPositions(List<Position> positions) {
    this.positions = positions;
  }

  public void addPosition(Position p) {
    positions.add(p);
  }

  public void clearPositions() {
    this.positions.clear();
  }
}
