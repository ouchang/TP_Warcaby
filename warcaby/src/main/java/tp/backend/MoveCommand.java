package tp.backend;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements ICommand {
  String playerId;
  //int pieceId; // 0 - piece, 1 - king
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
/*
  void setPieceId(int pieceId) {
    this.pieceId = pieceId;
  }

  int getPieceId() {
    return this.pieceId;
  }
*/
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

  public String showView() { // TO DO: Delete this
    String w = playerId + "| ";
    for(Position p : positions) {
      w += String.valueOf(p.getX()) + " ";
      w += String.valueOf(p.getY()) + "| ";
    }

    return w;
  }
}
