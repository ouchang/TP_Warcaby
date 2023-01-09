package tp.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.io.IOException;

class CommandFactory {

  static ICommand getInstance(String type) {
    switch(type) {
      case "gameStatus":
        return (new GameStatusClass ());
      case "getGames":
        return (new GetGamesClass ());
      case "joinGame":
        return (new JoinGameClass ());
      case "getCurrentGameState":
        return (new GetCurrentGameStateClass ());
      case "newGame":
        return (new NewGameClass ());
      case "gameCommand":
        return (new GameCommandClass ());
      default:
        return null;
    }
  }

  static Class getClass(String type) { 
    switch(type) {
      case "gameStatus":
        return (new GameStatusClass ()).getClass();
      case "getGames":
        return (new GetGamesClass ()).getClass();
      case "joinGame":
        return (new JoinGameClass ()).getClass();
      case "getCurrentGameState":
        return (new GetCurrentGameStateClass ()).getClass();
      case "newGame":
        return (new NewGameClass ()).getClass();
      case "gameCommand":
        return (new GameCommandClass ()).getClass();
      default:
        return null;
    }
  }
}

class CoderDecoder {

  ICommand decodeCommand(String json, String type) {
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      ICommand command = CommandFactory.getInstance(type);
      command = (ICommand) mapper.readValue(json, CommandFactory.getClass(type));
      return command;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
  

  String codeCommand(ICommand object) { // void
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      String commandAsString;
      commandAsString = mapper.writeValueAsString(object);
      return commandAsString;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
