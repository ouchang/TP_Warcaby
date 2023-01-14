package tp.backend;

public class CommandFactory {
  public static ICommand getCommand(String type) {
    switch(type) {
      case "GetGamesClass":
        return new GetGamesClass();
      case "MoveCommand":
        return new MoveCommand();
      case "GameStatus":
        return new GameStatus();
      case "GetCurrentGameStateClass":
        return new GetCurrentGameStateClass();
      case "JoinGameClass":
        return new JoinGameClass();
      case "NewGameClass":
        return new NewGameClass();
      case "ClientInit":
        return new ClientInit();
      case "WhoStartsClass":
        return new WhoStartsClass();
      case "GameKind":
        return new GameKind();
    }
    return null;
  }

  public static Class getClass(String type) { 
    switch(type) {
      case "GameStatus":
        return (new GameStatus()).getClass();
      case "GetGamesClass":
        return (new GetGamesClass()).getClass();
      case "JoinGameClass":
        return (new JoinGameClass()).getClass();
      case "GetCurrentGameStateClass":
        return (new GetCurrentGameStateClass()).getClass();
      case "NewGameClass":
        return (new NewGameClass()).getClass();
      case "MoveCommand":
        return (new MoveCommand()).getClass();
      case "ClientInit":
        return (new ClientInit()).getClass();
      case "WhoStartsClass":
        return (new WhoStartsClass()).getClass();
      case "GameKind":
        return (new GameKind()).getClass();
      default:
        return null;
    }
  }
}
