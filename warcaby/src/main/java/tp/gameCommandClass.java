package tp;

class gameCommandClass implements ICommand {
  //String id;
  int actorId;
  int pieceId; // 0 - piece, 1 - king
  //String command;
  int fromX;
  int fromY;
  int toX;
  int toY;

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
}
