package tp.backend;

/**
 * Factory of game's kinds
 */
public class GameKindFactory {
  public IGameKind getGameKind(String type) {
    switch(type) {
      case "czech":
        return new CzechKind();
      case "german":
        return new GermanKind();
      case "swedish":
        return new SwedishKind();
    }

    return null;
  }
}
