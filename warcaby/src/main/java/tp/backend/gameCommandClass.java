package tp.backend;

import java.util.List;
import java.util.ArrayList;

class gameCommandClass implements ICommand {
  //String id;
  int actorId;
  int pieceId; // 0 - piece, 1 - king
  //String command;
  
  //int fromX;
  //int fromY;
  //int toX;
  //int toY;
  List<Position> positions;

  gameCommandClass() {
    actorId = -1;
    pieceId = -1;
    positions = new ArrayList<Position>();
  }

  void setActorId(int actorId) {
    this.actorId = actorId;
  }

  int getActorId() {
    return this.actorId;
  }

  void setPieceId(int pieceId) {
    this.pieceId = pieceId;
  }

  int getPieceId() {
    return this.pieceId;
  }
/*
  void setFromX(int fromX) {
    this.fromX = fromX;
  }

  int getFromX() {
    return fromX;
  }

  void setFromY(int fromY) {
    this.fromY = fromY;
  }

  int getFromY() {
    return fromY;
  }

  void setToX(int toX) {
    this.toX = toX;
  }

  int getToX() {
    return toX;
  }

  void setToY(int toY) {
    this.toY = toY;
  }

  int getToY() {
    return toY;
  }

  public String showView() { // TO DO: Delete this
    return actorId + "|" + fromX + "|" + fromY + "|" + toX + "|" + toY;
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
    String w = actorId + "| ";
    for(Position p : positions) {
      w += String.valueOf(p.getX()) + " ";
      w += String.valueOf(p.getY()) + "| ";
    }

    return w;
  }
}
