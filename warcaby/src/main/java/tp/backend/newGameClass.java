package tp.backend;

class newGameClass implements ICommand {
  String actor;
  String gameKind;

  String getActor() {
    return actor;
  }

  void setActor(String actor) {
    this.actor = actor;
  }

  String getGameKind() {
    return gameKind;
  }

  void setGameKind(String gameKind) {
    this.gameKind = gameKind;
  }

  public String showView() {
    return actor + "|" + gameKind;
  }
}
