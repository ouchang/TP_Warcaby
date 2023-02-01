package tp.backend;

/**
 * Command's factory
 */
public class CommandFactory {
  public static ICommand getCommand(String type) {
    switch(type) {
      case "MoveCommand":
        return new MoveCommand();
      case "GameStatus":
        return new GameStatus();
      case "GetCurrentGameStateClass":
        return new GetCurrentGameStateClass();
      case "ClientInit":
        return new ClientInit();
      case "GameKind":
        return new GameKind();
      case "GameRecorded":
        return new GameRecorded();
      case "GameIDsRecorded":
        return new GameIDsRecorded();
    }
    return null;
  }

  public static Class getClass(String type) { 
    switch(type) {
      case "GameStatus":
        return (new GameStatus()).getClass();
      case "GetCurrentGameStateClass":
        return (new GetCurrentGameStateClass()).getClass();
      case "MoveCommand":
        return (new MoveCommand()).getClass();
      case "ClientInit":
        return (new ClientInit()).getClass();
      case "GameKind":
        return (new GameKind()).getClass();
      case "GameRecorded":
        return (new GameRecorded()).getClass();
      case "GameIDsRecorded":
        return (new GameIDsRecorded()).getClass();
      default:
        return null;
    }
  }
}
