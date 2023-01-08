package tp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.io.IOException;

class CommandFactory {

  static ICommand getInstance(String type) {
    switch(type) {
      case "gameStatus":
        return (new gameStatusClass());
      case "getGames":
        return (new getGamesClass());
      case "joinGame":
        return (new joinGameClass());
      case "getCurrentGameState":
        return (new getCurrentGameStateClass());
      case "newGame":
        return (new newGameClass());
      case "gameCommand":
        return (new gameCommandClass());
      default:
        return null;
    }
  }

  static Class getClass(String type) { 
    switch(type) {
      case "gameStatus":
        return (new gameStatusClass()).getClass();
      case "getGames":
        return (new getGamesClass()).getClass();
      case "joinGame":
        return (new joinGameClass()).getClass();
      case "getCurrentGameState":
        return (new getCurrentGameStateClass()).getClass();
      case "newGame":
        return (new newGameClass()).getClass();
      case "gameCommand":
        return (new gameCommandClass()).getClass();
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
