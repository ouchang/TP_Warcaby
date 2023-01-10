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
    }
    return null;
  }

  public static Class getClass(String type) { 
    switch(type) {
      case "GameStatusClass":
        return (new GameStatusClass ()).getClass();
      case "GetGamesClass":
        return (new GetGamesClass ()).getClass();
      case "JoinGameClass":
        return (new JoinGameClass ()).getClass();
      case "GetCurrentGameStateClass":
        return (new GetCurrentGameStateClass ()).getClass();
      case "NewGameClass":
        return (new NewGameClass ()).getClass();
      case "GameCommandClass":
        return (new GameCommandClass ()).getClass();
      default:
        return null;
    }
  }
}
