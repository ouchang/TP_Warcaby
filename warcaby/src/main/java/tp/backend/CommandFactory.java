package tp.backend;

public class CommandFactory {
  public static ICommand getCommand(String type) {
    switch(type) {
      case "GetGamesClass":
        return new GetGamesClass();
      case "GameCommandClass":
        return new GameCommandClass();
      case "GameStatusClass":
        return new GameStatusClass();
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
      case "GameStatusClass":
        return (new GameStatusClass()).getClass();
      case "GetGamesClass":
        return (new GetGamesClass()).getClass();
      case "JoinGameClass":
        return (new JoinGameClass()).getClass();
      case "GetCurrentGameStateClass":
        return (new GetCurrentGameStateClass()).getClass();
      case "NewGameClass":
        return (new NewGameClass()).getClass();
      case "GameCommandClass":
        return (new GameCommandClass()).getClass();
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
